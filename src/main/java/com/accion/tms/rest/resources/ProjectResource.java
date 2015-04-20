package com.accion.tms.rest.resources;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.accion.tms.JsonViews;
import com.accion.tms.entity.Project;
import com.accion.tms.entity.ProjectActivity;
import com.accion.tms.repository.user.ProjectActivityRepositoryDao;
import com.accion.tms.repository.user.ProjectRepositoryDao;


@Component
@Path("/project")
public class ProjectResource
{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProjectRepositoryDao projectRepositoryDao;
	
	@Autowired
	private ProjectActivityRepositoryDao projectActivityRepositoryDao;
	
	@Autowired
	private ObjectMapper mapper;
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("listproject/{id}")
	public void delete(@PathParam("id") String id)
	{
		this.logger.info("delete(id)");

		this.projectRepositoryDao.delete(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Project read(@PathParam("id") String id)
	{
		this.logger.info("read(id)");

		Project newsEntry = this.projectRepositoryDao.findById(id);
		if (newsEntry == null) {
			throw new WebApplicationException(404);
		}
		return newsEntry;
	}
	@GET
	@Path("listproject/{user}")
	@Produces(MediaType.APPLICATION_JSON)
	public String listBasedOnuser(@PathParam("user") String user) throws JsonGenerationException, JsonMappingException, IOException
	{
		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<Project> allEntries =  (List<Project>) this.projectRepositoryDao.findByProjectResources(user);

		return viewWriter.writeValueAsString(allEntries);
	}
	@GET
	@Path("listproject")
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
		List<Project> allEntries =  (List<Project>) this.projectRepositoryDao.findAll();

		return viewWriter.writeValueAsString(allEntries);
	}
	
	
	@GET
	@Path("listprojectactivity")
	@Produces(MediaType.APPLICATION_JSON)
	public String listprojectactivity() throws JsonGenerationException, JsonMappingException, IOException
	{
		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<ProjectActivity> allEntries =  (List<ProjectActivity>) this.projectActivityRepositoryDao.findAll();

		return viewWriter.writeValueAsString(allEntries);
	}
	

	@Path("addproject")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public com.accion.tms.entity.Project createProject(com.accion.tms.entity.Project newsEntry)
	{
		//this.logger.info("create(): " + newsEntry);
		
		newsEntry.setId(UUID.randomUUID().toString());
		//newsEntry.setPassword(this.passwordEncoder.encode(newsEntry.getPassword()));
		return this.projectRepositoryDao.save(newsEntry);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Project update(@PathParam("id") String id, Project newsEntry)
	{
		this.logger.info("update(): " + newsEntry);
		//newsEntry.setPassword(this.passwordEncoder.encode(newsEntry.getPassword()));
		return this.projectRepositoryDao.save(newsEntry);
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