package com.rft.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User extends EntityBase{

	private String username;
	private String email;
	@JsonIgnore
	private String password;

	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="user_role",
	joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="role_id",referencedColumnName="id"))
	private Set<Role> roles;
	
	@OneToOne(mappedBy="user")
	private JobOfferer jobOfferer;

	@OneToOne(mappedBy="user")
	private JobSeeker jobSeeker;
	
	@Column(columnDefinition = "TINYINT(1)")
	private boolean activated;
	
	
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public boolean getActivated() {
		return activated;
	}


	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public JobOfferer getJobOfferer() {
		return jobOfferer;
	}

	public void setJobOfferer(JobOfferer jobOfferer) {
		this.jobOfferer = jobOfferer;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}

	public void addRole(Role role)
	{
		if(roles == null)
			roles = new HashSet<Role>();
		roles.add(role);
	}
	
	public Integer getResourceId()
	{
		return id;
	}
	
	
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + username + ", password=" + password + ", roles=" + roles + "]";
	}
}
