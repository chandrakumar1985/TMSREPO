package com.accion.tms.entity;



import java.util.List;

/**
 *
 * @author chandra.kumar
 */
public class CalendarData {

    private List<CalendarDay> calendarDays ;
    private String month;
    private int monthNo;
    private int calendarNo;
    private List<CalendarWeek> weeklyCalendar;

    public List<CalendarWeek> getWeeklyCalendar() {
		return weeklyCalendar;
	}

	public void setWeeklyCalendar(List<CalendarWeek> weeklyCalendar) {
		this.weeklyCalendar = weeklyCalendar;
	}

	public int getCalendarNo() {
        return calendarNo;
    }

    public void setCalendarNo(int calendarNo) {
        this.calendarNo = calendarNo;
    }

    public int getMonthNo() {
        return monthNo;
    }

    public void setMonthNo(int monthNo) {
        this.monthNo = monthNo;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    private String year;

    public List<CalendarDay> getCalendarDays() {
        return calendarDays;
    }

    public void setCalendarDays(List<CalendarDay> calendarDays) {
        this.calendarDays = calendarDays;
    }
    

}
