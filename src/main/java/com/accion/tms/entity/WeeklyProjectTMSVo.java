package com.accion.tms.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonSerialize
public class WeeklyProjectTMSVo
{
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Id
	private String id;
	
	

	private String projectName;
	
	private String activityName;
	
	private boolean isApproved;
	
	private boolean isSaved;
	
	private boolean isRejected;
	
	private boolean submitted;
	
	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
	
	private String approverName;
	
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public boolean isRejected() {
		return isRejected;
	}

	public void setRejected(boolean isRejected) {
		this.isRejected = isRejected;
	}

	public String getUserRemarks() {
		return userRemarks;
	}

	public void setUserRemarks(String userRemarks) {
		this.userRemarks = userRemarks;
	}

	public String getApproverComments() {
		return approverComments;
	}

	public void setApproverComments(String approverComments) {
		this.approverComments = approverComments;
	}

	private String userRemarks;
	
	private String approverComments;
	
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
//projectName:null,activityName:null,  noOfHoursFirstDay:null,noOfHoursSecondDay:null,noOfHoursThirdDay:null,noOfHoursFourthDay:null,noOfHoursFifthDay:null,noOfHoursSixthDay:null,noOfHoursSeventhDay:null}
	private int noOfHoursFirstDay;
	private int noOfHoursSecondDay;
	private int noOfHoursThirdDay;
	private int noOfHoursFourthDay;
	private int noOfHoursFifthDay;
	private int noOfHoursSixthDay;
	private int noOfHoursSeventhDay;
	private String startDate;
	private String secondDate;
	private String thirdDate;
	private String fourthDate;
	private String fifthDate;
	private String sixthDate;
	private String endDate;



	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getSecondDate() {
		return secondDate;
	}

	public void setSecondDate(String secondDate) {
		this.secondDate = secondDate;
	}

	public String getThirdDate() {
		return thirdDate;
	}

	public void setThirdDate(String thirdDate) {
		this.thirdDate = thirdDate;
	}

	public String getFourthDate() {
		return fourthDate;
	}

	public void setFourthDate(String fourthDate) {
		this.fourthDate = fourthDate;
	}

	public String getFifthDate() {
		return fifthDate;
	}

	public void setFifthDate(String fifthDate) {
		this.fifthDate = fifthDate;
	}

	public String getSixthDate() {
		return sixthDate;
	}

	public void setSixthDate(String sixthDate) {
		this.sixthDate = sixthDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	private String weekStartDate;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getWeekStartDate() {
		return weekStartDate;
	}

	public void setWeekStartDate(String weekStartDate) {
		this.weekStartDate = weekStartDate;
	}

	public int getNoOfHoursFirstDay() {
		return noOfHoursFirstDay;
	}

	public void setNoOfHoursFirstDay(int noOfHoursFirstDay) {
		this.noOfHoursFirstDay = noOfHoursFirstDay;
	}

	public int getNoOfHoursSecondDay() {
		return noOfHoursSecondDay;
	}

	public void setNoOfHoursSecondDay(int noOfHoursSecondDay) {
		this.noOfHoursSecondDay = noOfHoursSecondDay;
	}

	public int getNoOfHoursThirdDay() {
		return noOfHoursThirdDay;
	}

	public void setNoOfHoursThirdDay(int noOfHoursThirdDay) {
		this.noOfHoursThirdDay = noOfHoursThirdDay;
	}

	public int getNoOfHoursFourthDay() {
		return noOfHoursFourthDay;
	}

	public void setNoOfHoursFourthDay(int noOfHoursFourthDay) {
		this.noOfHoursFourthDay = noOfHoursFourthDay;
	}

	public int getNoOfHoursFifthDay() {
		return noOfHoursFifthDay;
	}

	public void setNoOfHoursFifthDay(int noOfHoursFifthDay) {
		this.noOfHoursFifthDay = noOfHoursFifthDay;
	}

	public int getNoOfHoursSixthDay() {
		return noOfHoursSixthDay;
	}

	public void setNoOfHoursSixthDay(int noOfHoursSixthDay) {
		this.noOfHoursSixthDay = noOfHoursSixthDay;
	}

	public int getNoOfHoursSeventhDay() {
		return noOfHoursSeventhDay;
	}

	public void setNoOfHoursSeventhDay(int noOfHoursSeventhDay) {
		this.noOfHoursSeventhDay = noOfHoursSeventhDay;
	}	

}