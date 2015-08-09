package com.accion.tms.repository.user;

import org.springframework.data.repository.CrudRepository;

import com.accion.tms.entity.User;


public interface UserRepositoryDao extends CrudRepository<User, String> {
	
	
	public User findByUsername(String username);
	public User findById(String _id);

}
