package com.accion.tms.repository.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.accion.tms.entity.Project;


public interface ProjectRepositoryDao extends CrudRepository<Project, String> {
	
	
	public Project findByProjectName(String projectName);
	public Project findById(String id);
	public List<Project> findByProjectResources(String resource);

}
