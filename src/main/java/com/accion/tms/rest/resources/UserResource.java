package com.accion.tms.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.accion.tms.JsonViews;
import com.accion.tms.entity.Project;
import com.accion.tms.entity.User;
import com.accion.tms.entity.UserProjectBilling;
import com.accion.tms.repository.user.ProjectRepositoryDao;
import com.accion.tms.repository.user.UserRepositoryDao;
import com.accion.tms.rest.TokenUtils;
import com.accion.tms.transfer.TokenTransfer;
import com.accion.tms.transfer.UserTransfer;


@Component
@Path("/user")
public class UserResource
{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDetailsService userService;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;
	@Autowired
	private UserRepositoryDao userRepositoryDao;
	
	@Autowired
	private ProjectRepositoryDao projectRepositoryDao;
	@Resource
	private PasswordEncoder passwordEncoder;
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listuser/{id}")
	public void delete(@PathParam("id") String id)
	{
		this.logger.info("delete(id)");

		this.userRepositoryDao.delete(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public User read(@PathParam("id") String id)
	{
		this.logger.info("read(id)");

		User newsEntry = this.userRepositoryDao.findById(id);
		if (newsEntry == null) {
			throw new WebApplicationException(404);
		}
		return newsEntry;
	}
	@GET
	@Path("listuser")
	@Produces(MediaType.APPLICATION_JSON)
	public String list() throws JsonGenerationException, JsonMappingException, IOException
	{
		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<User> allEntries =  (List<User>) this.userRepositoryDao.findAll();

		return viewWriter.writeValueAsString(allEntries);
	}
	
	/**
	 * Retrieves the currently logged in user.
	 * 
	 * @return A transfer containing the username and the roles.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserTransfer getUser()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			throw new WebApplicationException(401);
		}
		UserDetails userDetails = (UserDetails) principal;
		User user = this.userRepositoryDao.findByUsername(userDetails.getUsername());
		return new UserTransfer(userDetails.getUsername(), this.createRoleMap(userDetails), user.getId());
	}


	/**
	 * Authenticates a user and creates an authentication token.
	 * 
	 * @param username
	 *            The name of the user.
	 * @param password
	 *            The password of the user.
	 * @return A transfer containing the authentication token.
	 */
	@Path("authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public TokenTransfer authenticate(@FormParam("username") String username, @FormParam("password") String password)
	{
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
		 * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
		UserDetails userDetails = this.userService.loadUserByUsername(username);

		return new TokenTransfer(TokenUtils.createToken(userDetails));
	}


	private Map<String, Boolean> createRoleMap(UserDetails userDetails)
	{
		Map<String, Boolean> roles = new HashMap<String, Boolean>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.getAuthority(), Boolean.TRUE);
		}

		return roles;
	}
	@Path("adduser")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.accion.tms.entity.User createUser(com.accion.tms.entity.User newsEntry)
	{
		//this.logger.info("create(): " + newsEntry);
		
		newsEntry.setId(UUID.randomUUID().toString());
		newsEntry.setPassword(this.passwordEncoder.encode(newsEntry.getPassword()));
		List<UserProjectBilling> upList = newsEntry.getUserProjectBilling();
		for(UserProjectBilling up : upList)
		{
			Project prj = projectRepositoryDao.findById(up.getProjectId());
			
			if(prj.getProjectResources()!= null)
			{
				prj.getProjectResources().add(newsEntry.getId());
			}
			else
			{
				List<String> resources = new ArrayList<String>();
				resources.add(newsEntry.getId());
				prj.setProjectResources(resources);
			}
			projectRepositoryDao.save(prj);
		}
		return this.userRepositoryDao.save(newsEntry);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public User update(@PathParam("id") String id, User newsEntry)
	{
		this.logger.info("update(): " + newsEntry);
		newsEntry.setPassword(this.passwordEncoder.encode(newsEntry.getPassword()));
		List<UserProjectBilling> upList = newsEntry.getUserProjectBilling();
		for(UserProjectBilling up : upList)
		{
			Project prj = projectRepositoryDao.findById(up.getProjectId());
			
			if(prj.getProjectResources()!= null && !prj.getProjectResources().contains(newsEntry.getId()))
			{
				prj.getProjectResources().add(newsEntry.getId());
			}
			else
			{
				List<String> resources = new ArrayList<String>();
				resources.add(newsEntry.getId());
				prj.setProjectResources(resources);
			}
			projectRepositoryDao.save(prj);
		}
		return this.userRepositoryDao.save(newsEntry);
	}


	
	private boolean isAdmin()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			if (authority.toString().equals("admin")) {
				return true;
			}
		}

		return false;
	}

}