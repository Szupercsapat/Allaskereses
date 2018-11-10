package com.rft.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rft.entities.DTOs.JobDTO;

@RestController
@RequestMapping("/job")
public class JobController {
	
	@PostMapping("/") 
	public void create(@RequestBody JobDTO jobDTO)
	{
		
	}
	
	@DeleteMapping("/") 
	public void delete(@RequestBody JobDTO jobDTO)
	{
		
	}
	
	@PutMapping("/") 
	public void update(@RequestBody JobDTO jobDTO)
	{
		
	}
	
}
