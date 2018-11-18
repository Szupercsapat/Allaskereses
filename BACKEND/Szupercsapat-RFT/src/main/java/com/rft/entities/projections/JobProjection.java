package com.rft.entities.projections;

import java.util.Set;

import org.springframework.data.rest.core.config.Projection;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;

@Projection(name="job", types= {Job.class})
public interface JobProjection {
	Integer getId();
	String getName();
	String getDescription();
	Set<JobCategory> getCategories();
}
