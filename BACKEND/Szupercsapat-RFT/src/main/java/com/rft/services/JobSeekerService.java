package com.rft.services;

import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.DTOs.JobSeekerDTO;

public interface JobSeekerService {
	void updateProfile(JobSeekerDTO seekerDTO);
	byte[] getCVinPDF(JobSeekerDTO seekerDTO);
	void saveImage(String username, MultipartFile imageFile);
	byte[] getProfileImage(String username);
}
