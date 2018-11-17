package com.rft.services;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.School;
import com.rft.entities.User;
import com.rft.entities.WorkPlace;
import com.rft.entities.DTOs.JobSeekerDTO;
import com.rft.exceptions.BadProfileTypeException;
import com.rft.exceptions.BadYearException;
import com.rft.exceptions.UserDoesNotExistsException;
import com.rft.repos.JobCategoryRepository;
import com.rft.repos.JobOffererRepository;
import com.rft.repos.JobSeekerRepository;
import com.rft.repos.SchoolRepository;
import com.rft.repos.UserRepository;
import com.rft.repos.WorkPlaceRepository;
import com.rft.utils.Utils;
import com.rft.utils.pdfGen.GeneratePDF;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {

	@Autowired
	private JobCategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JobSeekerRepository seekerRepository;
	@Autowired
	private SchoolRepository schoolRepository;
	@Autowired
	private WorkPlaceRepository workPlaceRepository;
	@Autowired
	private UserService userService;

	@Override
	public void updateProfile(JobSeekerDTO seekerDTO) {

		User user = userService.checkUserValues(seekerDTO.getUsername());
		JobSeeker seekerFromDb = seekerRepository.findByUser(user);

		mapSchools(seekerDTO, seekerFromDb);
		mapWorkPlaces(seekerDTO, seekerFromDb);
		mapNonNullFields(seekerDTO, seekerFromDb);
		updateCategories(seekerDTO, seekerFromDb);
		seekerRepository.save(seekerFromDb);
	}

	private void updateCategories(JobSeekerDTO seekerDTO, JobSeeker seekerFromDb) {
		if (seekerDTO.getCategories() == null)
			return;

		for (int categoryId : seekerDTO.getCategories()) {
			if (categoryId < 0) {
				JobCategory category = categoryRepository.findById(categoryId * (-1));
				seekerFromDb.removeCategory(category);
			} else {
				JobCategory category = categoryRepository.findById(categoryId);
				if (category != null) {
					if (!seekerFromDb.getCategories().contains(category)) {
						seekerFromDb.addCategory(category);
					}
				}
			}
		}
	}

	private void mapSchools(JobSeekerDTO seekerDTO, JobSeeker seekerFromDb) {
		if (seekerDTO.getSchools() == null)
			return;

		Set<School> schools = new HashSet<School>();

		for (School school : seekerDTO.getSchools()) {
			checkSchoolFields(school);
			Utils.checkTheNumberPositive4DigitLong(school.getFromYear());
			Utils.checkTheNumberPositive4DigitLong(school.getToYear());
			school.setSeeker(seekerFromDb);
			schools.add(school);
		}
		seekerFromDb.setToNullAllSchools();
		seekerRepository.save(seekerFromDb);
		seekerFromDb.setSchools(schools);
		schoolRepository.deleteAll();
	}

	private void checkSchoolFields(School school) {
		if (school.getFromYear() == null || school.getToYear() == null)
			throw new BadYearException("The year is null!");
		if (school.getName() == null)
			school.setName("");
	}

	private void mapWorkPlaces(JobSeekerDTO seekerDTO, JobSeeker seekerFromDb) {
		if (seekerDTO.getWorkPlaces() == null)
			return;

		Set<WorkPlace> workPlaces = new HashSet<WorkPlace>();

		for (WorkPlace workPlace : seekerDTO.getWorkPlaces()) {
			checkWorkPlaceFields(workPlace);
			Utils.checkTheNumberPositive4DigitLong(workPlace.getFromYear());
			Utils.checkTheNumberPositive4DigitLong(workPlace.getToYear());
			workPlace.setSeeker(seekerFromDb);
			workPlaces.add(workPlace);
		}
		seekerFromDb.setToNullAllWorkPlaces();
		seekerRepository.save(seekerFromDb);
		seekerFromDb.setWorkPlaces(workPlaces);
		workPlaceRepository.deleteAll();
	}

	private void checkWorkPlaceFields(WorkPlace workPlace) {
		if (workPlace.getFromYear() == null || workPlace.getToYear() == null)
			throw new BadYearException("The year is null!");
		if (workPlace.getName() == null)
			workPlace.setName("");
	}

	private void mapNonNullFields(JobSeekerDTO seekerDTO, JobSeeker seekerFromDb) {
		if (seekerDTO.getAboutMe() != null)
			seekerFromDb.setAboutMe(seekerDTO.getAboutMe());
		if (seekerDTO.getFirstName() != null)
			seekerFromDb.setFirstName(seekerDTO.getFirstName());
		if (seekerDTO.getLastName() != null)
			seekerFromDb.setLastName(seekerDTO.getLastName());
	}

	@Override
	public void saveImage(String username, MultipartFile imageFile) {
		User user = userRepository.findByUsername(username);

		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		userService.checkIfActivated(user);

		JobSeeker seeker = seekerRepository.findByUser(user);
		try {
			seeker.setProfileImage(imageFile.getBytes());
			seekerRepository.save(seeker);
		} catch (IOException e) {
			// TODO
		}
	}

	@Override
	public byte[] getCVinPDF(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException(
					"The given user by the username: " + username + " does not exists!");
		userService.checkIfActivated(user);

		JobSeeker seeker = user.getJobSeeker();
		return GeneratePDF.getPDF(seeker, Utils.getDefaultProfileImage());
	}

	@Override
	public byte[] getProfileImage(String username) {

		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		JobSeeker seeker = seekerRepository.findByUser(user);
		byte[] profileImage = seeker.getProfileImage();
		if (profileImage == null) {
			return Utils.getDefaultProfileImage();
		}
		return profileImage;

	}

}
