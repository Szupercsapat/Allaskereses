package com.rft.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.Role;
import com.rft.entities.User;
import com.rft.entities.UserActivation;
import com.rft.exceptions.ActivationExpiredException;
import com.rft.exceptions.EmailAddressAlreadyRegisteredException;
import com.rft.exceptions.UserameAlreadyRegisteredException;
import com.rft.exceptions.WrongActivationCodeException;
import com.rft.repos.JobOffererRepository;
import com.rft.repos.JobSeekerRepository;
import com.rft.repos.RoleRepository;
import com.rft.repos.UserActivationRepository;
import com.rft.repos.UserRepository;
import com.rft.utils.EmailUtil;
import com.rft.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private JobSeekerRepository seekerRepository;
	@Autowired
	private JobOffererRepository offererRepository;
	@Autowired
	private UserActivationRepository userActivationRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	EmailUtil emailSender;

	private static int port=8080;
	private static String serverContext="/rft";
	
	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public void register(String username, String email, String password) {
		checkIfAlreadyInDb(username, email);

		User user = new User();
		Role role = roleRepository.findByName("ROLE_USER");

		user.setEmail(email);
		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setUsername(username);

		user.addRole(role);

		userRepository.save(user);

		JobOfferer offerer = getDefaultJobOfferer(user);
		JobSeeker seeker = getDefaultJobSeeker(user);
		UserActivation userActivation = getDefaultUserActivation(user);

		offererRepository.save(offerer);
		seekerRepository.save(seeker);
		userActivationRepository.save(userActivation);
		
		emailSender.sendSimpleMessage(email, "Registration", getRegistrationText(username,userActivation.getActivationString()));
	}

	public void activateUser(String activationCode)
	{
		UserActivation activation = userActivationRepository.findByActivationString(activationCode);
		
		if(activation == null)
			throw new WrongActivationCodeException("The given activation code does not exists");
		
		Date thisMoment = new Date();
		
		if(activation.getExpiration_date().before(thisMoment))
		{
			userActivationRepository.delete(activation);
			userRepository.delete(activation.getUser().getId());
			throw new ActivationExpiredException("The activation date has expired!");
		}
		
		//findByID nem ment emiatt ez alatt stream-el oldottam meg
		//User user = userRepository.findByID(activation.getUser().getId()); 
		List<User> lista = userRepository.findAll();
		User user = lista.stream().filter(e -> e.getId().equals(activation.getUser().getId())).findFirst().get();
		user.setActivated(true);
		
		userActivationRepository.delete(activation);
		userRepository.save(user);
	}
	
	private void checkIfAlreadyInDb(String username, String email) {
		// TODO: error message localization

		if (userRepository.findByUsername(username) != null)
			throw new UserameAlreadyRegisteredException("Already registered with the username: " + username);

		if (userRepository.findByEmail(email) != null)
			throw new EmailAddressAlreadyRegisteredException("Already registered with an email: " + email);
	}

	private String getRegistrationText(String username,String activationCode) {
		StringBuilder sb = new StringBuilder();
		String link="localhost:"+port+serverContext+"/registered/activation/"+activationCode;

		sb.append("<h1>" + "Üdvözöljük " + username + "!" + "</h1></br>");
		sb.append("<p>" + "Köszöntjük oldalunkon." + "</p>");
		sb.append("</br>"+"A linkre kattintva tudja regisztrációját megerősíteni: " + "</br>" + link);
		//sb.append("<a href='"+link+"'>"+ "Aktiválás"+"</a>");

		return sb.toString();
	}

	private JobOfferer getDefaultJobOfferer(User user) {
		JobOfferer offerer = new JobOfferer();

		offerer.setAboutMe("Empty");
		offerer.setCategories(null);
		offerer.setFirstName("John");
		offerer.setLastName("Doe");
		offerer.setProfileImage(null);
		offerer.setUser(user);

		return offerer;
	}

	private JobSeeker getDefaultJobSeeker(User user) {
		JobSeeker seeker = new JobSeeker();

		seeker.setAboutMe("Empty");
		seeker.setCategories(null);
		seeker.setFirstName("John");
		seeker.setLastName("Doe");
		seeker.setProfileImage(null);
		seeker.setUser(user);

		return seeker;
	}

	private UserActivation getDefaultUserActivation(User user) {
		UserActivation activation = new UserActivation();

		Date today = new Date();
		Date plusOneDay = new Date(today.getTime() + (1000 * 60 * 60 * 24));

		activation.setActivationString(Utils.randomString(20));
		activation.setExpiration_date(plusOneDay);
		activation.setUser(user);

		return activation;
	}
}
