package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {

	
}
