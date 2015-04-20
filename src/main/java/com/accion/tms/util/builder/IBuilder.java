package com.accion.tms.util.builder;



import java.util.List;

import com.accion.tms.entity.CalendarData;



public interface IBuilder 
{
	public CalendarData buildCalender(int startMonth, int year, CalendarData calendarData, String userName);

}
