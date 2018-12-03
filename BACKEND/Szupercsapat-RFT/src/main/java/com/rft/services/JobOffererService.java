package com.rft.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.DTOs.JobCategoryDTO;
import com.rft.entities.DTOs.JobOffererDTO;
import com.rft.entities.DTOs.JobOffererDTOCollectionless;

public interface JobOffererService {
	void updateProfile(JobOffererDTO offererDTO);
	void saveImage(String username, MultipartFile imageFile);
	 byte[] getProfileImage(String username);
	JobCategoryDTO getCategoryIds(Integer offererId);
	JobOffererDTOCollectionless getOffererDTO(Integer offererId);
	List<JobOffererDTOCollectionless> getOffererDTOs(Integer page, Integer size);
}
