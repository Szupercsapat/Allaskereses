package com.rft.services;

import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.DTOs.JobSeekerDTO;

public interface JobSeekerService {
	/***
	 * Updates the profile with the given DTO.
	 * The DTO must containt a username.
	 * Everything else init is optional, such as categories, aboutMe,..etc
	 * @param seekerDTO
	 */
	void updateProfile(JobSeekerDTO seekerDTO);
	
	/**
	 * Returns the CV from the seeker profile by the username. 
	 * @param username
	 * @return
	 */
	byte[] getCVinPDF(String username);
	
	/**
	 * Saves the image to the given user, who is referenced by the username.
	 * If the file size exceeds to MB-s, then org.springframework.web.multipart.MultipartException happens.
	 * @param username
	 * @param imageFile
	 */
	void saveImage(String username, MultipartFile imageFile);
	
	byte[] getProfileImage(String username);
}
