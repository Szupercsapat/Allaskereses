package com.rft.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.School;
import com.rft.entities.User;
import com.rft.entities.WorkPlace;
import com.rft.entities.DTOs.JobOffererDTO;
import com.rft.entities.DTOs.JobSeekerDTO;
import com.rft.exceptions.BadProfileTypeException;
import com.rft.exceptions.BadYearException;
import com.rft.exceptions.ProfileImageIsMissingForCvException;
import com.rft.exceptions.ProfileTypeIsMissingException;
import com.rft.exceptions.UserDoesNotExistsException;
import com.rft.exceptions.UserIsNotActivatedException;
import com.rft.exceptions.UsernameMissingForProfileUpdateException;
import com.rft.repos.JobCategoryRepository;
import com.rft.repos.JobOffererRepository;
import com.rft.repos.JobSeekerRepository;
import com.rft.repos.SchoolRepository;
import com.rft.repos.UserRepository;
import com.rft.repos.WorkPlaceRepository;
import com.rft.utils.Utils;
import com.rft.utils.pdfGen.GeneratePDF;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private JobCategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JobOffererRepository offererRepository;
	@Autowired
	private JobSeekerRepository seekerRepository;
	@Autowired
	private SchoolRepository schoolRepository;
	@Autowired
	private WorkPlaceRepository workPlaceRepository;
	@Autowired
	private UserService userService;
	
	@Override
	public void updateSeekerProfile(JobSeekerDTO seekerDTO) {

		User user=userService.checkUserValues(seekerDTO.getUsername());
		JobSeeker seekerFromDb = seekerRepository.findByUser(user);
		
		mapSchoolsForSeeker(seekerDTO, seekerFromDb);
		mapWorkPlacesForSeeker(seekerDTO, seekerFromDb);
		mapNonNullFieldsForSeeker(seekerDTO, seekerFromDb);
		updateSeekerCategories(seekerDTO, seekerFromDb);
		seekerRepository.save(seekerFromDb);
	}

	@Override
	public void updateOffererProfile(JobOffererDTO offererDTO) {

		User user=userService.checkUserValues(offererDTO.getUsername());
		JobOfferer offererFromDb = offererRepository.findByUser(user);
		
		mapNonNullFieldsForOfferer(offererDTO, offererFromDb);
		updateOffererCategories(offererDTO, offererFromDb);
		offererRepository.save(offererFromDb);
	}
	
	private void updateOffererCategories(JobOffererDTO offererDTO, JobOfferer offererFromDb) {
		if (offererDTO.getCategories() == null)
			return;

		for (int categoryId : offererDTO.getCategories()) {
			if (categoryId < 0) {
				JobCategory category = categoryRepository.findById(categoryId * (-1));
				offererFromDb.removeCategory(category);
			} else {
				JobCategory category = categoryRepository.findById(categoryId);
				if (category != null) {
					if (!offererFromDb.getCategories().contains(category)) {
						offererFromDb.addCategory(category);
					}
				}
			}
		}
	}

	private void updateSeekerCategories(JobSeekerDTO seekerDTO, JobSeeker seekerFromDb) {
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

	private void mapSchoolsForSeeker(JobSeekerDTO seekerDTO, JobSeeker seekerFromDb) {
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
		schoolRepository.deleteAll();
		seekerFromDb.setSchools(schools);
	}

	private void checkSchoolFields(School school) {
		if (school.getFromYear() == null || school.getToYear() == null)
			throw new BadYearException("The year is null!");
		if (school.getName() == null)
			school.setName("");
	}

	private void mapWorkPlacesForSeeker(JobSeekerDTO seekerDTO, JobSeeker seekerFromDb) {
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
		workPlaceRepository.deleteAll();
		seekerFromDb.setWorkPlaces(workPlaces);
	}

	private void checkWorkPlaceFields(WorkPlace workPlace) {
		if (workPlace.getFromYear() == null || workPlace.getToYear() == null)
			throw new BadYearException("The year is null!");
		if (workPlace.getName() == null)
			workPlace.setName("");
	}

	private void mapNonNullFieldsForSeeker(JobSeekerDTO seekerDTO, JobSeeker seekerFromDb) {
		if (seekerDTO.getAboutMe() != null)
			seekerFromDb.setAboutMe(seekerDTO.getAboutMe());
		if (seekerDTO.getFirstName() != null)
			seekerFromDb.setFirstName(seekerDTO.getFirstName());
		if (seekerDTO.getLastName() != null)
			seekerFromDb.setLastName(seekerDTO.getLastName());
	}

	private void mapNonNullFieldsForOfferer(JobOffererDTO offererDTO, JobOfferer offererFromDb) {
		if (offererDTO.getAboutMe() != null)
			offererFromDb.setAboutMe(offererDTO.getAboutMe());
		if (offererDTO.getFirstName() != null)
			offererFromDb.setFirstName(offererDTO.getFirstName());
		if (offererDTO.getLastName() != null)
			offererFromDb.setLastName(offererDTO.getLastName());
	}

	@Override
	public void saveImage(String profileType, String username, MultipartFile imageFile) {
		// TODO:file size validation
		if (!profileType.equals("seeker") && !profileType.equals("offerer"))
			throw new BadProfileTypeException("The given profile type <" + profileType + "> is wrong");

		if (profileType.equals("seeker"))
			saveImageForSeeker(username, imageFile);
		else
			saveImageForOfferer(username, imageFile);
	}

	private void saveImageForOfferer(String username, MultipartFile imageFile) {

		User user = userRepository.findByUsername(username);
		userService.checkIfActivated(user);

		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		JobOfferer offerer = offererRepository.findByUser(user);
		try {
			offerer.setProfileImage(imageFile.getBytes());
			offererRepository.save(offerer);
		} catch (IOException e) {
			// TODO
		}
	}

	private void saveImageForSeeker(String username, MultipartFile imageFile) {
		User user = userRepository.findByUsername(username);
		userService.checkIfActivated(user);

		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		JobSeeker seeker = seekerRepository.findByUser(user);
		try {
			seeker.setProfileImage(imageFile.getBytes());
			seekerRepository.save(seeker);
		} catch (IOException e) {
			// TODO
		}
	}

	@Override
	public byte[] getProfileImage(String username, String profileType) {

		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		if (!profileType.equals("seeker") && !profileType.equals("offerer"))
			throw new BadProfileTypeException("The given profile type <" + profileType + "> is wrong");

		if (profileType.equals("seeker")) {
			JobSeeker seeker = seekerRepository.findByUser(user);
			byte[] profileImage = seeker.getProfileImage();
			if (profileImage == null) {
				return getDefaultProfileImage();
			}
			return profileImage;
		}

		JobOfferer offerer = offererRepository.findByUser(user);
		byte[] profileImage = offerer.getProfileImage();
		if (profileImage == null) {
			return getDefaultProfileImage();
		}
		return profileImage;
	}

	@Override
	public byte[] getCVinPDF(JobSeekerDTO seekerDTO) {
		User user = userRepository.findByUsername(seekerDTO.getUsername());
		if (user == null)
			throw new UserDoesNotExistsException(
					"The given user by the username: " + seekerDTO.getUsername() + " does not exists!");
		userService.checkIfActivated(user);

		JobSeeker seeker = user.getJobSeeker();
		return GeneratePDF.getPDF(seeker, getDefaultProfileImage());
	}

	private byte[] getDefaultProfileImage() {
		File defaultFile = new File("src/main/resources/images/defaultProfileImage.jpg");
		InputStream targetStream = null;
		try {
			targetStream = new FileInputStream(defaultFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		byte[] targetArray = null;
		try {
			targetArray = new byte[targetStream.available()];
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			targetStream.read(targetArray);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return targetArray;
	}
}
