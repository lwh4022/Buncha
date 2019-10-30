package com.buncha.repository.job;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buncha.model.job.Job;
import com.buncha.model.job.JobLocation;

public interface JobLocationRepository extends JpaRepository<JobLocation, Long>{

	Optional<List<JobLocation>> findAllByJob(Job job);
}
