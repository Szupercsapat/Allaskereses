package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.User;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> {

	JobSeeker findByUser(User user);
}
