package com.rft.entities.DTOs;

import java.util.Arrays;
import java.util.List;

import com.rft.entities.School;
import com.rft.entities.WorkPlace;

public class JobOffererDTOCollectionless{
	private String username;
	private String firstName;
	private String lastName;
	private String aboutMe;
	private Integer id;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
