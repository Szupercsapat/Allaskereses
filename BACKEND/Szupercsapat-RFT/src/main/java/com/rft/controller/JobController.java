package com.rft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rft.entities.DTOs.JobDTO;
import com.rft.services.JobService;

@RestController
@RequestMapping("/job")
public class JobController {
	
	@Autowired
	JobService jobService;
	
	@PostMapping 
	public void create(@RequestBody JobDTO jobDTO)
	{
		jobService.addJob(jobDTO);
	}
	
	@DeleteMapping("/{jobId}")
	public void deleteOne(@PathVariable("jobId") int jobId)
	{
		jobService.removeJob(jobId);
	}
	
	@DeleteMapping
	public void deleteAll()
	{
		jobService.removeAllJobs();
	}
	
	@PutMapping("/{jobId}")
	public void update(@PathVariable("jobId") int jobId,@RequestBody JobDTO jobDTO)
	{
		jobService.updateJob(jobId,jobDTO);
	}
	
}
