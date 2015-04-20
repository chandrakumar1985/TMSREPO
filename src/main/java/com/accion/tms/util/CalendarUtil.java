package com.accion.tms.util;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accion.tms.entity.CalendarData;
import com.accion.tms.entity.CalendarWeek;
import com.accion.tms.repository.user.TMSRepositoryDao;
import com.accion.tms.util.builder.impl.CalendarBuilder;

/**
 * This class is utility class for population of calendar data which is used to show on to the UI.
 * This creates the calendar for 2 month availability and then populates the calendar with actual
 *  availability flags obtained form service layer.
 * @author chandra.kumar
 *
 */
@Service("calendarUtil")
public class CalendarUtil {
	
	@Autowired
	private CalendarBuilder calendarBuilder;
	
	/**
	 * This creates the two month calendar availability based on the start date.
	 * @param calendarData calendarData which need to be populated with 
	 * two month java calendar based on star date.
	 * @param startMonth start month for which calendar need to be populated.
	 * @param year start year for which calendar need to be populated.
	 */
	public CalendarData createCalendar( int startMonth,int year, String userName)
	{
		CalendarData calendarData = new CalendarData();
		
		return calendarBuilder.buildCalender(startMonth, year, calendarData, userName);
		//return calendarData;
	}
	
	/**
	 * This populates the calendar data with the actual
	 *  availability flags for each day in calendar data.
	 * @param calendarData calendarData which need to be updated with availability flags.
	 * @param calendarAvaialbility calendar availability obtained from PMS.
	 */
	public void processCalendar(CalendarData calendar)
	{
		 Calendar cal = Calendar.getInstance();
		 int todaysDate = cal.get(Calendar.DATE);
		 int currentMonth = cal.get(Calendar.MONTH);
		
			
        
			
		
		//TODO the population of calendar data with actual avaiability flags is done here.
		
		
	}
	

}
