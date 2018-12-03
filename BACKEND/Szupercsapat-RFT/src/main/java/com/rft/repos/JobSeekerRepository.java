package com.rft.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.User;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> {

	JobSeeker findByUser(User user);
	JobSeeker findById(Integer id);
	
	@Query("select js from JobSeeker js join js.categories jc where jc.id in :catIds")
	List<JobSeeker> findByCategories(List<Integer> catIds);
}
