package com.rft.services;

import java.util.List;

import com.rft.entities.DTOs.JobDTO;

public interface JobService {
	void addJob(JobDTO jobDTO);
	void removeJob(Integer jobId);
	void updateJob(int jobId,JobDTO jobDTO);
	void removeAllJobs();
}
