package com.rft.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;
import java.lang.Integer;
import java.util.List;
import java.util.Set;

@Repository("jobRepository")
public interface JobRepository extends JpaRepository<Job, Integer>, JobRepositoryCustom { //commit előtt Crudrepo volt! igy ezek a query-k mennek
	List<Job> findByCategories(Set categories);
	Job findById(Integer id);

	//localhost:8080/rft/jobs/search/findByIds?jIds=161,162
	@Query("select j from Job j where j.id in :jIds")
	List<Job> findByIds(List<Integer> jIds);

	//@Query("from Job j where j.categories.id in cIds")
	//List<Job> findByCategoryIds(List<Integer> cIds);
	
	//localhost:8080/rft/jobs/search/findByIds?jIds=161,162
	@Query(value="select j.* from job j where id in :jIds",nativeQuery=true)
	List<Job> findByIdsNQ(List<Integer> jIds);
	
	//localhost:8080/rft/jobs/search/findByIds?jIds=161,162
		/*@Query(
				value="select j.* "
				+ "from job j inner join job_job_category jc on jc.job_id = j.id inner "
				+ "join job_category c on c.id = jc.category_id; "
				+ "where c.id in :cIds"
				,nativeQuery=true)
		List<Job> findByCategoryIdsNQInner(List<Integer> cIds);*/
	
	@Query("select j from Job j join j.categories c where c.id in :cIds and j.id > :id")
	List<Job> findByCatIds(List<Integer> cIds, Integer id);
	
	//List<Job> findByJobCategories(List<Integer> cIds);
	
}
































