package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.School;

public interface SchoolRepository extends JpaRepository<School, Integer> {
	
}
