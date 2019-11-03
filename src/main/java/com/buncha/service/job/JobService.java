package com.buncha.service.job;

import java.util.List;

import com.buncha.request.JobEnrollRequest;
import com.buncha.response.job.ApplicantsResponse;
import com.buncha.response.job.GetJobResponse;
import com.buncha.response.job.JobAllInfoResponse;
import com.buncha.response.job.JobShortInfoResponse;
import com.buncha.response.job.ScheduleResponse;

public interface JobService {

	String enroll(JobEnrollRequest enroll) throws Exception;
	
	String edit(JobEnrollRequest enroll) throws Exception;
	
	JobShortInfoResponse getMyJob(String memberId) throws Exception;
	
	ApplicantsResponse getApplicants(Long jobSeq) throws Exception;
	
	GetJobResponse getJobs(int paging) throws Exception;
	
	GetJobResponse getJobs(String searchText, int paging) throws Exception;
	
	GetJobResponse getJobs(int task, String ordering, String searchText, int paging) throws Exception;
	
	GetJobResponse getJobs(int task, String jobType, String contents, String searchText, int paging) throws Exception;
	
	JobAllInfoResponse getJobInfo(long seq) throws Exception;
	
	String applyJob(String memberId, long jobSeq) throws Exception;

	JobShortInfoResponse getAvailableJob(String memberId, long freelancerSeq) throws Exception;
	
	String suggestJobs(List<Long> jobs, long freelancerSeq) throws Exception;
	
	ScheduleResponse getSchedule(String memberId) throws Exception;
	
	String abortSuggestedJob(Long jobSeq, String memberId) throws Exception;
	
	String abortAppliedJob(Long jobSeq, String memberId) throws Exception;
}
