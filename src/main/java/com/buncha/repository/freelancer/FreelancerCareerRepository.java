package com.buncha.repository.freelancer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.freelancer.FreelancerCareer;

@Repository
public interface FreelancerCareerRepository extends JpaRepository<FreelancerCareer, Long>{

	Optional<List<FreelancerCareer>> findByFreelancer(Freelancer free);
}
