package com.rft.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.json.*;

import com.rft.entities.User;
import com.rft.exceptions.ActivationExpiredException;
import com.rft.exceptions.MissingUserInformationException;
import com.rft.exceptions.WrongActivationCodeException;
import com.rft.repos.UserRepository;
import com.rft.services.UserService;

//kell ide CrossOrigin, így minden fv-t tudunk frontendről hívni CORS-al
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepo;

	// @Autowired
	// private ProjectionFactory projectionFactory;

	@RequestMapping("/hello/{name}")
	public String hello(@PathVariable("name") String name) {
		return "Hello " + name;
	}
	/*
	 * @RequestMapping( value = "/helloka/findall", method = RequestMethod.GET
	 * //,produces = { MimeTypeUtils.APPLICATION_JSON_VALUE }, //headers =
	 * "Accept=application/json" ) public ResponseEntity<List<User>> findAll() {
	 * 
	 * List<User> findAll = userService.findAll(); for(User u : findAll) {
	 * System.out.println(u); }
	 * 
	 * try { return new ResponseEntity<List<User>>(findAll, HttpStatus.OK); } catch
	 * (Exception e) { return new
	 * ResponseEntity<List<User>>(HttpStatus.BAD_REQUEST); } }
	 */

	/*
	 * @PostMapping("/find/{name}") public Iterable<Proba> checkIn(@PathVariable
	 * String name) {
	 * 
	 * return probaRepo.findAll();
	 * 
	 * }
	 */
	/*
	 * @RequestMapping(value="/helloka/findall", method = RequestMethod.GET)
	 * Page<Proba> employeesPageable(Pageable pageable) { //ne proba2 enity legyen
	 * hanem, dto //if(1==1) // throw new
	 * RuntimeException("helloe there dear friend");
	 * 
	 * return probaRepo.findAll(pageable); }
	 */

	// localhost:8080/helloka/findall?page=1&size=3&sort=username,desc
	// @PreAuthorize(value = "hasRole('ROLE_ADMIN')") //doesnt work
	// return a dto (projection) can be sorted - paged
	/*
	 * @RequestMapping(value = "/helloka/findall") public
	 * Page<PartialProbaProjection> listEmployees(Pageable pageable) {
	 * 
	 * return probaRepo.findAll(pageable). map(proba ->
	 * projectionFactory.createProjection(PartialProbaProjection.class, proba)); }
	 */

	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public Map<String, Object> process(@RequestBody Map<String, Object> payload) throws Exception {

		System.out.println(payload);
		int i = 0;
		for (Map.Entry<String, Object> entry : payload.entrySet()) {
			i++;
			String key = entry.getKey();
			Object value = entry.getValue();
			entry.setValue(i + "# hey");
		}
		return payload;
	}

	// get and save image, return an image via bytearray shit or some shit
	@GetMapping(value = "/getimage")
	public @ResponseBody byte[] getImage() throws IOException {

		File initialFile = new File("src/main/resources/Scarlett_Johansson_Césars_2014.jpg");
		InputStream targetStream = new FileInputStream(initialFile);

		byte[] targetArray = new byte[targetStream.available()];
		targetStream.read(targetArray);

		return targetArray;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		File convertFile = new File("C:\\kepek\\" + file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
	}
	
	
	/*
	//angular
	//https://stackoverflow.com/questions/39863317/how-to-force-angular2-to-post-using-x-www-form-urlencoded
	@PostMapping( "/register" )
	public ResponseEntity<Object> someControllerMethod( @RequestParam Map<String, String> body ) throws Exception {

	   System.out.println(body.get("username") +"\n\n\n");
	   return new ResponseEntity<>("Registered", HttpStatus.OK);
	}*/
	
	/***
	 * https://stackoverflow.com/questions/24551915/how-to-get-form-data-as-a-map-in-spring-mvc-controller
	 * !Make sure the Content-type is application/x-www-form-urlencoded!
	 * @param user
	 * @return
	 * @throws Exception
	 */
	//osztályba belraktam elvileg nem kell, de külön metódusokra is lehet rakni
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> register(@RequestBody User user) throws Exception {

		System.out.println(user.getUsername());
		System.out.println(user.getEmail());
		System.out.println(user.getPassword());
		
		String username = user.getUsername() == null ? "" : user.getUsername();
		String email = user.getEmail() == null ? "" : user.getEmail();
		String password = user.getPassword() == null ? "" : user.getPassword();

		if (username.isEmpty() || email.isEmpty() || password.isEmpty())
			throw new MissingUserInformationException("Userinformation is missing");
		
		userService.register(username, email, password);

		return new ResponseEntity<>("Registered", HttpStatus.CREATED);
	}
	/*
	 * itthagytam eredeti kódot
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> register(@RequestBody MultiValueMap<String, String> user) throws Exception {

		String username = user.getFirst("username") == null ? "" : user.getFirst("username");
		String email = user.getFirst("email") == null ? "" : user.getFirst("email");
		String password = user.getFirst("password") == null ? "" : user.getFirst("password");

		if (username.isEmpty() || email.isEmpty() || password.isEmpty())
			throw new MissingUserInformationException("Userinformation is missing");
		
		userService.register(username, email, password);

		return new ResponseEntity<>("Registered", HttpStatus.CREATED);
	}*/
	
	@RequestMapping("/registered/activation/{activationCode}")
	public ResponseEntity<Object> userActivation(@PathVariable("activationCode") String activationCode) 
	{
		HttpHeaders headers = new HttpHeaders();
		
		try {
			userService.activateUser(activationCode);
		}catch(ActivationExpiredException ex)
		{
			//TODO: forward to page where it says that you have to register again because the activation has expired
			headers.setLocation(URI.create("https://en.wikipedia.org/wiki/Expiration"));
			return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
			//https://stackoverflow.com/a/47411493
		}
		catch(WrongActivationCodeException ex)
		{
			//TODO: forward to page where it says that you have to register again because the activation has expired
			headers.setLocation(URI.create("https://www.thesaurus.com/noresult?term=wrong%20activation%20code&s=ts"));
			return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
			//https://stackoverflow.com/a/47411493
		}
		//TODO redirect to login page
		headers.setLocation(URI.create("https://en.m.wikipedia.org/wiki/Success"));https://en.wikipedia.org/wiki/Login
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ResponseEntity<Object> logout (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    HttpHeaders headers = new HttpHeaders();
	    
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	  //TODO redirect to login page
	  		headers.setLocation(URI.create("https://en.wikipedia.org/wiki/Login"));
	  		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
}
