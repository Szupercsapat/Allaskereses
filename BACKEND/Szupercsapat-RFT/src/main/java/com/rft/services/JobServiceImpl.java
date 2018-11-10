package com.rft.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rft.entities.JobOfferer;
import com.rft.entities.User;
import com.rft.entities.DTOs.JobDTO;
import com.rft.entities.DTOs.JobOffererDTO;
import com.rft.exceptions.UserDoesNotExistsException;
import com.rft.repos.JobCategoryRepository;
import com.rft.repos.JobOffererRepository;
import com.rft.repos.JobRepository;
import com.rft.repos.UserRepository;

@Service
public class JobServiceImpl implements JobService {
	@Autowired
	private JobCategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JobOffererRepository offererRepository;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private UserService userService;

	@Override
	public void addJob(JobDTO jobDTO) { //POSTMAN POST
		JobOfferer offerer = getOfferer(jobDTO.getUsername());
	}

	@Override
	public void removeJob(JobDTO jobDTO) {//POSTMAN DELETE
		JobOfferer offerer = getOfferer(jobDTO.getUsername());

	}

	@Override
	public void updateJob(JobDTO jobDTO) { //POSTMAN PUT
		JobOfferer offerer = getOfferer(jobDTO.getUsername());

	}
	
	private JobOfferer getOfferer(String username)
	{
		User user = userService.checkUserValues(username);
		JobOfferer offerer = offererRepository.findByUser(user);
		return offerer;
	}
	
}
