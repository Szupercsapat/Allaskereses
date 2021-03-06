package com.rft.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.User;
import com.rft.entities.DTOs.JobCategoryDTO;
import com.rft.entities.DTOs.JobOffererDTO;
import com.rft.entities.DTOs.JobOffererDTOCollectionless;
import com.rft.entities.DTOs.JobOffererDTOMin;
import com.rft.entities.DTOs.JobSeekerDTOCollectionless;
import com.rft.exceptions.BadProfileTypeException;
import com.rft.exceptions.ImageSizeIsTooBigException;
import com.rft.exceptions.ProfileDoesNotExistsException;
import com.rft.exceptions.UserDoesNotExistsException;
import com.rft.repos.JobCategoryRepository;
import com.rft.repos.JobOffererRepository;
import com.rft.repos.UserRepository;
import com.rft.utils.Utils;

@Service
public class JobOffererServiceImpl implements JobOffererService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JobCategoryRepository categoryRepository;
	@Autowired
	private JobOffererRepository offererRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private JobService jobService;

	@Override
	public void updateProfile(JobOffererDTO offererDTO) {

		User user = userService.checkUserValues(offererDTO.getUsername());
		JobOfferer offererFromDb = offererRepository.findByUser(user);

		mapNonNullFields(offererDTO, offererFromDb);
		updateCategories(offererDTO, offererFromDb);
		offererRepository.save(offererFromDb);
	}

	private void updateCategories(JobOffererDTO offererDTO, JobOfferer offererFromDb) {
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

	private void mapNonNullFields(JobOffererDTO offererDTO, JobOfferer offererFromDb) {
		if (offererDTO.getAboutMe() != null)
			offererFromDb.setAboutMe(offererDTO.getAboutMe());
		if (offererDTO.getFirstName() != null)
			offererFromDb.setFirstName(offererDTO.getFirstName());
		if (offererDTO.getLastName() != null)
			offererFromDb.setLastName(offererDTO.getLastName());
	}

	@Override
	public void saveImage(String username, MultipartFile imageFile) {

		User user = userRepository.findByUsername(username);

		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		userService.checkIfActivated(user);

		// max file méret < 64kb
		if (imageFile.getSize() > Utils.MAX_IMAGEFILE_SIZE)
			throw new ImageSizeIsTooBigException(
					"The imagesize is more than: " + Utils.MAX_IMAGEFILE_SIZE / 1000 + " KB");

		JobOfferer offerer = offererRepository.findByUser(user);
		try {
			offerer.setProfileImage(imageFile.getBytes());
			offererRepository.save(offerer);
		} catch (IOException e) {
			// TODO
		}
	}

	@Override
	public byte[] getProfileImage(String username) {

		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		JobOfferer offerer = offererRepository.findByUser(user);
		byte[] profileImage = offerer.getProfileImage();
		if (profileImage == null) {
			return Utils.getDefaultProfileImage();
		}
		return profileImage;
	}

	public void removeDescendants(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		JobOfferer offerer = offererRepository.findByUser(user);

		Iterator<JobCategory> categoryIterator = offerer.getCategories().iterator();
		while (categoryIterator.hasNext()) {
			JobCategory category = categoryIterator.next();
			categoryIterator.remove();
		}

		jobService.removeAllJobs(offerer);
	}

	@Override
	public JobCategoryDTO getCategoryIds(Integer offererId) {
		JobOfferer  offerer = offererRepository.findById(offererId);
		if( offerer == null)
			throw new ProfileDoesNotExistsException("The profile does not exists!");
		
		JobCategoryDTO dto = new JobCategoryDTO();
		dto.setIds( offerer.getCategories().stream().mapToInt(cat->cat.getId()).boxed().collect(Collectors.toList()));
		return dto;
	}

	@Override
	public JobOffererDTOMin getOffererDTO(Integer offererId) {
		JobOfferer offerer = offererRepository.findById(offererId);
		if(offerer == null)
			throw new ProfileDoesNotExistsException("The profile does not exists!");
		
		JobOffererDTOMin dto = new JobOffererDTOMin();

		dto.setAboutMe(offerer.getAboutMe());
		dto.setFirstName(offerer.getFirstName());
		dto.setLastName(offerer.getLastName());
		dto.setUsername(offerer.getUser().getUsername());
		dto.setId(offerer.getId());
		dto.setCategories(offerer.getCategories().stream().mapToInt(o->o.getId()).boxed().collect(Collectors.toList()));

		return dto;
	}

	@Override
	public List<JobOffererDTOMin> getOffererDTOs(Integer page, Integer size) {
		List<JobOffererDTOMin> dtos = new ArrayList<JobOffererDTOMin>();

		offererRepository.findAll().stream().forEach(offerer -> {
			JobOffererDTOMin offererDTO = new JobOffererDTOMin();

			offererDTO.setAboutMe(offerer.getAboutMe());
			offererDTO.setFirstName(offerer.getFirstName());
			offererDTO.setLastName(offerer.getLastName());
			offererDTO.setUsername(offerer.getUser().getUsername());
			offererDTO.setId(offerer.getId());
			offererDTO.setCategories(offerer.getCategories().stream().mapToInt(o->o.getId()).boxed().collect(Collectors.toList()));

			dtos.add(offererDTO);
		});

		return dtos;
	}
}
