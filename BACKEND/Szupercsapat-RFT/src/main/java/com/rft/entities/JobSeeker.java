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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "job_seeker")
public class JobSeeker extends EntityBase {

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "job_seeker_job_category", joinColumns = @JoinColumn(name = "seeker_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
	private Set<JobCategory> categories;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "seeker", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<School> schools;

	@OneToMany(mappedBy = "seeker", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<WorkPlace> workPlaces;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Lob
	@Column(name = "about_me", length = 1024)
	private String aboutMe;

	@JsonIgnore
	@Lob
	@Column(name = "profile_image")
	private byte[] profileImage;

	public void addCategory(JobCategory category) {
		if (categories == null)
			categories = new HashSet<JobCategory>();
		categories.add(category);
	}

	public void removeCategory(JobCategory category) {
		if (categories == null)
			return;
		categories.remove(category);
	}

	public Set<JobCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<JobCategory> categories) {
		this.categories = categories;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Set<School> getSchools() {
		return schools;
	}

	public void setSchools(Set<School> schools) {
		this.schools = schools;
	}

	public Set<WorkPlace> getWorkPlaces() {
		return workPlaces;
	}

	public void setWorkPlaces(Set<WorkPlace> workPlaces) {
		this.workPlaces = workPlaces;
	}

	public void setToNullAllSchools() {
		for (School school : schools) {
			school.setSeeker(null);
		}
	}
	public void setToNullAllWorkPlaces() {
		for (WorkPlace workPlace : workPlaces) {
			workPlace.setSeeker(null);
		}
	}
	public String Username()
	{
		return user.getUsername();
	}
}
