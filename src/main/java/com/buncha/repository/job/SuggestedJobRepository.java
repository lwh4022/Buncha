package com.buncha.repository.job;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.job.Job;
import com.buncha.model.job.SuggestedJob;

@Repository
public interface SuggestedJobRepository extends JpaRepository<SuggestedJob, Long>{

	Optional<List<SuggestedJob>> findAllByFreelancer(Freelancer freelancer);
	
	Optional<SuggestedJob> findByJobLikeAndFreelancerLike(Job job, Freelancer freelancer);
	
	Optional<SuggestedJob> findByJob(Job job);
}
