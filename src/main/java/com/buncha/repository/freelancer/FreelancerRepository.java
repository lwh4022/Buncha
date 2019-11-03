package com.buncha.repository.freelancer;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.member.Member;


@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long>{
	
	Optional<Freelancer> findByMember(Member member);
	
	Page<Freelancer> findAll(Pageable paging);
}
