package com.rft.entities.DTOs;

import java.util.Arrays;
import java.util.List;

import com.rft.entities.School;
import com.rft.entities.WorkPlace;

public class JobSeekerDTO{
	private String username;
	private String firstName;
	private String lastName;
	private String aboutMe;
	private byte[] profileImage;
	List<Integer> categories;
	List<School> schools;
	List<WorkPlace> workPlaces;
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
	public byte[] getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
	public List<Integer> getCategories() {
		return categories;
	}
	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}
	public List<School> getSchools() {
		return schools;
	}
	public void setSchools(List<School> schools) {
		this.schools = schools;
	}
	public List<WorkPlace> getWorkPlaces() {
		return workPlaces;
	}
	public void setWorkPlaces(List<WorkPlace> workPlaces) {
		this.workPlaces = workPlaces;
	}
	
}
