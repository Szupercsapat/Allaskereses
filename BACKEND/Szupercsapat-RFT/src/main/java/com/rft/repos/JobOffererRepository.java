package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.JobOfferer;

public interface JobOffererRepository extends JpaRepository<JobOfferer, Long> {

	
}
