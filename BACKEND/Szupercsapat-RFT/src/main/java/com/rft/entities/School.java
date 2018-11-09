package com.rft.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name="school")
public class School extends TableRowBase {

	@ManyToOne
	@JoinColumn(name = "seeker_id")
	private JobSeeker seeker;

	public JobSeeker getSeeker() {
		return seeker;
	}

	public void setSeeker(JobSeeker seeker) {
		this.seeker = seeker;
	}
}
