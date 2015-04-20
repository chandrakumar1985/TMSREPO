package com.accion.tms.repository;


import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.accion.tms.entity.ProjectActivity;
import com.accion.tms.entity.User;
import com.accion.tms.repository.user.ProjectActivityRepositoryDao;
import com.accion.tms.repository.user.TMSRepositoryDao;
import com.accion.tms.repository.user.UserRepositoryDao;


/**
 * Initialize the database with some test entries.
 * 
 * @author Philip W. Sorst <philip@sorst.net>
 */
public class DataBaseInitializer
{

	@Resource
	private UserRepositoryDao userRepositoryDao;
	
	@Resource
	private TMSRepositoryDao tMSRepositoryDao;
	
	@Resource
	private ProjectActivityRepositoryDao projectActivityRepositoryDao;

	@Resource
	private PasswordEncoder passwordEncoder;


	protected DataBaseInitializer()
	{
		/* Default constructor for reflection instantiation */
	}


	/**public DataBaseInitializer(UserDao userDao, NewsEntryDao newsEntryDao, PasswordEncoder passwordEncoder)
	{
		this.userDao = userDao;
		this.newsEntryDao = newsEntryDao;
		this.passwordEncoder = passwordEncoder;
	}**/


	public void initDataBase()
	{
		this.userRepositoryDao.deleteAll();
		this.tMSRepositoryDao.deleteAll();
		User userUser = new User("user", this.passwordEncoder.encode("user"));
		userUser.addRole("user");
		userUser.setId(UUID.randomUUID().toString());
		this.userRepositoryDao.save(userUser);
		
		User userUser1 = new User("hanuman", this.passwordEncoder.encode("hanuman"));
		userUser1.addRole("user");
		userUser1.setId(UUID.randomUUID().toString());
		this.userRepositoryDao.save(userUser1);

		User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
		adminUser.addRole("user");
		adminUser.addRole("admin");
		adminUser.setId(UUID.randomUUID().toString());
		this.userRepositoryDao.save(adminUser);
		
		User adminUser1 = new User("hanu", this.passwordEncoder.encode("hanu"));
		adminUser1.addRole("user");
		adminUser1.addRole("admin");
		adminUser1.setId(UUID.randomUUID().toString());
		this.userRepositoryDao.save(adminUser1);
		
		
		this.projectActivityRepositoryDao.deleteAll();
		ProjectActivity projectActivity = new ProjectActivity();
		projectActivity.setActivityDesc("Leave activity");
		projectActivity.setName("Leave");
		projectActivity.setId(UUID.randomUUID().toString());
		this.projectActivityRepositoryDao.save(projectActivity);
		
		ProjectActivity projectActivity1 = new ProjectActivity();
		projectActivity1.setActivityDesc("Coding activity");
		projectActivity1.setName("Coding");
		projectActivity1.setId(UUID.randomUUID().toString());
		this.projectActivityRepositoryDao.save(projectActivity1);
		
		ProjectActivity projectActivity2 = new ProjectActivity();
		projectActivity2.setActivityDesc("Unit Testing activity");
		projectActivity2.setName("Unit Testing");
		projectActivity2.setId(UUID.randomUUID().toString());
		this.projectActivityRepositoryDao.save(projectActivity2);
		
		ProjectActivity projectActivity3 = new ProjectActivity();
		projectActivity3.setActivityDesc("Functional Testing activity");
		projectActivity3.setName("Functional Testing");
		projectActivity3.setId(UUID.randomUUID().toString());
		this.projectActivityRepositoryDao.save(projectActivity3);
		
	}

}