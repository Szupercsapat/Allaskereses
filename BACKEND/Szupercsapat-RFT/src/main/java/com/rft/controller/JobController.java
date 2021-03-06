package com.rft.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;
import com.rft.entities.DTOs.JobCategoryDTO;
import com.rft.entities.DTOs.JobDTO;
import com.rft.entities.projections.JobProjection;
import com.rft.repos.JobCategoryRepository;
import com.rft.repos.JobRepository;
import com.rft.services.JobService;

@RestController
@RequestMapping("/job")
public class JobController {
	
	@Autowired
	JobService jobService;
	@Autowired
	private ProjectionFactory projectionFactory;
	@Autowired
	private JobRepository jobRepo;
	@Autowired
	private JobCategoryRepository categoryRepo;
	
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
	
	@DeleteMapping("/user/{username}")
	public void deleteAll(@PathVariable("username") String username)
	{
		jobService.removeAllJobs(username);
	}
	
	@PutMapping("/{jobId}")
	public void update(@PathVariable("jobId") int jobId,@RequestBody JobDTO jobDTO)
	{
		jobService.updateJob(jobId,jobDTO);
	}
	
	@GetMapping("/getAllCount")
	public Integer getAllJobsCount()
	{
		return jobService.getAllJobsCount();
	}
	
	@GetMapping("/getAll/page/{page}/size/{size}")
	public List<JobDTO> getAllWithPaging(
			@PathVariable("page") Integer page
			,@PathVariable("size") Integer size)
	{
		return jobService.getAllWithPaging(page,size);
	}
	
	
	@GetMapping("/getByUsernameCount/username/{username}")
	public Integer getByUsernameCount( @PathVariable("username") String username )
	{
		return jobService.getByUsernameCount(username);
	}
	
	@GetMapping("/getByUsername/username/{username}/page/{page}/size/{size}")
	public List<JobDTO> getByUsername(
			@PathVariable("username") String username,
			@PathVariable("page") Integer page
			,@PathVariable("size") Integer size)
	{
		return jobService.getByUsernameWithPaging(username,page,size);
	}
	
	@GetMapping("/getByCategories")
	public @ResponseBody List<JobDTO> getJobsByCategories(@RequestBody JobCategoryDTO categoryDTO)
	{
		return jobService.getAllByCategories(categoryDTO);
	}
	
	@GetMapping("/getByCategoriesCount")
	public Integer getJobsByCategoriesCount(@RequestBody JobCategoryDTO categoryDTO)
	{
		return jobService.getJobsByCategoriesCount(categoryDTO);
	}

	@GetMapping("/getByCategories/page/{page}/size/{size}")
	public List<JobDTO> getJobsByCategoriesWithPaging(@RequestBody JobCategoryDTO categoryDTO
			,@PathVariable("page") Integer page
			,@PathVariable("size") Integer size)
	{
		return jobService.getJobsByCategoriesWithPaging(categoryDTO,page,size);
	}

	@GetMapping("/getByOffererIdCount/offererId/{offererId}")
	public Integer getByOffererIdCount( @PathVariable("offererId") Integer offererId )
	{
		return jobService.getByOffererIdCount(offererId);
	}
	
	@GetMapping("/getByOffererId/offererId/{offererId}/page/{page}/size/{size}")
	public List<JobDTO> getByOffererId(
			@PathVariable("offererId") Integer offererId,
			@PathVariable("page") Integer page
			,@PathVariable("size") Integer size)
	{
		return jobService.getByOffererIdWithPaging(offererId,page,size);
	}
	
}
