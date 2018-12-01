package com.rft.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.rft.entities.School;
import java.lang.Integer;
import java.util.List;
import com.rft.entities.JobSeeker;

public interface SchoolRepository extends JpaRepository<School, Integer> {
	Page<List<School>> findByToYear(@Param("toYear") Integer toyear,Pageable pageable);
	Page<List<School>> findBySeeker(@Param("seeker") JobSeeker seeker,Pageable pageable);
}
