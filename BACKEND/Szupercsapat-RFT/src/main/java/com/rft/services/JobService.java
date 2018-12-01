package com.rft.services;

import java.util.List;

import com.rft.entities.JobOfferer;
import com.rft.entities.DTOs.JobCategoryDTO;
import com.rft.entities.DTOs.JobDTO;

public interface JobService {
	void addJob(JobDTO jobDTO);
	void removeJob(Integer jobId);
	void updateJob(int jobId,JobDTO jobDTO);
	void removeAllJobs(JobOfferer offerer) ;
	void removeAllJobs(String username) ;
	List<JobDTO> getAllByCategories(JobCategoryDTO categoryDTO);
	Integer getJobsByCategoriesCount(JobCategoryDTO categoryDTO);
	
	/**
	 * Page starts at 1
	 * @param categoryDTO
	 * @param page
	 * @param size
	 * @return
	 */
	List<JobDTO> getJobsByCategoriesWithPaging(JobCategoryDTO categoryDTO, Integer page, Integer size);
}
