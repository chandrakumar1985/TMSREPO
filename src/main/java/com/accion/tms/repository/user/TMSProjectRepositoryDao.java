package com.accion.tms.repository.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.accion.tms.entity.ProjectTMS;


public interface TMSProjectRepositoryDao extends CrudRepository<ProjectTMS, String> {
	
	public List<ProjectTMS> findByApproverNameAndSubmitted(String approverName , boolean isSubmitted);
	public ProjectTMS findById(String id);
}
