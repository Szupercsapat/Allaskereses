package com.rft.entities.DTOs;

import java.util.List;

public class JobDTO {
	Integer jobId;
	String username;
	String name;
	String description;
	Integer offererId;
	
	
	public Integer getOffererId() {
		return offererId;
	}
	public void setOffererId(Integer offererId) {
		this.offererId = offererId;
	}
	List<Integer> categories;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Integer> getCategories() {
		return categories;
	}
	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	
}
