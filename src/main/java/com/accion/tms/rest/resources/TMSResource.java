package com.accion.tms.rest.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import com.accion.tms.entity.CalendarDate;
import com.accion.tms.entity.ProjectTMS;
import com.accion.tms.entity.UserTMS;
import com.accion.tms.entity.PieChartVo;
import com.accion.tms.entity.WeeklyProjectTMSVo;
import com.accion.tms.entity.WeeklyTMSVo;
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
	
	//
	@Path("addweeklytms")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void createWeeklyTMS(WeeklyTMSVo weeklyProjectTmsVo)
	{
		//this.logger.info("create(): " + newsEntry);
		//System.out.println(newsEntry);
		if(weeklyProjectTmsVo!= null && weeklyProjectTmsVo.getWeeklyProjectTMS() != null)
		{
			List<ProjectTMS> firstDayList = new ArrayList<ProjectTMS>();
			List<ProjectTMS> secondDayList = new ArrayList<ProjectTMS>();
			List<ProjectTMS> thirdDayList = new ArrayList<ProjectTMS>();
			List<ProjectTMS> fourthDayList = new ArrayList<ProjectTMS>();
			List<ProjectTMS> fifthDayList = new ArrayList<ProjectTMS>();
			List<ProjectTMS> sixthDayList = new ArrayList<ProjectTMS>();
			List<ProjectTMS> seventhDayList = new ArrayList<ProjectTMS>();
			UserTMS firstDayTMS = new UserTMS();
			firstDayTMS.setId(UUID.randomUUID().toString());
			UserTMS secondDayTMS = new UserTMS();
			secondDayTMS.setId(UUID.randomUUID().toString());
			UserTMS thirdDayTMS = new UserTMS();
			thirdDayTMS.setId(UUID.randomUUID().toString());
			UserTMS fourthDayTMS = new UserTMS();
			fourthDayTMS.setId(UUID.randomUUID().toString());
			UserTMS fifthDayTMS = new UserTMS();
			fifthDayTMS.setId(UUID.randomUUID().toString());
			UserTMS sixthDayTMS = new UserTMS();
			sixthDayTMS.setId(UUID.randomUUID().toString());
			UserTMS seventhDayTMS = new UserTMS();
			seventhDayTMS.setId(UUID.randomUUID().toString());
			for(WeeklyProjectTMSVo weeklyProjectTMSVo :weeklyProjectTmsVo.getWeeklyProjectTMS())
			{
				if(weeklyProjectTMSVo.getNoOfHoursFirstDay() > 0)
				{
					ProjectTMS projectTMS = new ProjectTMS();
					projectTMS.setSaved(weeklyProjectTMSVo.isSaved());
					projectTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					projectTMS.setUserTmsId(firstDayTMS.getId());
					projectTMS.setApproverName(weeklyProjectTMSVo.getApproverName());
					firstDayTMS.setUserName(weeklyProjectTMSVo.getUserName());
					firstDayTMS.setSaved(weeklyProjectTMSVo.isSaved());
					firstDayTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					projectTMS.setNoOfHours(weeklyProjectTMSVo.getNoOfHoursFirstDay());
					projectTMS.setProjectName(weeklyProjectTMSVo.getProjectName());
					projectTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getStartDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getStartDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getStartDate().split("/")[2]);
					if(weeklyProjectTMSVo.getStartDate()!= null)
					{
						secondDayTMS.setDay(Integer.parseInt(weeklyProjectTMSVo.getStartDate().split("/")[0])+"");
						secondDayTMS.setMonth((Integer.parseInt(weeklyProjectTMSVo.getStartDate().split("/")[1])-1)+"");
						secondDayTMS.setYear(weeklyProjectTMSVo.getStartDate().split("/")[2]);
					}
					firstDayTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getStartDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getStartDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getStartDate().split("/")[2]);
					projectTMS.setActivityName(weeklyProjectTMSVo.getActivityName());
					projectTMS.setId(UUID.randomUUID().toString());
					firstDayList.add(projectTMS);
					firstDayTMS.setProjectTMS(firstDayList);
				}
				if(weeklyProjectTMSVo.getNoOfHoursSecondDay() > 0)
				{
					ProjectTMS projectTMS = new ProjectTMS();
					projectTMS.setSaved(weeklyProjectTMSVo.isSaved());
					projectTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					projectTMS.setUserTmsId(secondDayTMS.getId());
					projectTMS.setApproverName(weeklyProjectTMSVo.getApproverName());
					secondDayTMS.setUserName(weeklyProjectTMSVo.getUserName());
					secondDayTMS.setSaved(weeklyProjectTMSVo.isSaved());
					secondDayTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					secondDayTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getSecondDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getSecondDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getSecondDate().split("/")[2]);
					projectTMS.setNoOfHours(weeklyProjectTMSVo.getNoOfHoursSecondDay());
					projectTMS.setProjectName(weeklyProjectTMSVo.getProjectName());
					projectTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getSecondDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getSecondDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getSecondDate().split("/")[2]);
					if(weeklyProjectTMSVo.getSecondDate()!= null)
					{
						secondDayTMS.setDay(Integer.parseInt(weeklyProjectTMSVo.getSecondDate().split("/")[0])+"");
						secondDayTMS.setMonth((Integer.parseInt(weeklyProjectTMSVo.getSecondDate().split("/")[1])-1)+"");
						secondDayTMS.setYear(weeklyProjectTMSVo.getSecondDate().split("/")[2]);
					}
					projectTMS.setActivityName(weeklyProjectTMSVo.getActivityName());
					projectTMS.setId(UUID.randomUUID().toString());
					secondDayList.add(projectTMS);
					secondDayTMS.setProjectTMS(secondDayList);
				}
				if(weeklyProjectTMSVo.getNoOfHoursThirdDay() > 0)
				{
					ProjectTMS projectTMS = new ProjectTMS();
					projectTMS.setSaved(weeklyProjectTMSVo.isSaved());
					projectTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					projectTMS.setUserTmsId(thirdDayTMS.getId());
					projectTMS.setApproverName(weeklyProjectTMSVo.getApproverName());
					thirdDayTMS.setUserName(weeklyProjectTMSVo.getUserName());
					thirdDayTMS.setSaved(weeklyProjectTMSVo.isSaved());
					thirdDayTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					projectTMS.setNoOfHours(weeklyProjectTMSVo.getNoOfHoursThirdDay());
					projectTMS.setProjectName(weeklyProjectTMSVo.getProjectName());
					projectTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getThirdDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getThirdDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getThirdDate().split("/")[2]);
					thirdDayTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getThirdDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getThirdDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getThirdDate().split("/")[2]);
					if(weeklyProjectTMSVo.getThirdDate()!= null)
					{
						thirdDayTMS.setDay(Integer.parseInt(weeklyProjectTMSVo.getThirdDate().split("/")[0])+"");
						thirdDayTMS.setMonth((Integer.parseInt(weeklyProjectTMSVo.getThirdDate().split("/")[1])-1)+"");
						thirdDayTMS.setYear(weeklyProjectTMSVo.getThirdDate().split("/")[2]);
					}
					projectTMS.setActivityName(weeklyProjectTMSVo.getActivityName());
					projectTMS.setId(UUID.randomUUID().toString());
					thirdDayList.add(projectTMS);
					thirdDayTMS.setProjectTMS(thirdDayList);
				}
				if(weeklyProjectTMSVo.getNoOfHoursFourthDay() > 0)
				{
					ProjectTMS projectTMS = new ProjectTMS();
					projectTMS.setSaved(weeklyProjectTMSVo.isSaved());
					projectTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					projectTMS.setUserTmsId(fourthDayTMS.getId());
					projectTMS.setApproverName(weeklyProjectTMSVo.getApproverName());
					fourthDayTMS.setUserName(weeklyProjectTMSVo.getUserName());
					fourthDayTMS.setSaved(weeklyProjectTMSVo.isSaved());
					fourthDayTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					fourthDayTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getFourthDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getFourthDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getFourthDate().split("/")[2]);
					if(weeklyProjectTMSVo.getFourthDate()!= null)
					{
						fourthDayTMS.setDay(Integer.parseInt(weeklyProjectTMSVo.getFourthDate().split("/")[0])+"");
						fourthDayTMS.setMonth((Integer.parseInt(weeklyProjectTMSVo.getFourthDate().split("/")[1])-1)+"");
						fourthDayTMS.setYear(weeklyProjectTMSVo.getFourthDate().split("/")[2]);
					}
					projectTMS.setNoOfHours(weeklyProjectTMSVo.getNoOfHoursFourthDay());
					projectTMS.setProjectName(weeklyProjectTMSVo.getProjectName());
					projectTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getFourthDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getFourthDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getFourthDate().split("/")[2]);
					projectTMS.setActivityName(weeklyProjectTMSVo.getActivityName());
					projectTMS.setId(UUID.randomUUID().toString());
					fourthDayList.add(projectTMS);
					fourthDayTMS.setProjectTMS(fourthDayList);
				}
				if(weeklyProjectTMSVo.getNoOfHoursFifthDay() > 0)
				{
					ProjectTMS projectTMS = new ProjectTMS();
					projectTMS.setSaved(weeklyProjectTMSVo.isSaved());
					projectTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					projectTMS.setUserTmsId(fifthDayTMS.getId());
					projectTMS.setApproverName(weeklyProjectTMSVo.getApproverName());
					fifthDayTMS.setUserName(weeklyProjectTMSVo.getUserName());
					fifthDayTMS.setSaved(weeklyProjectTMSVo.isSaved());
					fifthDayTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					fifthDayTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getFifthDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getFifthDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getFifthDate().split("/")[2]);
					projectTMS.setNoOfHours(weeklyProjectTMSVo.getNoOfHoursFifthDay());
					projectTMS.setProjectName(weeklyProjectTMSVo.getProjectName());
					projectTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getFifthDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getFifthDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getFifthDate().split("/")[2]);
					if(weeklyProjectTMSVo.getFifthDate()!= null)
					{
						fifthDayTMS.setDay(Integer.parseInt(weeklyProjectTMSVo.getFifthDate().split("/")[0])+"");
						fifthDayTMS.setMonth((Integer.parseInt(weeklyProjectTMSVo.getFifthDate().split("/")[1])-1)+"");
						fifthDayTMS.setYear(weeklyProjectTMSVo.getFifthDate().split("/")[2]);
					}
					projectTMS.setActivityName(weeklyProjectTMSVo.getActivityName());
					projectTMS.setId(UUID.randomUUID().toString());
					fifthDayList.add(projectTMS);
					fifthDayTMS.setProjectTMS(fifthDayList);
				}
				if(weeklyProjectTMSVo.getNoOfHoursSixthDay() > 0)
				{
					ProjectTMS projectTMS = new ProjectTMS();
					projectTMS.setSaved(weeklyProjectTMSVo.isSaved());
					projectTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					projectTMS.setUserTmsId(sixthDayTMS.getId());
					projectTMS.setApproverName(weeklyProjectTMSVo.getApproverName());
					sixthDayTMS.setUserName(weeklyProjectTMSVo.getUserName());
					projectTMS.setNoOfHours(weeklyProjectTMSVo.getNoOfHoursSixthDay());
					sixthDayTMS.setSaved(weeklyProjectTMSVo.isSaved());
					sixthDayTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					sixthDayTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getSixthDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getSixthDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getSixthDate().split("/")[2]);
					if(weeklyProjectTMSVo.getSixthDate()!= null)
					{
						sixthDayTMS.setDay(Integer.parseInt(weeklyProjectTMSVo.getSixthDate().split("/")[0])+"");
						sixthDayTMS.setMonth((Integer.parseInt(weeklyProjectTMSVo.getSixthDate().split("/")[1])-1)+"");
						sixthDayTMS.setYear(weeklyProjectTMSVo.getSixthDate().split("/")[2]);
					}
					projectTMS.setProjectName(weeklyProjectTMSVo.getProjectName());
					projectTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getSixthDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getSixthDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getSixthDate().split("/")[2]);
					projectTMS.setActivityName(weeklyProjectTMSVo.getActivityName());
					projectTMS.setId(UUID.randomUUID().toString());
					sixthDayList.add(projectTMS);
					sixthDayTMS.setProjectTMS(sixthDayList);
				}
				if(weeklyProjectTMSVo.getNoOfHoursSeventhDay() > 0)
				{
					ProjectTMS projectTMS = new ProjectTMS();
					projectTMS.setSaved(weeklyProjectTMSVo.isSaved());
					projectTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					projectTMS.setUserTmsId(seventhDayTMS.getId());
					projectTMS.setApproverName(weeklyProjectTMSVo.getApproverName());
					seventhDayTMS.setUserName(weeklyProjectTMSVo.getUserName());
					seventhDayTMS.setSaved(weeklyProjectTMSVo.isSaved());
					seventhDayTMS.setSubmitted(weeklyProjectTMSVo.isSubmitted());
					seventhDayTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getEndDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getEndDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getEndDate().split("/")[2]);
					if(weeklyProjectTMSVo.getEndDate()!= null)
					{
					seventhDayTMS.setDay(Integer.parseInt(weeklyProjectTMSVo.getEndDate().split("/")[0])+"");
					seventhDayTMS.setMonth((Integer.parseInt(weeklyProjectTMSVo.getEndDate().split("/")[1])-1)+"");
					seventhDayTMS.setYear(weeklyProjectTMSVo.getEndDate().split("/")[2]);
					}
					projectTMS.setNoOfHours(weeklyProjectTMSVo.getNoOfHoursSeventhDay());
					projectTMS.setProjectName(weeklyProjectTMSVo.getProjectName());
					projectTMS.setTmsDate(Integer.parseInt(weeklyProjectTMSVo.getEndDate().split("/")[0])+"/" + (Integer.parseInt(weeklyProjectTMSVo.getEndDate().split("/")[1])-1)+"/" +weeklyProjectTMSVo.getEndDate().split("/")[2]);
					projectTMS.setActivityName(weeklyProjectTMSVo.getActivityName());
					projectTMS.setId(UUID.randomUUID().toString());
					seventhDayList.add(projectTMS);
					seventhDayTMS.setProjectTMS(seventhDayList);
				}
				
				
			}
			if(seventhDayList.size() > 0)
			{
				tMSProjectRepositoryDao.save(seventhDayList);
				this.tMSRepositoryDao.save(seventhDayTMS);
			}
			if(sixthDayList.size() > 0)
			{
				tMSProjectRepositoryDao.save(sixthDayList);
				this.tMSRepositoryDao.save(sixthDayTMS);
			}
			if(fifthDayList.size() > 0)
			{
				tMSProjectRepositoryDao.save(fifthDayList);
				this.tMSRepositoryDao.save(fifthDayTMS);
			}
			if(fourthDayList.size() > 0)
			{
				tMSProjectRepositoryDao.save(fourthDayList);
				this.tMSRepositoryDao.save(fourthDayTMS);
			}
			if(thirdDayList.size() > 0)
			{
				tMSProjectRepositoryDao.save(thirdDayList);
				this.tMSRepositoryDao.save(thirdDayTMS);
			}
			if(secondDayList.size() > 0)
			{
				tMSProjectRepositoryDao.save(secondDayList);
				this.tMSRepositoryDao.save(secondDayTMS);
			}
			if(firstDayList.size() > 0)
			{
				tMSProjectRepositoryDao.save(firstDayList);
				this.tMSRepositoryDao.save(firstDayTMS);
			}
		}
		
		
	}
	
	@Path("gettms/{month}/{year}/{date}/{username}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ProjectTMS> getTMS(@PathParam("month") int month, @PathParam("year") int year,@PathParam("date") int date, @PathParam("username") String username )
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
	public CalendarData currentMonthCalendar(@PathParam("month") int month, @PathParam("year") int year,@PathParam("date") int date, @PathParam("username") String username )
	{
		if(month == 0 && year == 0 && date == 0)
		{
			Calendar calendar =  Calendar.getInstance();
			month = calendar.get(Calendar.MONTH);
			year = calendar.get(Calendar.YEAR);
			date = calendar.get(Calendar.DATE);
		}
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

	
	@Path("gettmsdate/{month}/{year}/{date}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  CalendarDate getTMSDate(@PathParam("month") int month, @PathParam("year") int year,@PathParam("date") int date )
	{
		CalendarDate calDate = new CalendarDate();
		Calendar c = Calendar.getInstance();
		c.set(year,month,date);
		// Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        //String startDate = "", endDate = "";
        String currentDate=df.format(c.getTime());
        calDate.setCurrentDate(currentDate);
        populateCalendar(c, calDate);
        //startDate = df.format(firstDayOfWeek(c).getTime());
        //endDate = df.format(lastDayOfWeek(c).getTime());
		
		//calDate.setStartDate(startDate);
		//calDate.setEndDate(endDate);
		//calDate.setCurrentDate(currentDate);
		
		return calDate;
	}
	
	@Path("getbillingchartreport/{month}/{year}/{date}/{username}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<PieChartVo> getPieChart(@PathParam("month") int month, @PathParam("year") int year,@PathParam("date") String date, @PathParam("username") String username )
	{
		List<PieChartVo> visitFrequencyList = new ArrayList<PieChartVo>();
		UserTMS userTMS = tMSRepositoryDao.findByTmsDateAndUserName(date+"/"+month + "/" +year, username);
		PieChartVo v= new PieChartVo();
		v.setKey("billable");
		v.setCount(3);
		visitFrequencyList.add(v);
		PieChartVo v1= new PieChartVo();
		v1.setKey("nonbillable");
		v1.setCount(1);
		visitFrequencyList.add(v1);
		return visitFrequencyList;
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
	
	public static Calendar firstDayOfWeek(Calendar calendar){
	     Calendar cal = (Calendar) calendar.clone();
	     //int day = cal.get(Calendar.DAY_OF_YEAR);
	     while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
	         // cal.setTimeInMillis(cal.getTimeInMillis()-ONE_DAY);
	    	 cal.add(Calendar.DAY_OF_YEAR, -1);
	     }
	     return cal;
	}
	
	public static Calendar lastDayOfWeek(Calendar calendar){
	     Calendar cal = (Calendar) calendar.clone();
	     int day = cal.get(Calendar.DAY_OF_YEAR);
	     while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
	          cal.set(Calendar.DAY_OF_YEAR, ++day);
	     }
	     return cal;
	}
	
	public static CalendarDate populateCalendar(Calendar calendar, CalendarDate calendarDate){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		calendar = firstDayOfWeek(calendar);
		calendarDate.setStartDate(df.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendarDate.setSecondDate(df.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendarDate.setThirdDate(df.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendarDate.setFourthDate(df.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendarDate.setFifthDate(df.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendarDate.setSixthDate(df.format(calendar.getTime()));
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendarDate.setEndDate(df.format(calendar.getTime()));
		return calendarDate;
	}

}