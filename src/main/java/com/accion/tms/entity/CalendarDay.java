package com.accion.tms.entity;



/**
 *
 * @author chandra.kumar
 */
public class CalendarDay {

    boolean isApproved;
    boolean isSaved;
    boolean isRejected;
    private boolean submitted;
	
	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
    public boolean isRejected() {
		return isRejected;
	}

	public void setRejected(boolean isRejected) {
		this.isRejected = isRejected;
	}

	String columnClass;
    
    public String getColumnClass() {
		return columnClass;
	}

	public void setColumnClass(String columnClass) {
		this.columnClass = columnClass;
	}

	String id;
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	    boolean display = true;

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }
    String date;
    int day;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

	public void setDay(int day) {
        this.day = day;
    }

}
