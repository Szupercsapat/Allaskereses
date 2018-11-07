package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.User;
import com.rft.entities.UserActivation;
import java.lang.String;
import java.util.List;

public interface UserActivationRepository extends JpaRepository<UserActivation, Integer> {
	UserActivation findByActivationString(String activationstring);
}
