package com.rft.services;

import java.util.List;

import com.rft.entities.User;
import com.rft.entities.DTOs.UserDTO;


public interface UserService {

	List<User> findAll();
	void register(UserDTO userDTO);
	void activateUser(String activationCode);
	void checkIfActivated(User user);
	User checkUserValues(String username);
	void removeUser(String username);
	void removeAllUsers();
	void changePassword(UserDTO userDTO);
}

