package com.rft;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rft.entities.User;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.rft.entities.AccessTokenEntity;
import com.rft.entities.Job;
import com.rft.entities.JobCategory;
import com.rft.entities.JobOfferer;
import com.rft.entities.JobSeeker;
import com.rft.entities.RefreshTokenEntity;
import com.rft.entities.School;
import com.rft.entities.User;
import com.rft.entities.WorkPlace;
import com.rft.repos.AccessTokenRepository;
import com.rft.repos.JobCategoryRepository;
import com.rft.repos.JobOffererRepository;
import com.rft.repos.JobSeekerRepository;
import com.rft.repos.RefreshTokenRepository;
import com.rft.repos.RoleRepository;
import com.rft.repos.SchoolRepository;
import com.rft.repos.UserRepository;
import com.rft.repos.WorkPlaceRepository;
import com.rft.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SzupercsapatRftApplicationTests {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	JobSeekerRepository seekerRepository;
	@Autowired
	JobOffererRepository offererRepository;
	@Autowired
	JobCategoryRepository categoryRepository;
	@Autowired
	SchoolRepository schoolRepository;
	@Autowired
	WorkPlaceRepository workPlaceRepository;
	@Autowired
	AccessTokenRepository accTokenRepository;
	@Autowired
	RefreshTokenRepository refTokenRepository;
	@Autowired
	UserService userService;
	@PersistenceContext
    private EntityManager em;
	
	
	@Test
	public void addSchoolsToSeeker()
	{
		User user = userRepository.findByUsername("user");
		JobSeeker seeker = seekerRepository.findByUser(user);
		seeker.setToNullAllSchools();
		seekerRepository.save(seeker);
		schoolRepository.deleteAll();
		
		School school1 = new School();
		school1.setFromYear(2014);
		school1.setToYear(2030);
		school1.setName("DEIK");
		school1.setSeeker(seeker);
		
		School school2 = new School();
		school2.setFromYear(2011);
		school2.setToYear(2014);
		school2.setName("Brassai");
		school2.setSeeker(seeker);
		
		Set<School> schools = new HashSet<School>();
		schools.add(school1);
		schools.add(school2);
		
		seeker.setSchools(schools);
		seekerRepository.save(seeker);
	}
	
	@Test
	public void addWorkPlacesToSeeker()
	{
		User user = userRepository.findByUsername("user");
		JobSeeker seeker = seekerRepository.findByUser(user);
		seeker.setToNullAllWorkPlaces();
		seekerRepository.save(seeker);
		workPlaceRepository.deleteAll();
		
		WorkPlace workPlace1 = new WorkPlace();
		workPlace1.setFromYear(2001);
		workPlace1.setToYear(2007);
		workPlace1.setName("Tesco");
		workPlace1.setSeeker(seeker);
		
		WorkPlace workPlace2 = new WorkPlace();
		workPlace2.setFromYear(2008);
		workPlace2.setToYear(20014);
		workPlace2.setName("Microsoft");
		workPlace2.setSeeker(seeker);
		
		Set<WorkPlace> workPlaces = new HashSet<WorkPlace>();
		workPlaces.add(workPlace1);
		workPlaces.add(workPlace2);
		
		seeker.setWorkPlaces(workPlaces);
		seekerRepository.save(seeker);
	}
	
	@Test
	public void createJobs()
	{
		User user = userRepository.findByUsername("user");
		JobOfferer offerer = offererRepository.findByUser(user);
		
		Job job1 = new Job();
		job1.setDescription("Description");
		job1.setName("job1");
		job1.setOfferer(offerer);
		
		Job job2 = new Job();
		job2.setDescription("Description");
		job2.setName("job2");
		job2.setOfferer(offerer);
		
		offerer.addJob(job1);
		offerer.addJob(job2);
		
		offererRepository.save(offerer);
	}
	
	@Test
	public void checkTokens()
	{
		List<AccessTokenEntity> findAll = accTokenRepository.findAll();
		for(AccessTokenEntity token : findAll)
		{
			RefreshTokenEntity refToken = refTokenRepository.findByTokenId(token.getRefreshToken());
			
			if(refToken !=null)
				System.out.println("\n\n\n"+"------- ref token id: " + refToken.getTokenId()+"\n\n\n");
			
			System.out.println("\n\n\n"+token.getUsername()+"\n\n\n");
		}
	}
	
	@Test
	public void jobQueryTest()
	{
		List<Integer> categoryIds= Arrays.asList(3,5);
		
		
		List<Job> jobs = em
				.createQuery("select j from Job j join j.categories c where c.id in :cIds")
				.setParameter("cIds", categoryIds)
				.getResultList();
		for(Job job : jobs)
		{
			System.out.println(job.getId()+"\n");
		}
	}
	
}



