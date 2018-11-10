package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;
import java.lang.Integer;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {
	List<Job> findByCategories(List<JobCategory> categories);
	Job findById(Integer id);
}
