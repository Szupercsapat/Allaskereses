package com.rft.services;

import com.rft.entities.DTOs.JobCategoryDTOMin;

public interface CategoryService {

	JobCategoryDTOMin getById(Integer id);

}
