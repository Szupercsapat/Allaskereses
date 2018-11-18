package com.rft.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="job_category")
public class JobCategory extends EntityBase{

	@ManyToMany(mappedBy = "categories")
	Set<JobSeeker> seekers;
	
	@ManyToMany(mappedBy = "categories")
	Set<JobOfferer> offerers;
	
	@ManyToMany(mappedBy = "categories")
	Set<Job> jobs;
	
	@Column(name="job_name")
	private String jobName;
	
	@Column(name="parent_id")
	private Integer parentId;
	
	private String about;
	
	@Column(columnDefinition = "TINYINT(1)")
	private boolean active;

	public Set<JobSeeker> getSeekers() {
		return seekers;
	}

	public void setSeekers(Set<JobSeeker> seekers) {
		this.seekers = seekers;
	}

	public Set<JobOfferer> getOfferers() {
		return offerers;
	}

	public void setOfferers(Set<JobOfferer> offerers) {
		this.offerers = offerers;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Job> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}
	public Integer getResourceId()
	{
		return id;
	}
}
