package com.rft.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rft.entities.AccessTokenEntity;
import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.RefreshTokenEntity;
import com.rft.entities.Role;
import com.rft.entities.User;
import com.rft.entities.UserActivation;
import com.rft.entities.DTOs.UserDTO;
import com.rft.exceptions.ActivationExpiredException;
import com.rft.exceptions.EmailAddressAlreadyRegisteredException;
import com.rft.exceptions.MissingUserInformationException;
import com.rft.exceptions.NewPasswordIsMissingException;
import com.rft.exceptions.OldPasswordDoesNotMatchException;
import com.rft.exceptions.OldPasswordIsMissingException;
import com.rft.exceptions.UserDoesNotExistsException;
import com.rft.exceptions.UserIsNotActivatedException;
import com.rft.exceptions.UserameAlreadyRegisteredException;
import com.rft.exceptions.UsernameIsMissingException;
import com.rft.exceptions.UsernameMissingForProfileUpdateException;
import com.rft.exceptions.WrongActivationCodeException;
import com.rft.exceptions.RoleDoesNotExistsException;
import com.rft.repos.AccessTokenRepository;
import com.rft.repos.JobOffererRepository;
import com.rft.repos.JobSeekerRepository;
import com.rft.repos.RefreshTokenRepository;
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
	@Autowired
	AccessTokenRepository accTokenRepository;
	@Autowired
	RefreshTokenRepository refTokenRepository;

	@Value("${server.port}")
	private String port;

	@Value("${server.contextPath}")
	private String serverContext;

	@Override
	public void removeUser(String username) {
		deleteUsersToken(username);
	}

	@Override
	public void removeAllUsers() {

	}

	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public void checkIfActivated(User user) {
		if (!user.isActivated())
			throw new UserIsNotActivatedException("User is not activated.");
	}

	public User checkUserValues(String username) {
		if (username == null)
			throw new UsernameIsMissingException("Username is missing !");
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The username given does not exists!");

		checkIfActivated(user);
		return user;
	}

	@Override
	public void register(UserDTO userDTO) {
		
		if( userDTO.getUsername() == null || userDTO.getEmail()  == null || 
			userDTO.getPassword() == null || userDTO.getRole() == null)
			throw new MissingUserInformationException("Userinformation is missing");
		
		if( userDTO.getUsername().isEmpty() || userDTO.getEmail().isEmpty() || 
				userDTO.getPassword().isEmpty() || userDTO.getRole().isEmpty())
				throw new MissingUserInformationException("Userinformation is missing");
			
		checkIfAlreadyInDb(userDTO.getUsername(), userDTO.getEmail());

		List<String> validRoles = roleRepository.findAll().stream().map( r-> r.getName() ).collect(Collectors.toList());
		
		if( !validRoles.contains(userDTO.getRole()) )
			throw new RoleDoesNotExistsException("The role does not exists !");
		
		User user = new User();
		Role role = roleRepository.findByName(userDTO.getRole());

		user.setEmail(userDTO.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		user.setUsername(userDTO.getUsername());

		user.addRole(role);

		userRepository.save(user);

		JobOfferer offerer = getDefaultOfferer(user);
		JobSeeker seeker = getDefaultSeeker(user);
		UserActivation userActivation = getDefaultUserActivation(user);

		offererRepository.save(offerer);
		seekerRepository.save(seeker);
		userActivationRepository.save(userActivation);

		emailSender.sendSimpleMessage(userDTO.getEmail(), "Registration",
				getRegistrationText(userDTO.getUsername(), userActivation.getActivationString()));
	}

	public void activateUser(String activationCode) {
		UserActivation activation = userActivationRepository.findByActivationString(activationCode);

		if (activation == null)
			throw new WrongActivationCodeException("The given activation code does not exists");

		Date thisMoment = new Date();

		if (activation.getExpiration_date().before(thisMoment)) {
			userActivationRepository.delete(activation);
			userRepository.delete(activation.getUser().getId());
			throw new ActivationExpiredException("The activation date has expired!");
		}

		// findByID nem ment emiatt ez alatt stream-el oldottam meg
		// User user = userRepository.findByID(activation.getUser().getId());
		List<User> lista = userRepository.findAll();
		User user = lista.stream().filter(e -> e.getId().equals(activation.getUser().getId())).findFirst().get();
		user.setActivated(true);

		userActivationRepository.delete(activation);
		userRepository.save(user);
	}

	private void deleteUsersToken(String username) {
		List<AccessTokenEntity> accTokens = accTokenRepository.findAll();

		Iterator<AccessTokenEntity> accTokenIterator = accTokens.iterator();
		while (accTokenIterator.hasNext()) {
			AccessTokenEntity accToken = accTokenIterator.next();
			RefreshTokenEntity refToken = refTokenRepository.findByTokenId(accToken.getRefreshToken());
			refTokenRepository.delete(refToken);
			accTokenIterator.remove();
		}
		
	}

	private void checkIfAlreadyInDb(String username, String email) {
		// TODO: error message localization

		if (userRepository.findByUsername(username) != null)
			throw new UserameAlreadyRegisteredException("Already registered with the username: " + username);

		if (userRepository.findByEmail(email) != null)
			throw new EmailAddressAlreadyRegisteredException("Already registered with an email: " + email);
	}

	private String getRegistrationText(String username, String activationCode) {
		StringBuilder sb = new StringBuilder();
		String link = "localhost:" + port + serverContext + "/user" + "/registered/activation/" + activationCode;

		sb.append("<h1>" + "Üdvözöljük " + username + "!" + "</h1></br>");
		sb.append("<p>" + "Köszöntjük oldalunkon." + "</p>");
		sb.append("</br>" + "A linkre kattintva tudja regisztrációját megerősíteni: " + "</br>" + link);
		// sb.append("<a href='"+link+"'>"+ "Aktiválás"+"</a>");

		return sb.toString();
	}

	private JobOfferer getDefaultOfferer(User user) {
		JobOfferer offerer = new JobOfferer();

		offerer.setAboutMe("Empty");
		offerer.setCategories(null);
		offerer.setFirstName("John");
		offerer.setLastName("Doe");
		offerer.setProfileImage(null);
		offerer.setUser(user);

		return offerer;
	}

	private JobSeeker getDefaultSeeker(User user) {
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

	@Override
	public void changePassword(UserDTO userDTO) {
		if(userDTO ==null)
			throw new UsernameIsMissingException("No data found!");
		
		User user = userRepository.findByUsername(userDTO.getUsername());
		
		if(user==null)
			throw new UsernameIsMissingException("Username is missing!");
		
		checkIfActivated(user);
		
		if(userDTO.getNewPassword() ==null)
			throw new NewPasswordIsMissingException("No new password was found!");
		
		if(userDTO.getPassword() ==null)
			throw new OldPasswordIsMissingException("No old password was found!");
		
		String givenOldPasswordEncripted =  bCryptPasswordEncoder.encode(userDTO.getPassword()); 
		String givenNewPasswordEncripted = bCryptPasswordEncoder.encode(userDTO.getNewPassword());
		
		String oldPasswordEncripted = user.getPassword();
		
		if(bCryptPasswordEncoder.matches(userDTO.getPassword(), oldPasswordEncripted))
		{
			user.setPassword(givenNewPasswordEncripted);
			userRepository.save(user);
			return;
		}
		else
			throw new OldPasswordDoesNotMatchException("The given password does not match with the old one!");
	}
}