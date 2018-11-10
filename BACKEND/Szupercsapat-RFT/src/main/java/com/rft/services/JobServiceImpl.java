package com.rft.services;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.User;
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
	public void removeAllJobs() {
		for (int jobId : jobRepository.findAll().stream().mapToInt(j -> j.getId()).toArray()) {
			removeJob(jobId);
		}
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
}
