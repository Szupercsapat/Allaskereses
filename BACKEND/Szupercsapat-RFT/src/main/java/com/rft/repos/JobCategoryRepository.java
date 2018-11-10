package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import java.lang.Integer;
import java.util.List;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer> {
	JobCategory findById(Integer id);
}
