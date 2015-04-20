package com.accion.tms.rest.resources;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.accion.tms.entity.CalendarData;
import com.accion.tms.entity.ProjectTMS;
import com.accion.tms.entity.UserTMS;
import com.accion.tms.repository.user.TMSProjectRepositoryDao;
import com.accion.tms.repository.user.TMSRepositoryDao;
import com.accion.tms.util.CalendarUtil;


@Component
@Path("/tms")
public class TMSResource
{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TMSRepositoryDao tMSRepositoryDao;
	@Autowired
	private TMSProjectRepositoryDao tMSProjectRepositoryDao;
	
	@Autowired
	private CalendarUtil calendarUtil;
	
	
	
	@Autowired
	private ObjectMapper mapper;
	
	

	@Path("addtms")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void createTMS(UserTMS newsEntry)
	{
		//this.logger.info("create(): " + newsEntry);
		System.out.println(newsEntry);
		if(newsEntry.getId() == null || newsEntry.getId().isEmpty())
		newsEntry.setId(UUID.randomUUID().toString());
		for(ProjectTMS projectTMS: newsEntry.getProjectTMS())
		{
			projectTMS.setSaved(newsEntry.isSaved());
			projectTMS.setSubmitted(newsEntry.isSubmitted());
			projectTMS.setUserTmsId(newsEntry.getId());
			if(projectTMS.getId() == null || projectTMS.getId().isEmpty())
			projectTMS.setId(UUID.randomUUID().toString());
		}
		//newsEntry.setPassword(this.passwordEncoder.encode(newsEntry.getPassword()));
		tMSProjectRepositoryDao.save(newsEntry.getProjectTMS());
		this.tMSRepositoryDao.save(newsEntry);
	}
	
	
	
	@Path("gettms/{month}/{year}/{date}/{username}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ProjectTMS> getTMS(@PathParam("month") int month, @PathParam("year") int year,@PathParam("date") String date, @PathParam("username") String username )
	{
		UserTMS userTMS = tMSRepositoryDao.findByTmsDateAndUserName(date+"/"+month + "/" +year, username);
		if(userTMS == null)
		{
			return null;
		}
		return userTMS.getProjectTMS();
	}
	
	
	@Path("getalltmsforaprroval/{approverName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ProjectTMS> getAllTMSForApproval(@PathParam("approverName") String approverName)
	{
		List<ProjectTMS> userTMSList = tMSProjectRepositoryDao.findByApproverNameAndSubmitted(approverName, true);
		/**List<ProjectTMS> projectTMSList = new ArrayList<ProjectTMS>();
		for(UserTMS userTMS : userTMSList)
		{
			for(ProjectTMS projectTMS : userTMS.getProjectTMS())
			{
			projectTMSList.add(projectTMS);
			}
		}**/
		return userTMSList;
	}
	
	@Path("currentmonthcalendar/{month}/{year}/{date}/{username}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//month,:year,:date,:username
	public CalendarData currentMonthCalendar(@PathParam("month") int month, @PathParam("year") int year,@PathParam("date") String date, @PathParam("username") String username )
	{
		return calendarUtil.createCalendar(month, year, username);
	}
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("update")
	public void  update(ProjectTMS updateTMS )
	{
		ProjectTMS projectTMS = this.tMSProjectRepositoryDao.findById(updateTMS.getId());
		
			if(projectTMS.getId().equalsIgnoreCase(updateTMS.getId()))
			{
				if(updateTMS.isApproved())
				{
					projectTMS.setSubmitted(false);
					projectTMS.setApproved(true);
					projectTMS.setRejected(false);
				}
				else if(updateTMS.isRejected())
				{
					projectTMS.setRejected(true);
					projectTMS.setSubmitted(false);
					projectTMS.setApproved(false);
				}
				projectTMS.setApproverRemarks(updateTMS.getApproverRemarks());
			}
			this.tMSProjectRepositoryDao.save(projectTMS);
			//projectTmsId
			UserTMS userTMS = tMSRepositoryDao.findById(projectTMS.getUserTmsId());
			int count = 0;
			int submitcount = 0;
			int rejectcount = 0;
		    for(ProjectTMS pTMS :userTMS.getProjectTMS())
		    {
		    	if(pTMS.isApproved())
		    	{
		    		++count;
		    	}
		    	else if(pTMS.isSubmitted())
		    	{
		    		++submitcount;
		    	}
		    	else if(pTMS.isRejected())
		    	{
		    		++rejectcount;
		    	}
		    }
		    if(count== userTMS.getProjectTMS().size())
		    {
		    	userTMS.setApproved(true);
		    }
		    else if(submitcount== userTMS.getProjectTMS().size())
		    {
		    	userTMS.setSubmitted(true);
		    }
		    else if(rejectcount== userTMS.getProjectTMS().size())
		    {
		    	userTMS.setRejected(true);
		    }
		    tMSRepositoryDao.save(userTMS);
		
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