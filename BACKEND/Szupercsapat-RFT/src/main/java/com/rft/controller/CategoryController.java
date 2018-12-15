package com.rft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rft.entities.DTOs.JobCategoryDTOMin;
import com.rft.repos.JobCategoryRepository;
import com.rft.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	JobCategoryRepository categoryRepository;
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/getById/{id}")
	public JobCategoryDTOMin getById(@PathVariable("id") Integer id)
	{
		return categoryService.getById(id);
	}
}
