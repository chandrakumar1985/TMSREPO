package com.accion.tms.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
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
import com.accion.tms.entity.ProjectReport;
import com.accion.tms.entity.ProjectTMS;
import com.accion.tms.entity.UserProjectBilling;
import com.accion.tms.entity.UserTMS;
import com.accion.tms.repository.user.ProjectActivityRepositoryDao;
import com.accion.tms.repository.user.ProjectRepositoryDao;
import com.accion.tms.repository.user.TMSRepositoryDao;
import com.accion.tms.repository.user.UserRepositoryDao;
import com.accion.tms.entity.User;


@Component
@Path("/report")
public class ProjectReportResource
{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProjectRepositoryDao projectRepositoryDao;
	
	@Autowired
	private UserRepositoryDao userRepositoryDao;
	
	@Autowired
	private ProjectActivityRepositoryDao projectActivityRepositoryDao;
	
	@Autowired
	private TMSRepositoryDao tMSRepositoryDao;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getprojectreport/{month}/{year}/{day}/{projectid}")
	public List<ProjectReport> getProjectReport(@PathParam("month") String month, @PathParam("year") String year, @PathParam("day") String day, @PathParam("projectid") String projectid)
	{
		Project prj = projectRepositoryDao.findById(projectid);
		UserProjectBilling userPrjBilling = null; 
		int maxBillableHours = 180;
		int actualBillableHours = 0;
		ProjectTMS projectTMS = null;
		ProjectReport projectReport = null;
		boolean isCreateReport = false;
		
		List<ProjectReport> reportList = new ArrayList<ProjectReport>();
		for(String id : prj.getProjectResources())
		{
			actualBillableHours = 0;
			projectTMS = null;
			projectReport = null;
			userPrjBilling = null;
			isCreateReport = false;
			User user = this.userRepositoryDao.findById(id);
			if(user == null)
				continue;
			if( user.getUserProjectBilling()!= null)
			{
				for(UserProjectBilling pBilling : user.getUserProjectBilling())
				{
					if(prj.getId().equals(pBilling.getProjectId()))
					{
					userPrjBilling =  pBilling;
					break;
					}
				}
			}
			List<UserTMS> userTMSList= tMSRepositoryDao.findByMonthAndYearAndUserName(month,year, user.getUsername());
		    for(UserTMS userTMS : userTMSList)
		    {
		    	for(ProjectTMS prjTMS : userTMS.getProjectTMS())
		    	{
		    		if(prj.getProjectName().equals(prjTMS.getProjectName()))
		    		{
		    			projectTMS = prjTMS;
		    			ProjectActivity prjActivity = null;
		    			if(projectTMS.getActivityName() != null)
		    			{
		    				isCreateReport = true;
		    				prjActivity = projectActivityRepositoryDao.findByName(projectTMS.getActivityName());
		    			}
		    			if(prjActivity != null && prjActivity.isBillable())
		    			{
		    			actualBillableHours += projectTMS.getNoOfHours();
		    			break;
		    			}
		    			
		    		}
		    	}
		    }
		    if(isCreateReport)
		    {
		    projectReport = new ProjectReport();
		    projectReport.setResourceName(user.getUsername());
		    projectReport.setHourlyRate(userPrjBilling.getHourlyRate());
		    projectReport.setMaxBillableHours(maxBillableHours);
		    projectReport.setActualBillableHours(actualBillableHours);
		    projectReport.setTotalAmountGenerated(projectReport.getActualBillableHours()*projectReport.getHourlyRate());
		    reportList.add(projectReport);
		    }
		}
		
		
		//rest/report/getprojectreport/:month/:year/:date/:projectid', {month: month, year:year,date:date, projectid:projectid
	 
	 /**ProjectReport pr1 = new ProjectReport();
	 pr1.setResourceName("hanu");
	 pr1.setHourlyRate(10);
	 pr1.setMaxBillableHours(160);
	 pr1.setActualBillableHours(140);
	 pr1.setTotalAmountGenerated(pr1.getActualBillableHours()*pr1.getHourlyRate());
	 reportList.add(pr1);
	 
	 ProjectReport pr2 = new ProjectReport();
	 pr2.setResourceName("hanu1");
	 pr2.setHourlyRate(10);
	 pr2.setMaxBillableHours(160);
	 pr2.setActualBillableHours(140);
	 pr2.setTotalAmountGenerated(pr2.getActualBillableHours()*pr2.getHourlyRate());
	 reportList.add(pr2);
	 
	 ProjectReport pr3 = new ProjectReport();
	 pr3.setResourceName("hanu2");
	 pr3.setHourlyRate(10);
	 pr3.setMaxBillableHours(160);
	 pr3.setActualBillableHours(140);
	 pr3.setTotalAmountGenerated(pr3.getActualBillableHours()*pr3.getHourlyRate());
	 reportList.add(pr3);
	 
	 ProjectReport pr4 = new ProjectReport();
	 pr4.setResourceName("hanu3");
	 pr4.setHourlyRate(10);
	 pr4.setMaxBillableHours(160);
	 pr4.setActualBillableHours(140);
	 pr4.setTotalAmountGenerated(pr4.getActualBillableHours()*pr4.getHourlyRate());
	 reportList.add(pr4);
	 
	 ProjectReport pr5 = new ProjectReport();
	 pr5.setResourceName("hanu4");
	 pr5.setHourlyRate(10);
	 pr5.setMaxBillableHours(160);
	 pr5.setActualBillableHours(140);
	 pr5.setTotalAmountGenerated(pr5.getActualBillableHours()*pr5.getHourlyRate());
	 reportList.add(pr5);**/
	 
	 return reportList;
	}
	
	

}