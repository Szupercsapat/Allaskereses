package com.rft.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.School;
import com.rft.entities.DTOs.JobCategoryDTO;
import com.rft.entities.DTOs.JobSeekerDTO;
import com.rft.entities.DTOs.JobSeekerDTOCollectionless;
import com.rft.entities.DTOs.SchoolDTO;
import com.rft.entities.DTOs.WorkPlaceDTO;

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

	List<JobSeekerDTOCollectionless> getSeekerDTOs(Integer page, Integer size);

	JobSeekerDTOCollectionless getSeekerDTO(Integer seekerId);

	List<SchoolDTO> getSchools(Integer seekerId);

	List<WorkPlaceDTO> getWorkPlaces(Integer seekerId);

	JobCategoryDTO getCategoryIds(Integer seekerId);
}
