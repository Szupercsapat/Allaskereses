package com.rft.repos;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;

@Repository("CustomJobRepositoryMethodsImpl")
public interface JobRepositoryCustom {
	List<Job> findByJobCategories(List<Integer> cIds);
}
