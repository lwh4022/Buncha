package com.buncha.repository.freelancer;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.freelancer.FreelancerFile;

@Repository
public interface FreelancerFileRepository extends JpaRepository<FreelancerFile, Long>{

	Optional<FreelancerFile> findByFreelancer(Freelancer free);
	void deleteByFreelancer(Freelancer freelancer);
}
