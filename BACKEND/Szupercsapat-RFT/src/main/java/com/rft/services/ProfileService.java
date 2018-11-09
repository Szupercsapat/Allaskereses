package com.rft.services;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.User;
import com.rft.entities.DTOs.JobOffererDTO;
import com.rft.entities.DTOs.JobSeekerDTO;

public interface ProfileService {

	void updateSeekerProfile(JobSeekerDTO seekerDTO);
	void updateOffererProfile(JobOffererDTO offererDTO);
	void checkIfActivated(User user);
	void saveImage(String profileType, String username, MultipartFile imageFile);
	byte[] getProfileImage(String username, String profileType);
	byte[] getCVinPDF(JobSeekerDTO seekerDTO);
}
