package com.rft.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rft.entities.School;
import com.rft.entities.WorkPlace;

public interface WorkPlaceRepository extends JpaRepository<WorkPlace, Long> {
	
}
