package com.accion.tms.repository.user;

import org.springframework.data.repository.CrudRepository;

import com.accion.tms.entity.ProjectActivity;


public interface ProjectActivityRepositoryDao extends CrudRepository<ProjectActivity, String> {
	
	
	public ProjectActivity findByName(String name);
	public ProjectActivity findById(String id);

}
