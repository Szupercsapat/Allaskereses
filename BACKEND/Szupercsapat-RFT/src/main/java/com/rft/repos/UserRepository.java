package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.User;
import java.lang.Integer;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String name);
	User findByEmail(String email);
	User findById(Integer id);
}
