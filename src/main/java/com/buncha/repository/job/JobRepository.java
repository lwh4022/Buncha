package com.buncha.repository.job;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.buncha.model.job.Job;
import com.buncha.model.member.Member;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>{

	Optional<List<Job>> findAllByMember(Member member);
	
	Page<Job> findAll(Pageable paging);
	
	@Query("SELECT j FROM Job j where j.companyName like %?1% OR j.singleLineIntro like %?1% OR j.jobDescription like %?1%")
	Page<Job> getSearchedJobList(String searchText, Pageable pageable);
	
	@Query("SELECT j FROM Job j where j.companyName like %?1% OR j.singleLineIntro like %?1% OR j.jobDescription like %?1% ORDER BY j.recruitDateTime ASC")
	Page<Job> getRecruitDateASC(String searchText, Pageable paging);
	
	@Query("SELECT j FROM Job j where j.companyName like %?1% OR j.singleLineIntro like %?1% OR j.jobDescription like %?1% ORDER BY j.startDateTime ASC")
	Page<Job> getStartDateASC(String searchText, Pageable paging);
	
	Page<Job> findAllByOrderByStartDateTimeAsc(Pageable paging);
	
	Page<Job> findAllByOrderByRecruitDateTimeAsc(Pageable paging);
	
	@Query("SELECT j FROM Job j where j.jobType like %?1%")
	Page<Job> findAllByJobTypeContaining(String jobType, Pageable paging);
	
	@Query("SELECT j FROM Job j where j.jobType like %?1% AND j.companyName like %?2% OR j.singleLineIntro like %?2% OR j.jobDescription like %?2%")
	Page<Job> getJobTypeContaining(String jobType, String searchText, Pageable paging);
}
