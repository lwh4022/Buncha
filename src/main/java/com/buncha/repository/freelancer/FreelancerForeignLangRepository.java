package com.buncha.repository.freelancer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.freelancer.FreelancerForeignLang;

@Repository
public interface FreelancerForeignLangRepository extends JpaRepository<FreelancerForeignLang, Long>{

	Optional<List<FreelancerForeignLang>> findByFreelancer(Freelancer free);
}
