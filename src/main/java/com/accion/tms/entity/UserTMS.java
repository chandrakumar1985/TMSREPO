package com.accion.tms.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="usertms")
public class UserTMS
{
	
	@Id
	private String id;
	
	private String tmsDate;
	
	private boolean submitted;
	
	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public String getTmsDate() {
		return tmsDate;
	}

	public void setTmsDate(String tmsDate) {
		this.tmsDate = tmsDate;
	}

	private String year;
	
	private String userName;
	
	private boolean isApproved;
	
	private boolean isSaved;
	
	private boolean isRejected;
	
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

	public boolean isRejected() {
		return isRejected;
	}

	public void setRejected(boolean isRejected) {
		this.isRejected = isRejected;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private String month;
	
	private String day;
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	//this could be used for invoice.
	private String userRoleForproject;
	
	public String getUserRoleForproject() {
		return userRoleForproject;
	}

	public void setUserRoleForproject(String userRoleForproject) {
		this.userRoleForproject = userRoleForproject;
	}
	@DBRef
	private List<ProjectTMS> projectTMS;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<ProjectTMS> getProjectTMS() {
		return projectTMS;
	}

	public void setProjectTMS(List<ProjectTMS> projectTMS) {
		this.projectTMS = projectTMS;
	}

}