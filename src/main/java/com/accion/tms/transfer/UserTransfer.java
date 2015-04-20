package com.accion.tms.transfer;

import java.util.Map;


public class UserTransfer
{

	private final String name;
	private final String id;

	private final Map<String, Boolean> roles;


	public UserTransfer(String userName, Map<String, Boolean> roles, String id)
	{
		this.name = userName;
		this.roles = roles;
		this.id = id;
	}


	public String getId() {
		return id;
	}


	public String getName()
	{
		return this.name;
	}


	public Map<String, Boolean> getRoles()
	{
		return this.roles;
	}

}