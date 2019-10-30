package com.buncha.repository.job;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.job.AppliedJob;
import com.buncha.model.job.Job;

@Repository
public interface AppliedJobRepository extends JpaRepository<AppliedJob, Long>{

	Optional<List<AppliedJob>> findAllByJob(Job job);
	
	Optional<AppliedJob> findByJobLikeAndFreelancerLike(Job job, Freelancer freelancer);
	
	Optional<List<AppliedJob>> findAllByFreelancer(Freelancer freelancer);
	
	Optional<AppliedJob> findByJob(Job job);
}
