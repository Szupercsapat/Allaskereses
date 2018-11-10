package com.rft.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Job extends EntityBase {
	String name;
	@Lob
	@Column(name = "description", length = 2048)
	private String description;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) // persist https://stackoverflow.com/a/48421327
	@JoinTable(name = "job_job_category", joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Set<JobCategory> categories;

	@ManyToOne
	@JoinColumn(name = "offerer_id")
	private JobOfferer offerer;

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

	public Set<JobCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<JobCategory> categories) {
		this.categories = categories;
	}

	public JobOfferer getOfferer() {
		return offerer;
	}

	public void setOfferer(JobOfferer offerer) {
		this.offerer = offerer;
	}

	// https://stackoverflow.com/a/48421327
	public void addCategories(JobCategory category) {
		if(category == null)
			return;
		if (categories == null)
			categories = new HashSet<JobCategory>();
		if (!categories.contains(category)) {
			categories.add(category);
			category.getJobs().add(this);
		}
	}

	// https://stackoverflow.com/a/48421327
	public void removeCategories(JobCategory category) {
		if (categories == null)
			return;
		categories.remove(category);
		category.getJobs().remove(this);
	}
}
