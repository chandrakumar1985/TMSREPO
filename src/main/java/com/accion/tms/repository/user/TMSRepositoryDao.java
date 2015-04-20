package com.accion.tms.repository.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.accion.tms.entity.Project;
import com.accion.tms.entity.ProjectTMS;
import com.accion.tms.entity.UserTMS;


public interface TMSRepositoryDao extends CrudRepository<UserTMS, String> {
	public UserTMS findByTmsDateAndUserName(String tmsDate, String userName);
	public UserTMS findById(String id);
	public List<UserTMS> findByProjectTMS_ApproverNameAndSubmitted(String approverName , boolean isSubmitted);

}
