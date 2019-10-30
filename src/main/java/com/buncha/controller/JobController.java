package com.buncha.controller;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.buncha.model.freelancer.Freelancer;
import com.buncha.repository.freelancer.FreelancerRepository;
import com.buncha.request.JobEnrollRequest;
import com.buncha.request.JobRequest;
import com.buncha.request.SuggestJobsRequest;
import com.buncha.response.Result;
import com.buncha.service.freelancer.FreelancerService;
import com.buncha.service.job.JobService;

@RestController
public class JobController {
	
	@Autowired JobService service;
	@Autowired FreelancerRepository freeRepo;
	@Autowired FreelancerService freelancerService;
	
	// 업무 등록
	@RequestMapping(value = "/private/enrollJob", method=RequestMethod.POST, consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public Result enrollJob(@RequestBody JobEnrollRequest info) throws Exception {
		Result result = Result.successInstance();
		String message = service.enroll(info);
		result.setData(message);
		return result;
	}
	
	// 업무 수정
	@RequestMapping(value = "/private/editJob", method=RequestMethod.PUT, consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public Result editJob(@RequestBody JobEnrollRequest info) throws Exception {
		Result result = Result.successInstance();
		String message = service.edit(info);
		result.setData(message);
		return result;
	}
	
	//해당 업무 정보 받기
	@RequestMapping(value="/private/getJobInfo/{jobSeq}", method=RequestMethod.GET)
	public Result getJobInfo(@PathVariable("jobSeq") Long seq) throws Exception {
		Result result = Result.successInstance();
		result.setData(service.getJobInfo(seq));
		return result;
	}
	
	
	//등록한 업무 받아오기
	@RequestMapping(value = "/private/getEnrolledJob/{memberId}", method=RequestMethod.GET)
	public Result getEnrolledJob(@PathVariable("memberId") String memberId) throws Exception {
		Result result = Result.successInstance();
		result.setData(service.getMyJob(memberId));
		return result;
	}
	
	//지원가능한 업무 받아오기
	@RequestMapping(value = "/private/getAvailableJob/{memberId}/{freelancerSeq}", method=RequestMethod.GET)
	public Result getAvailableJob(@PathVariable("memberId") String memberId, @PathVariable("freelancerSeq") long freelancerSeq) throws Exception {
		Result result = Result.successInstance();
		result.setData(service.getAvailableJob(memberId, freelancerSeq));
		return result;
	}
	
	//지원자 받아오기
	@RequestMapping(value="/private/getApplicants/{jobSeq}", method=RequestMethod.GET)
	public Result getApplicants(@PathVariable("jobSeq") long jobSeq) throws Exception {
		Result result = Result.successInstance();
		result.setData(service.getApplicants(jobSeq));
		return result;
	}
	
	//지원자 preview 정보
	@RequestMapping(value="/private/getApplicantInfo/{freelancerSeq}", method=RequestMethod.GET)
	public Result getApplicantInfo(@PathVariable("freelancerSeq") long freelancerSeq) throws Exception {
		Result result = Result.successInstance();
		Freelancer freelancer = freeRepo.findById(freelancerSeq).orElseThrow(() -> new Exception("프리랜서 정보가 없습니다."));
		result.setData(freelancerService.getFreelancerInfo(freelancer.getMember().getMemberId()));
		return result;
	}
	
	
	//Job_Show
	@RequestMapping(value="/public/getJobs/{paging}", method=RequestMethod.GET)
	public Result getJobs(@PathVariable("paging") int paging) throws Exception {
		Result result = Result.successInstance();
		result.setData(service.getJobs(paging));
		
		return result;
	}
	
	// 검색된 자료
	@RequestMapping(value="/public/getJobs/{searchText}/{paging}", method=RequestMethod.GET)
	public Result getJobs(@PathVariable("searchText") String searchText, @PathVariable("paging") int paging) throws Exception {
		Result result = Result.successInstance();
		String decodedString = URLDecoder.decode(searchText,"UTF-8");
		result.setData(service.getJobs(decodedString, paging));
		return result;
	}
	
	// 정렬 자료
	@RequestMapping(value="/public/getJobs/{task}/{ordering}/{searchText}/{paging}", method=RequestMethod.GET)
	public Result getJobs(@PathVariable("task") int task, @PathVariable("searchText") String searchText, @PathVariable("paging") int paging, @PathVariable("ordering") String ordering) throws Exception {
		Result result = Result.successInstance();
		String decodedSearchText = URLDecoder.decode(searchText,"UTF-8");
		result.setData(service.getJobs(task, ordering, decodedSearchText, paging));
		return result;
	}
	
	//업무별 선택
	@RequestMapping(value="/public/getJobs/{task}/{jobType}/{jobTypeContent}/{searchText}/{paging}", method=RequestMethod.GET)
	public Result getJobs(@PathVariable("searchText") String searchText, @PathVariable("paging") int paging, @PathVariable("jobType") String jobType, @PathVariable("jobTypeContent") String jobTypeContent, @PathVariable("task") int task) throws Exception {
		Result result = Result.successInstance();
		String decodedJobType = URLDecoder.decode(jobType, "UTF-8");
		String decodedSearchText = URLDecoder.decode(searchText,"UTF-8");
		String decodedJobTypecontent = URLDecoder.decode(jobTypeContent, "UTF-8");
		result.setData(service.getJobs(task, decodedJobType, decodedJobTypecontent, decodedSearchText, paging));
		return result;
	}
	
	// 업무 지원
	@RequestMapping(value="/private/applyJob", method=RequestMethod.POST, consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Result applyJob(@RequestBody JobRequest job) throws Exception {
		Result result = Result.successInstance();
		result.setData(service.applyJob(job.getMemberId(), job.getJobSeq()));
		return result;
	}
	
	// 지원 요청
	@RequestMapping(value="/private/suggestJob", method=RequestMethod.POST, consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Result suggestJob(@RequestBody SuggestJobsRequest suggestJobs) throws Exception{
		Result result = Result.successInstance();
		result.setData(service.suggestJobs(suggestJobs.getJobs(), suggestJobs.getFreelancerSeq()));
		return result;
	}
	
	//스케줄 정보
	@RequestMapping(value="/private/scheduleInfo/{memberId}", method=RequestMethod.GET)
	public Result scheduleInfo(@PathVariable("memberId") String memberId) throws Exception{
		Result result = Result.successInstance();
		result.setData(service.getSchedule(memberId));
		return result;
	}
	
	// 제안 받은 업무 철회
	@RequestMapping(value="/private/abortSuggestedJob/{jobSeq}/{memberId}", method=RequestMethod.DELETE)
	public Result abortSuggestedJob(@PathVariable("jobSeq") long jobSeq, @PathVariable("memberId") String memberId) throws Exception{
		Result result = Result.successInstance();
		result.setData(service.abortSuggestedJob(jobSeq, memberId));
		return result;
	}
	
	// 지원한 업무 철회
	@RequestMapping(value="/private/abortAppliedJob/{jobSeq}/{memberId}", method=RequestMethod.DELETE)
	public Result abortAppliedJob(@PathVariable("jobSeq") long jobSeq, @PathVariable("memberId") String memberId) throws Exception{
		Result result = Result.successInstance();
		result.setData(service.abortAppliedJob(jobSeq, memberId));
		return result;
	}
}
