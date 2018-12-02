package com.rft.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.User;

public interface JobOffererRepository extends JpaRepository<JobOfferer, Integer> {

	JobOfferer findByUser(User user);
	
	@Query("select js from JobOfferer js join js.categories jc where jc.id in :catIds")
	List<JobOfferer> findByCategories(List<Integer> catIds);
}
