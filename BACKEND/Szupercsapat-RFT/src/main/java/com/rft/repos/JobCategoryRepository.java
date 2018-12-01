package com.rft.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.School;

import java.lang.Integer;
import java.util.List;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer> {
	JobCategory findById(Integer id);
	Page<List<JobCategory>> findBySeekersIn(@Param("seekers") List<JobCategory> seekers,Pageable pageable);
	List<JobCategory> findByIdIn(List<Integer> ids);
	
	//@Query("from JobCategory jc where jc.parentId = :pId")
	//List<JobCategory> findByParentId(Integer pId);
	
	//localhost:8080/rft/jobCategories/search/findByParentIds?pIds=5,3
	@Query("from JobCategory jc where jc.parentId in :pIds")
	List<JobCategory> findByParentIds(List<Integer> pIds);
}
