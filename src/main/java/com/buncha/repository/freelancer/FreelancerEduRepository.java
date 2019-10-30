package com.buncha.repository.freelancer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.buncha.model.enumClass.PoOrUnderType;
import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.freelancer.FreelancerEdu;

@Repository
public interface FreelancerEduRepository extends JpaRepository<FreelancerEdu, Long>{

	Optional<List<FreelancerEdu>> findByFreelancer(Freelancer free);
	
	Page<FreelancerEdu> findAllByPostUnderLike(PoOrUnderType poOrUnder, Pageable paging);
	
	Optional<List<FreelancerEdu>> findAllByPostUnderLike(PoOrUnderType postUnder);
}
