package com.rft.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;
import com.rft.entities.DTOs.JobCategoryDTOMin;
import com.rft.entities.DTOs.JobDTO;
import com.rft.exceptions.JobCategoryDoesNotExistsException;
import com.rft.repos.JobCategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	JobCategoryRepository categoryRepository;

	@Override
	public JobCategoryDTOMin getById(Integer id) {
		
		JobCategory category = categoryRepository.findById(id);//itt halmeg
		
		if (category == null)
			throw new JobCategoryDoesNotExistsException("The category does not exists");

		JobCategoryDTOMin minDto = mapCategoryToMinDTO(category);

		return minDto;
	}

	private JobCategoryDTOMin mapCategoryToMinDTO(JobCategory category) {

		JobCategoryDTOMin dto = new JobCategoryDTOMin();

		dto.setAbout(category.getAbout());
		dto.setActive(category.isActive());
		dto.setCategoryId(category.getId());
		dto.setParentId(category.getParentId());

		return dto;
	}

}
