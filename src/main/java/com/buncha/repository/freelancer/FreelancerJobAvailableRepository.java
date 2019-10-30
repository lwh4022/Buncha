package com.buncha.repository.freelancer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.buncha.model.enumClass.CareerType;
import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.freelancer.FreelancerJobAvailable;

@Repository
public interface FreelancerJobAvailableRepository extends JpaRepository<FreelancerJobAvailable, Long>{

	Optional<List<FreelancerJobAvailable>> findAllByFreelancer(Freelancer free);
	
	Page<FreelancerJobAvailable> findAllByJobAvailableLike(CareerType job, Pageable paging);
	
	Page<FreelancerJobAvailable> findAllByFreelancerInAndJobAvailableLike(List<Freelancer> freelancer, CareerType job, Pageable paging);
	
}
