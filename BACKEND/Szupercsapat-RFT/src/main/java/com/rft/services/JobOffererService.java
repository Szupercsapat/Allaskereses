package com.rft.services;

import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.DTOs.JobOffererDTO;

public interface JobOffererService {
	void updateProfile(JobOffererDTO offererDTO);
	void saveImage(String username, MultipartFile imageFile);
	 byte[] getProfileImage(String username);
}
