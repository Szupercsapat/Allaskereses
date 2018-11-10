package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import java.lang.Integer;
import java.util.List;
import java.util.Set;

public interface JobRepository extends JpaRepository<Job, Integer> {
	List<Job> findByCategories(Set categories);
	Job findById(Integer id);
}
