package com.accion.tms.entity;



public class ProjectReport
{
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public float getProjectName() {
		return projectName;
	}

	public void setProjectName(float projectName) {
		this.projectName = projectName;
	}

	public String getProjectOwner() {
		return projectOwner;
	}

	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public float getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(float hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public int getMaxBillableHours() {
		return maxBillableHours;
	}

	public void setMaxBillableHours(int maxBillableHours) {
		this.maxBillableHours = maxBillableHours;
	}

	public int getActualBillableHours() {
		return actualBillableHours;
	}

	public void setActualBillableHours(int actualBillableHours) {
		this.actualBillableHours = actualBillableHours;
	}

	public float getTotalAmountGenerated() {
		return totalAmountGenerated;
	}

	public void setTotalAmountGenerated(float totalAmountGenerated) {
		this.totalAmountGenerated = totalAmountGenerated;
	}

	private String projectId;
	
	private float projectName;
	
	private String projectOwner;

	private String resourceName;
	
	private float hourlyRate;
	
	private int maxBillableHours;
	
	private int actualBillableHours;
	
	private float totalAmountGenerated;

}