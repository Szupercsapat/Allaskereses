package com.rft.repos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.rft.entities.Job;
import com.rft.entities.JobCategory;

public class JobRepositoryImpl implements JobRepositoryCustom {
	
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public List<Job> findByJobCategories(List<Integer> cIds) {
	
		System.out.println("itt\n\n\n");
		
		return em
				.createQuery("select j from Job j join j.categories c where c.id in :cIds")
				.setParameter("cIds", cIds)
				.getResultList();
	}

}
