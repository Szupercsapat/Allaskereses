package com.rft.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.DTOs.JobCategoryDTO;
import com.rft.entities.DTOs.JobOffererDTO;
import com.rft.entities.DTOs.JobOffererDTOCollectionless;
import com.rft.entities.DTOs.JobOffererDTOMin;
import com.rft.entities.DTOs.JobSeekerDTOCollectionless;
import com.rft.services.JobOffererService;

@RestController
@RequestMapping("/offerer")
public class OffererController {
	@Autowired
	private JobOffererService offererService;

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> update(@RequestBody JobOffererDTO offererDTO) throws Exception {
		offererService.updateProfile(offererDTO);
		return new ResponseEntity<>("Updated", HttpStatus.ACCEPTED);
	}

	// https://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/uploadProfileImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestPart("username") String username,
			@RequestPart("image") MultipartFile imageFile) throws IOException {
		offererService.saveImage(username, imageFile);
		return new ResponseEntity<>("File is uploaded successfully", HttpStatus.ACCEPTED);
	}

	// https://stackoverflow.com/a/16725508
	@ResponseBody
	@RequestMapping(value = "/getProfileImage/username/{username}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@PathVariable("username") String username) {

		return offererService.getProfileImage(username);
	}
	
	@GetMapping("/offerers/page/{page}/size/{size}")
	public List<JobOffererDTOMin> getOffererDTOs(@PathVariable("page") Integer page,@PathVariable("size") Integer size)
	{
		return offererService.getOffererDTOs(page,size);
	}
	
	@GetMapping("/offerer/{offererId}")
	public JobOffererDTOMin getOffererDTO(@PathVariable("offererId") Integer offererId)
	{
		return offererService.getOffererDTO(offererId);
	}
	
	@GetMapping("/categoryIds/{offererId}")
	public JobCategoryDTO getCategoryIds(@PathVariable("offererId") Integer offererId)
	{
		return offererService.getCategoryIds(offererId);
	}
}
