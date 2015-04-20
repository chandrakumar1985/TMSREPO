package com.accion.tms.util.builder.impl;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accion.tms.entity.CalendarData;
import com.accion.tms.entity.CalendarDay;
import com.accion.tms.entity.CalendarWeek;
import com.accion.tms.entity.ProjectTMS;
import com.accion.tms.entity.UserTMS;
import com.accion.tms.repository.user.TMSRepositoryDao;
import com.accion.tms.util.builder.IBuilder;

@Service("calendarBuilder")
public class CalendarBuilder implements IBuilder
{
	@Autowired
	private TMSRepositoryDao tMSRepositoryDao;
	
	public enum Day
	 {
	     SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3),
	     THURSDAY(4), FRIDAY(5), SATURDAY(6);
	     private int day;
	  // Constructor for Day
	     Day(int calendarday) {
	       day = calendarday;
	     }
	     
	     int getDay()
	     {
	    	return day;
	     }
	 }
	
		
	public CalendarData buildCalender(int startMonth,int year, CalendarData calendarDataBean, String userName)
	{
		

	       CalendarDay calendarDay = null;
	       Calendar cal = Calendar.getInstance();
	       
	       calendarDataBean = new CalendarData();
	      
	       cal.set(year, startMonth, 1);
	       List<CalendarDay> calendarDays  = new ArrayList<CalendarDay>();
	       for(int day = 1; day <= cal.getActualMaximum(cal.DAY_OF_MONTH); day++)
	       {
	           cal.set(year, startMonth, day);
	           calendarDay = new CalendarDay();
	           calendarDay.setDay(day);
	           calendarDay.setDate(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
	           UserTMS userTMS = tMSRepositoryDao.findByTmsDateAndUserName(day+"/"+startMonth + "/" +year, userName);
	           if(userTMS != null)
	           {	  
	        	   if(userTMS.isSaved())
	        	   {
	        	   calendarDay.setSaved(userTMS.isSaved());
	        	   calendarDay.setColumnClass("selectedDate");
	        	   }
	        	   else if(userTMS.isApproved())
	        	   {
	        	   calendarDay.setApproved(userTMS.isApproved());
	        	   calendarDay.setColumnClass("promotion");
	        	   
	        	   }
	        	   else if(userTMS.isRejected())
	        	   {
	        	   calendarDay.setRejected(userTMS.isRejected());
	        	   calendarDay.setColumnClass("rejected");
	        	   }
	        	   else if(userTMS.isSubmitted())
	        	   {
	        	   calendarDay.setSubmitted(userTMS.isSubmitted());
	        	   calendarDay.setColumnClass("submittedDate");
	        	   }
	        	   else
	        	   {
	        	   calendarDay.setColumnClass("available");
	        	   }
	        	  
	           }
	           calendarDays.add(calendarDay);


	       }
	       appendWeekDays(calendarDays);
	       
	       
	       calendarDataBean.setWeeklyCalendar(createWeeklyCalendar(calendarDays));
	       
	       calendarDataBean.setMonth(cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US).toUpperCase());
	       calendarDataBean.setMonthNo(cal.get(Calendar.MONTH));
	       calendarDataBean.setYear(String.valueOf(cal.get(Calendar.YEAR)));
	       
	       
	       return calendarDataBean;
	}
	
	
	 /**
     * 
     */
    
    private List<CalendarWeek> createWeeklyCalendar(List<CalendarDay> calendarDays)
    {
    	List<CalendarWeek> calendarWeeks  = new ArrayList<CalendarWeek>(6);   	
    	for(int i=0;i<6;i++)
    	{
    		CalendarWeek calendarWeek = new CalendarWeek();
        	calendarWeek.setFirstDay(calendarDays.get(i*7));
        	calendarWeek.setSecondDay(calendarDays.get(i*7 + 1));
        	calendarWeek.setThirdDay(calendarDays.get(i*7 + 2));
        	calendarWeek.setFourthDay(calendarDays.get(i*7 + 3));
        	calendarWeek.setFifthDay(calendarDays.get(i*7 + 4));
        	calendarWeek.setSixthDay(calendarDays.get(i*7 + 5));
        	calendarWeek.setSeventhDay(calendarDays.get(i*7 + 6));
    		calendarWeeks.add(calendarWeek);
    	}
    	
    	return calendarWeeks;
    	
    }
       
    
    /**
     * need to relook at the if condition
     * @param calendarDays
     */
    private void appendWeekDays(List<CalendarDay> calendarDays)
    {
        appendFirstWeekDays(calendarDays, Day.valueOf(calendarDays.get(0).getDate().toUpperCase()).getDay());        	
      //adding last week data.
    	while(calendarDays.size() < 42)
    	{
    		CalendarDay calendarDay = new CalendarDay();
            calendarDay.setDisplay(false);
    		calendarDays.add(calendarDay);
    		
    	}
    	//TODO look whether this unique id is required or not.
    	for(int id=0; id < calendarDays.size();id++)
    	{
    		calendarDays.get(id).setId(String.valueOf(id));
    	}
    	
    }
    
    private void appendFirstWeekDays(List<CalendarDay> calendarDays, int day)
    {
    	while(day > 0)
    	{
    		CalendarDay calendarDay = new CalendarDay();
            calendarDay.setDisplay(false);
            calendarDays.add(0, calendarDay);
            day--;
    	}
    	
    }
    
	

}
