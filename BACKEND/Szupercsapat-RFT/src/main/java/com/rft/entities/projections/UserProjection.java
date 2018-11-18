package com.rft.entities.projections;

import java.util.Set;

import org.springframework.data.rest.core.config.Projection;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.User;

@Projection(name = "user", types = { User.class })
public interface UserProjection {
	
	String getUsername();
	String getEmail();
	JobSeeker getJobSeeker();
	JobOfferer getJobOfferer();
}
