package com.accion.tms.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Document(collection="user")
public class User implements Entity, UserDetails
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8236229971783557190L;

	@Id
	private String id;

	private String username;

	private String password;
	
	private boolean enabled = true;
	
	private boolean credentialsNonExpired = true;
	
	private boolean accountNonLocked = true;
	
	private boolean accountNonExpired = true;

	// = new HashSet<GrantedAuthority>();
	
	private Set<String> roles = new HashSet<String>();
	private List<String> projects;


	protected User()
	{
		/* Reflection instantiation */
	}


	public User(String name, String passwordHash)
	{
		
		this.username = name;
		this.password = passwordHash;
	}


	public String getId()
	{
		return this.id;
	}


	public void setId(String id)
	{
		this.id = id;
	}


	public String getName()
	{
		return this.username;
	}


	public void setName(String name)
	{
		this.username = name;
	}


	public Set<String> getRoles()
	{
		return this.roles;
	}


	public void setRoles(Set<String> roles)
	{
		this.roles = roles;
	}


	public void addRole(String role)
	{
		this.roles.add(role);
	}
	public List<String> getProjects() {
		return projects;
	}
	public void setProjects(List<String> projects) {
		this.projects = projects;
	}


	@Override
	public String getPassword()
	{
		return this.password;
	}


	public void setPassword(String password)
	{
		this.password = password;
	}


	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		Set<String> roles = this.getRoles();

		if (roles == null) {
			return Collections.emptyList();
		}

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(); 
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}

		return authorities;
	}


	@Override
	public String getUsername()
	{
		return this.username;
	}


	@Override
	public boolean isAccountNonExpired()
	{
		return accountNonExpired;
	}


	@Override
	public boolean isAccountNonLocked()
	{
		return accountNonLocked;
	}


	@Override
	public boolean isCredentialsNonExpired()
	{
		return credentialsNonExpired;
	}


	@Override
	public boolean isEnabled()
	{
		return enabled;
	}

}