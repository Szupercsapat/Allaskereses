package com.rft.entities.DTOs;

public class SafeUserDTO {
	private String username;
	private Integer id;
	private boolean active;
	private String email;
	private Integer seekerId;
	private Integer offererId;
	
	public Integer getSeekerId() {
		return seekerId;
	}
	public void setSeekerId(Integer seekerId) {
		this.seekerId = seekerId;
	}
	public Integer getOffererId() {
		return offererId;
	}
	public void setOffererId(Integer offererId) {
		this.offererId = offererId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
