package com.accion.tms.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonSerialize
public class WeeklyTMSVo
{
	
	List<WeeklyProjectTMSVo> weeklyProjectTMS;

	public List<WeeklyProjectTMSVo> getWeeklyProjectTMS() {
		return weeklyProjectTMS;
	}

	public void setWeeklyProjectTMS(List<WeeklyProjectTMSVo> weeklyProjectTMS) {
		this.weeklyProjectTMS = weeklyProjectTMS;
	}
}