package com.rft.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.DTOs.JobOffererDTO;
import com.rft.entities.DTOs.JobSeekerDTO;
import com.rft.repos.UserRepository;
import com.rft.services.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfilesController {

	@Autowired
	private ProfileService profileService;
	
	@GetMapping("/test")
	public String authTest()
	{
		return "You logged in successfully";
	}

	@RequestMapping(value = "/updateSeeker",method = RequestMethod.POST)
	public ResponseEntity<String> updateSeeker(@RequestBody JobSeekerDTO seekerDTO) throws Exception { //https://stackoverflow.com/a/18525961
		profileService.updateSeekerProfile(seekerDTO);
		return new ResponseEntity<>("Updated", HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/updateOfferer",method = RequestMethod.POST)
	public ResponseEntity<String> updateOfferer(@RequestBody JobOffererDTO offererDTO) throws Exception { 
		profileService.updateOffererProfile(offererDTO);
		return new ResponseEntity<>("Updated", HttpStatus.ACCEPTED);
	}
	
	//https://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/uploadProfileImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(
			  @RequestPart("profileType") String profileType,
			  @RequestPart("username") String username,
			  @RequestPart("image") MultipartFile imageFile) throws IOException 
	{	
		profileService.saveImage(profileType,username,imageFile);	
		return new ResponseEntity<>("File is uploaded successfully", HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/getProfileImage/username/{username}/profileType/{profileType}")
	public @ResponseBody byte[] getImage(@PathVariable("username") String username,@PathVariable("profileType") String profileType) {

		return profileService.getProfileImage(username,profileType);
	}
	
	//https://stackoverflow.com/a/16656382
	@RequestMapping(value="/getCV", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getCV(@RequestBody JobSeekerDTO seekerDTO) {

	    byte[] contents = profileService.getCVinPDF(seekerDTO);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    String filename = seekerDTO.getUsername()+".pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
	    return response;
	}
}
