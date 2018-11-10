package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.JobOfferer;
import com.rft.entities.User;

public interface JobOffererRepository extends JpaRepository<JobOfferer, Integer> {

	JobOfferer findByUser(User user);
}
