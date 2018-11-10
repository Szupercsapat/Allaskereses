package com.rft.services;

import java.util.List;

import com.rft.entities.DTOs.JobDTO;

public interface JobService {
	void addJob(JobDTO jobDTO);
	void removeJob(JobDTO jobDTO);
	void updateJob(JobDTO jobDTO);
}
