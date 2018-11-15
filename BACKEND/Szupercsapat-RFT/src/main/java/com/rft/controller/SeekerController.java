package com.rft.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import com.rft.entities.DTOs.JobSeekerDTO;
import com.rft.services.JobSeekerService;

@RestController
@RequestMapping("/seeker")
public class SeekerController {

	@Autowired
	private JobSeekerService seekerService;
	
	@GetMapping("/test")
	public String authTest()
	{
		return "You logged in successfully";
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public ResponseEntity<String> update(@RequestBody JobSeekerDTO seekerDTO) throws Exception { //https://stackoverflow.com/a/18525961
		seekerService.updateProfile(seekerDTO);
		return new ResponseEntity<>("Updated", HttpStatus.ACCEPTED);
	}
	
	//https://stackoverflow.com/a/16656382
	@RequestMapping(value="/getCV", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getCV(@RequestBody JobSeekerDTO seekerDTO) {

	    byte[] contents = seekerService.getCVinPDF(seekerDTO);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    String filename = seekerDTO.getUsername()+".pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
	    return response;
	}
	
	//https://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
	@RequestMapping(value = "/uploadProfileImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(
			  @RequestPart("username") String username,
			  @RequestPart("image") MultipartFile imageFile) throws IOException 
	{	
		seekerService.saveImage(username,imageFile);	
		return new ResponseEntity<>("File is uploaded successfully", HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/getProfileImage/username/{username}")
	public @ResponseBody byte[] getImage(@PathVariable("username") String username) {

		return seekerService.getProfileImage(username);
	}
}
























