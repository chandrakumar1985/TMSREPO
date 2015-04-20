package com.accion.tms.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="project")
public class Project
{
	private String startDate;
	private String endDate;
	@Id
	private String id;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectOwner() {
		return projectOwner;
	}
	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}
	public List<String> getProjectResources() {
		return projectResources;
	}
	public void setProjectResources(List<String> projectResources) {
		this.projectResources = projectResources;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String projectName;
	private String projectOwner;
	private List<String> projectResources;
	private List<String> projectActivities;
	public List<String> getProjectActivities() {
		return projectActivities;
	}
	public void setProjectActivities(List<String> projectActivities) {
		this.projectActivities = projectActivities;
	}
	private String description;
	
	
	
	

}