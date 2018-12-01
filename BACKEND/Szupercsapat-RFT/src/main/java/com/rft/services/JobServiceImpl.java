package com.rft.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.User;
import com.rft.entities.DTOs.JobCategoryDTO;
import com.rft.entities.DTOs.JobDTO;
import com.rft.entities.DTOs.JobOffererDTO;
import com.rft.exceptions.IdIsMissingException;
import com.rft.exceptions.JobDoesNotExistsException;
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
	public void addJob(JobDTO jobDTO) {
		JobOfferer offerer = getOfferer(jobDTO.getUsername());
		Job job = new Job();
		job.setOfferer(offerer);
		mapJobDTOtoJob(jobDTO, job);
		offerer.addJob(job);
		offererRepository.save(offerer);
	}

	@Override
	public void removeJob(Integer jobId) {
		if (jobId == null)
			throw new IdIsMissingException("Job ID is missing for delete!");

		Job job = jobRepository.findById(jobId);
		if (job == null)
			throw new JobDoesNotExistsException("Job does not exists!");
		Set<JobCategory> categories = job.getCategories();

		// https://stackoverflow.com/a/18448699
		Iterator<JobCategory> categoryIterator = categories.iterator();
		while (categoryIterator.hasNext()) {
			JobCategory category = categoryIterator.next();
			categoryIterator.remove();
		}

		JobOfferer offerer = job.getOfferer();
		offerer.removeJob(job);

		jobRepository.delete(job);
	}

	@Override
	public void updateJob(int jobId, JobDTO jobDTO) {
		Job job = jobRepository.findById(jobId);
		JobOfferer offerer = job.getOfferer();

		mapJobDTOtoJob(jobDTO, job);
		offererRepository.save(offerer);
	}

	@Override
	public void removeAllJobs(JobOfferer offerer) {
		for (int jobId : jobRepository.findAll().stream().filter(j -> j.getOfferer().equals(offerer))
				.mapToInt(j -> j.getId()).toArray()) {
			removeJob(jobId);
		}
	}

	@Override
	public void removeAllJobs(String username) {

		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		JobOfferer offerer = offererRepository.findByUser(user);

		removeAllJobs(offerer);
	}

	private JobOfferer getOfferer(String username) {
		User user = userService.checkUserValues(username);
		JobOfferer offerer = offererRepository.findByUser(user);
		return offerer;
	}

	private void mapJobDTOtoJob(JobDTO jobDTO, Job job) {

		if (jobDTO.getName() != null)
			job.setName(jobDTO.getName());
		if (jobDTO.getDescription() != null)
			job.setDescription(jobDTO.getDescription());

		mapCategoriesTojob(jobDTO.getCategories(), job);
	}

	private void mapCategoriesTojob(List<Integer> categories, Job job) {
		if (categories == null || categories.isEmpty())
			return;
		for (int categoryId : categories) {
			if (categoryId < 0) {
				JobCategory category = categoryRepository.findById(categoryId * (-1));
				if (category != null)
					job.removeCategories(category);
			} else {
				JobCategory category = categoryRepository.findById(categoryId);
				if (category != null) {
					job.addCategories(category);
				}

			}
		}
	}

	private List<JobCategory> mapCategoryDTOtoCategories(JobCategoryDTO categoryDTO) {
		List<JobCategory> categories = new ArrayList<JobCategory>();

		for (int catId : categoryDTO.getIds()) {
			JobCategory jobCategory = categoryRepository.findById(catId);
			if (jobCategory != null)
				categories.add(jobCategory);
		}

		return categories;
	}

	private JobDTO mapJobToJobDTO(Job job) {
		if (job == null)
			return null;

		JobDTO dto = new JobDTO();

		dto.setUsername(job.getOfferer().getUser().getUsername());
		dto.setDescription(job.getDescription());
		dto.setName(job.getName());
		dto.setJobId(job.getId());

		List<Integer> catIds = new ArrayList<Integer>();

		for (JobCategory cat : job.getCategories()) {
			catIds.add(cat.getId());
		}

		dto.setCategories(catIds);

		return dto;
	}

	private List<JobDTO> mapJobsToJobDTO(List<Job> jobs) {
		List<JobDTO> dtos = new ArrayList<JobDTO>();

		for (Job job : jobs) {
			JobDTO dto = mapJobToJobDTO(job);
			if (dto != null)
				dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<JobDTO> getAllByCategories(JobCategoryDTO categoryDTO) {

		if (categoryDTO == null)
			return null;
		if (categoryDTO.getIds() == null)
			return null;

		List<JobDTO> jobDtos = new ArrayList<JobDTO>();

		List<JobCategory> categories = mapCategoryDTOtoCategories(categoryDTO);

		List<Job> jobs = new ArrayList<Job>();

		for (Job job : jobRepository.findAll()) {
			for (JobCategory category : categories) {
				if (job.getCategories().contains(category)) {
					jobs.add(job);
					break;
				}
			}
		}

		jobDtos = mapJobsToJobDTO(jobs);

		return jobDtos;
	}

	@Override
	public Integer getJobsByCategoriesCount(JobCategoryDTO categoryDTO) {

		return getAllByCategories(categoryDTO).size();
	}

	@Override
	public List<JobDTO> getJobsByCategoriesWithPaging(JobCategoryDTO categoryDTO, Integer page, Integer size) {

		if(page<0 || size < 0)
			return new ArrayList<JobDTO>();
		
		List<JobDTO> dtos = getAllByCategories(categoryDTO);
		List<JobDTO> pagedDtos = new ArrayList<JobDTO>();

		int count = dtos.size();
		int firstElement = page * size;
		int endElement = size + page * size;

		if (firstElement > count)
			return new ArrayList<JobDTO>();

		for (int i = firstElement; i < endElement; i++) {
			if (i > count - 1)
				break;
			pagedDtos.add(dtos.get(i));
		}

		return pagedDtos;
	}
}
