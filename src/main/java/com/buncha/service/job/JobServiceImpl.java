package com.buncha.service.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mariadb.jdbc.MariaDbClob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.freelancer.FreelancerEdu;
import com.buncha.model.job.AppliedJob;
import com.buncha.model.job.Job;
import com.buncha.model.job.JobLocation;
import com.buncha.model.job.SuggestedJob;
import com.buncha.model.job.enumClass.LocationType;
import com.buncha.model.member.Member;
import com.buncha.repository.freelancer.FreelancerEduRepository;
import com.buncha.repository.freelancer.FreelancerRepository;
import com.buncha.repository.job.AppliedJobRepository;
import com.buncha.repository.job.JobLocationRepository;
import com.buncha.repository.job.JobRepository;
import com.buncha.repository.job.SuggestedJobRepository;
import com.buncha.repository.member.MemberRepository;
import com.buncha.request.JobEnrollRequest;
import com.buncha.response.freelancer.EduShortInfo;
import com.buncha.response.job.ApplicantsInfo;
import com.buncha.response.job.ApplicantsResponse;
import com.buncha.response.job.GetJobResponse;
import com.buncha.response.job.JobAllInfoResponse;
import com.buncha.response.job.JobInfo;
import com.buncha.response.job.JobShortInfo;
import com.buncha.response.job.JobShortInfoResponse;
import com.buncha.response.job.ScheduleResponse;

@Service
public class JobServiceImpl implements JobService{

	private final String NoMemberMsg = "존재하지 않은 아이디입니다";
	private final String NoFreelancerMsg = "프리랜서로 등록하지 않았습니다";
	private final String NoJobMsg = "업무 정보가 없습니다.";
	
	private final int NO_STRING = 0;
	
	@Autowired MemberRepository memberRepo;
	@Autowired AppliedJobRepository appliedRepo;
	@Autowired JobLocationRepository locationRepo;
	@Autowired SuggestedJobRepository suggestedRepo;
	@Autowired JobRepository jobRepo;
	@Autowired FreelancerRepository freeRepo;
	@Autowired FreelancerEduRepository eduRepo;
	
	@Override
	public String enroll(JobEnrollRequest enroll) throws Exception{
		this.enrollJob(enroll);
		return "성공";
	}
	
	@Override
	public String edit(JobEnrollRequest enroll) throws Exception {
		Job job = jobRepo.findById(enroll.getJobSeq()).orElseThrow(()-> new Exception("존재하지 않는 업무입니다."));
		job.setAmount(enroll.getAmount());
		job.setCompanyName(enroll.getCompanyName());
		job.setEmail(enroll.getEmail());
		job.setEndDate(enroll.getEndDate());
		job.setStartDateTime(enroll.getStartDate());
		job.setJobDescription(this.getClob(enroll.getJobDescription()));
		if(enroll.getJobType() == null) {
			throw new Exception("업무 종류가 없습니다.");
		}
		job.setJobType(enroll.getJobType());
		job.setMember(this.existingMember(enroll.getMemberId()));
		job.setIsNego(enroll.getIsNego());
		job.setNumRecurit(enroll.getNumRecruit());
		job.setComOrIndi(enroll.getComOrIndi());
		job.setPhone(enroll.getPhone());
		job.setPic(enroll.getPic());
		job.setRecruitDate(enroll.getRecruitDate());
		job.setRecruitDateTime(enroll.getRecruitDate());
		job.setSalary(enroll.getSalary());
		job.setSingleLineIntro(this.getClob(enroll.getSingleLineIntro()));
		job.setStartDate(enroll.getStartDate());
		Job jobSaved = jobRepo.save(job);
		for (int i = 0; i < enroll.getJobLocation().size(); i++) {
			if(enroll.getJobLocation().get(i) != null) {
				JobLocation jobsLocation = new JobLocation();
				jobsLocation.setJob(jobSaved);
				jobsLocation.setLocation(LocationType.valueOf(enroll.getJobLocation().get(i)));
				locationRepo.save(jobsLocation);
			}
		}
		return "성공";
	}
	
	@Override
	public JobShortInfoResponse getMyJob(String memberId) throws Exception {
		JobShortInfoResponse jobShortInfoResponse = new JobShortInfoResponse();
		Member member = this.existingMember(memberId);
		
		Optional<List<Job>> job = jobRepo.findAllByMember(member);
		if(job.isPresent()) {
			
			List<JobShortInfo> jobShortInfoList = new ArrayList<>();
			List<Job> jobList = job.get();
			for (int i = 0; i < jobList.size(); i++) {
				JobShortInfo jobInfo = new JobShortInfo();
				jobInfo.setEndDate(jobList.get(i).getEndDate());
				jobInfo.setIsNego(jobList.get(i).getIsNego());
				jobInfo.setContents(this.clobToString(jobList.get(i).getJobDescription()));
				jobInfo.setNum(jobList.get(i).getJobSeq());
				if(jobList.get(i).getJobType() != null) {
					jobInfo.setJobType(jobList.get(i).getJobType());
				}
				jobInfo.setRecruitNum(jobList.get(i).getNumRecurit());
				jobInfo.setSalary(jobList.get(i).getSalary());
				jobInfo.setTitle(this.clobToString(jobList.get(i).getSingleLineIntro()));
				jobInfo.setStartDate(jobList.get(i).getStartDate());
				jobInfo.setEnrolledAt(jobList.get(i).getChanged());
				jobInfo.setRecruiting(jobList.get(i).getRecruitDate());
				jobShortInfoList.add(jobInfo);
			}
			jobShortInfoResponse.setInfo(jobShortInfoList);
			return jobShortInfoResponse;
		} else {
			return null;
		}
	}
	
	@Override
	public JobShortInfoResponse getAvailableJob(String memberId, long freelancerSeq) throws Exception {
		JobShortInfoResponse jobShortInfoResponse = new JobShortInfoResponse();
		Member member = this.existingMember(memberId);
		Freelancer freelancer = freeRepo.findById(freelancerSeq).orElseThrow(()-> new Exception("프리랜서 정보가 없습니다."));
		Optional<List<Job>> job = jobRepo.findAllByMember(member);
		if(job.isPresent()) {
			List<JobShortInfo> jobShortInfoList = new ArrayList<>();
			List<Job> jobList = job.get();
			LocalDateTime ldt = LocalDateTime.now();
			ZonedDateTime zdt = ldt.atZone(ZoneId.of("Z"));
			for (int i = 0; i < jobList.size(); i++) {
				Optional<SuggestedJob> isExist = suggestedRepo.findByJobLikeAndFreelancerLike(jobList.get(i), freelancer);
				if(!isExist.isPresent()) {
					if(jobList.get(i).getrecruitDateTime() > zdt.toInstant().toEpochMilli() / 1000 / 60 / 60 / 24) {
						JobShortInfo jobInfo = new JobShortInfo();
						jobInfo.setEndDate(jobList.get(i).getEndDate());
						jobInfo.setIsNego(jobList.get(i).getIsNego());
						jobInfo.setContents(this.clobToString(jobList.get(i).getJobDescription()));
						jobInfo.setNum(jobList.get(i).getJobSeq());
						if(jobList.get(i).getJobType() != null) {
							jobInfo.setJobType(jobList.get(i).getJobType());
						}
						jobInfo.setRecruitNum(jobList.get(i).getNumRecurit());
						jobInfo.setSalary(jobList.get(i).getSalary());
						jobInfo.setTitle(this.clobToString(jobList.get(i).getSingleLineIntro()));
						jobInfo.setStartDate(jobList.get(i).getStartDate());
						jobInfo.setEnrolledAt(jobList.get(i).getChanged());
						jobInfo.setRecruiting(jobList.get(i).getRecruitDate());
						jobShortInfoList.add(jobInfo);
					}
				}
			}
			jobShortInfoResponse.setInfo(jobShortInfoList);
			return jobShortInfoResponse;
		} else {
			return null;
		}
	}
	
	private void enrollJob(JobEnrollRequest enroll) throws Exception {
		Job job = new Job();
		job.setAmount(enroll.getAmount());
		job.setCompanyName(enroll.getCompanyName());
		job.setEmail(enroll.getEmail());
		job.setEndDate(enroll.getEndDate());
		job.setStartDateTime(enroll.getStartDate());
		job.setJobDescription(this.getClob(enroll.getJobDescription()));
		if(enroll.getJobType() == null) {
			throw new Exception("업무 종류가 없습니다.");
		}
		job.setJobType(enroll.getJobType());
		job.setMember(this.existingMember(enroll.getMemberId()));
		job.setIsNego(enroll.getIsNego());
		job.setNumRecurit(enroll.getNumRecruit());
		job.setComOrIndi(enroll.getComOrIndi());
		job.setPhone(enroll.getPhone());
		job.setPic(enroll.getPic());
		job.setRecruitDate(enroll.getRecruitDate());
		job.setRecruitDateTime(enroll.getRecruitDate());
		job.setSalary(enroll.getSalary());
		job.setSingleLineIntro(this.getClob(enroll.getSingleLineIntro()));
		job.setStartDate(enroll.getStartDate());
		Job jobSaved = jobRepo.save(job);
		for (int i = 0; i < enroll.getJobLocation().size(); i++) {
			if(enroll.getJobLocation().get(i) != null) {
				JobLocation jobsLocation = new JobLocation();
				jobsLocation.setJob(jobSaved);
				jobsLocation.setLocation(LocationType.valueOf(enroll.getJobLocation().get(i)));
				locationRepo.save(jobsLocation);
			}
		}		
	}
	
	@Override
	public JobAllInfoResponse getJobInfo(long seq) throws Exception {
		JobAllInfoResponse jobAllInfoResponse = new JobAllInfoResponse();
		Optional<Job> optionalJob = jobRepo.findById(seq);
		if(optionalJob.isPresent()) {
			Job job = optionalJob.get();
			JobInfo jobInfo = new JobInfo();
			jobInfo.setAmount(job.getAmount());
			jobInfo.setChanged(job.getChanged());
			jobInfo.setComOrIndi(job.getComOrIndi());
			jobInfo.setCompanyName(job.getCompanyName());
			jobInfo.setEmail(job.getEmail());
			jobInfo.setEndDate(job.getEndDate());
			jobInfo.setIsNego(job.getIsNego());
			jobInfo.setEndDate(job.getEndDate());
			jobInfo.setJobDescription(this.clobToString(job.getJobDescription()));
			jobInfo.setJobSeq(job.getJobSeq());
			jobInfo.setJobType(job.getJobType());
			jobInfo.setNumRecruit(job.getNumRecurit());
			jobInfo.setPhone(job.getPhone());
			jobInfo.setPic(job.getPic());
			jobInfo.setRecruitDate(job.getRecruitDate());
			jobInfo.setSalary(job.getSalary());
			jobInfo.setSingleLineIntro(this.clobToString(job.getSingleLineIntro()));
			jobInfo.setStartDate(job.getStartDate());
			
			jobAllInfoResponse.setJob(jobInfo);
			Optional<List<JobLocation>> optionalJobLocation = locationRepo.findAllByJob(job);
			if(optionalJobLocation.isPresent()) {
				List<JobLocation> jobLocationList = optionalJobLocation.get();
				List<String> locationList = new ArrayList<>();
				for (int i = 0; i < jobLocationList.size(); i++) {
					locationList.add(jobLocationList.get(i).getLocation().name());
				}
				jobAllInfoResponse.setJobLocation(locationList);
			}
		}
		return jobAllInfoResponse;
	}
	
	
	//지원자 정보 받기
	@Override
	public ApplicantsResponse getApplicants(Long jobSeq) throws Exception {
		ApplicantsResponse applicantsResponse = new ApplicantsResponse();
		List<ApplicantsInfo> applicantsInfoList = new ArrayList<>();
		Optional<Job> optionalJob = jobRepo.findById(jobSeq);
		if(optionalJob.isPresent()) {
			Job job = optionalJob.get();
			Optional<List<AppliedJob>> optionalAppliedJob = appliedRepo.findAllByJob(job);
			if(optionalAppliedJob.isPresent()) {
				List<AppliedJob> appliedJobList = optionalAppliedJob.get();
				for (int i = 0; i < appliedJobList.size(); i++) {
					// long freelancerSeq
					Freelancer freelancer = appliedJobList.get(i).getFreelancer();
					long freelancerSeq = freelancer.getFreelancerSeq();
					ApplicantsInfo applicantsInfo = new ApplicantsInfo();
					applicantsInfo.setFreelancerSeq(freelancerSeq);
					
					// long jobSeq
					applicantsInfo.setJobSeq(jobSeq);
					
					// String koName
					String koName = appliedJobList.get(i).getFreelancer().getMember().getName();
					applicantsInfo.setKoName(koName);
					
					// String selfDescription
					String selfDescription = this.clobToString(appliedJobList.get(i).getFreelancer().getDescription());
					applicantsInfo.setSelfDescription(selfDescription);
					
					// List<EduShortInfo>
					List<EduShortInfo> eduShortInfoList = new ArrayList<>();
					EduShortInfo eduShortInfo = new EduShortInfo();
					Optional<List<FreelancerEdu>> optionalFreelancerEdu = eduRepo.findByFreelancer(freelancer);
					if(optionalFreelancerEdu.isPresent()) {
						List<FreelancerEdu> freelancerEduList = optionalFreelancerEdu.get();
						for (int j = 0; j < freelancerEduList.size(); j++) {
							eduShortInfo.setEnrollYear(freelancerEduList.get(j).getEnrollYear());
							eduShortInfo.setFreelancerSeq(freelancerSeq);
							eduShortInfo.setGradYear(freelancerEduList.get(j).getGradYear());
							eduShortInfo.setIsStudent(freelancerEduList.get(j).getIsStudent());
							if(freelancerEduList.get(j).getPostUnder() != null) {
								eduShortInfo.setPoOrUnder(freelancerEduList.get(j).getPostUnder().name());
							}
							eduShortInfoList.add(eduShortInfo);
						}
					}
					applicantsInfo.setEducation(eduShortInfoList);
					applicantsInfoList.add(applicantsInfo);
				}
				applicantsResponse.setInfo(applicantsInfoList);
				return applicantsResponse;
			} else {
				return null;
			}
		} else {
			throw new Exception("존재하지 않는 업무 입니다.");
		}
	}
	
	// 전체
	@Override
	public GetJobResponse getJobs(int pagingNum) throws Exception {
		Pageable paging = PageRequest.of(pagingNum, 6, Sort.Direction.DESC, "changed");
		List<JobShortInfo> jobShortInfoList = new ArrayList<>();
		Page<Job> jobs = jobRepo.findAll(paging);
		if(jobs.hasContent()) {
			List<Job> contents = jobs.getContent(); 
			for (int i = 0; i < contents.size(); i++) {
				JobShortInfo jobInfo = new JobShortInfo();
				jobInfo.setAmount(contents.get(i).getAmount());
				jobInfo.setContents(this.clobToString(contents.get(i).getJobDescription()));
				jobInfo.setEndDate(contents.get(i).getEndDate());
				jobInfo.setEnrolledAt(contents.get(i).getChanged());
				jobInfo.setIsNego(contents.get(i).getIsNego());
				if(contents.get(i).getJobType() != null) {
					jobInfo.setJobType(contents.get(i).getJobType());
				}
				jobInfo.setNum(contents.get(i).getJobSeq());
				jobInfo.setRecruiting(contents.get(i).getRecruitDate());
				jobInfo.setRecruitNum(contents.get(i).getNumRecurit());
				jobInfo.setSalary(contents.get(i).getSalary());
				jobInfo.setStartDate(contents.get(i).getStartDate());
				jobInfo.setTitle(this.clobToString(contents.get(i).getSingleLineIntro()));
				jobShortInfoList.add(jobInfo);
			}
			JobShortInfoResponse jobShortInfoResponse = new JobShortInfoResponse();
			jobShortInfoResponse.setInfo(jobShortInfoList);
			GetJobResponse getJobResponse = new GetJobResponse();
			getJobResponse.setInfo(jobShortInfoResponse);
			if(jobs.nextPageable().isPaged()) {
				getJobResponse.setIsFinal(false);
				return getJobResponse;
			} else {
				getJobResponse.setIsFinal(true);
				return getJobResponse;
			}
		}
		return null;
	}
	
	//검색어
	@Override
	public GetJobResponse getJobs(String searchText, int pagingNum) throws Exception {
		Pageable paging = PageRequest.of(pagingNum, 6, Sort.Direction.DESC,"changed");
		List<JobShortInfo> jobShortInfoList = new ArrayList<>();
		Page<Job> jobs = jobRepo.getSearchedJobList(searchText, paging);
		if(jobs.hasContent()) {
			List<Job> contents = jobs.getContent(); 
			for (int i = 0; i < contents.size(); i++) {
				JobShortInfo jobInfo = new JobShortInfo();
				jobInfo.setAmount(contents.get(i).getAmount());
				jobInfo.setContents(this.clobToString(contents.get(i).getJobDescription()));
				jobInfo.setEndDate(contents.get(i).getEndDate());
				jobInfo.setEnrolledAt(contents.get(i).getChanged());
				jobInfo.setIsNego(contents.get(i).getIsNego());
				if(contents.get(i).getJobType() != null) {
					jobInfo.setJobType(contents.get(i).getJobType());
				}
				jobInfo.setNum(contents.get(i).getJobSeq());
				jobInfo.setRecruiting(contents.get(i).getRecruitDate());
				jobInfo.setRecruitNum(contents.get(i).getNumRecurit());
				jobInfo.setSalary(contents.get(i).getSalary());
				jobInfo.setStartDate(contents.get(i).getStartDate());
				jobInfo.setTitle(this.clobToString(contents.get(i).getSingleLineIntro()));
				jobShortInfoList.add(jobInfo);
			}
			JobShortInfoResponse jobShortInfoResponse = new JobShortInfoResponse();
			jobShortInfoResponse.setInfo(jobShortInfoList);
			GetJobResponse getJobResponse = new GetJobResponse();
			getJobResponse.setInfo(jobShortInfoResponse);
			if(jobs.nextPageable().isPaged()) {
				getJobResponse.setIsFinal(false);
				return getJobResponse;
			} else {
				getJobResponse.setIsFinal(true);
				return getJobResponse;
			}
		}
		return null;
	}
	
	//정렬 및 검색어 
	@Override
	public GetJobResponse getJobs(int task, String ordering, String searchText, int pagingNum) throws Exception {
		Pageable paging;
		List<JobShortInfo> jobShortInfoList = new ArrayList<>();
		Page<Job> jobs;
		if(ordering.equals("recruiting")) {
			paging = PageRequest.of(pagingNum, 6);
			if(task == NO_STRING) {
				jobs = jobRepo.findAllByOrderByRecruitDateTimeAsc(paging);
			} else {
				jobs = jobRepo.getRecruitDateASC(searchText, paging);
			}
			if(jobs.hasContent()) {
				List<Job> contents = jobs.getContent(); 
				for (int i = 0; i < contents.size(); i++) {
					JobShortInfo jobInfo = new JobShortInfo();
					jobInfo.setAmount(contents.get(i).getAmount());
					jobInfo.setContents(this.clobToString(contents.get(i).getJobDescription()));
					jobInfo.setEndDate(contents.get(i).getEndDate());
					jobInfo.setEnrolledAt(contents.get(i).getChanged());
					jobInfo.setIsNego(contents.get(i).getIsNego());
					if(contents.get(i).getJobType() != null) {
						jobInfo.setJobType(contents.get(i).getJobType());
					}
					jobInfo.setNum(contents.get(i).getJobSeq());
					jobInfo.setRecruiting(contents.get(i).getRecruitDate());
					jobInfo.setRecruitNum(contents.get(i).getNumRecurit());
					jobInfo.setSalary(contents.get(i).getSalary());
					jobInfo.setStartDate(contents.get(i).getStartDate());
					jobInfo.setTitle(this.clobToString(contents.get(i).getSingleLineIntro()));
					jobShortInfoList.add(jobInfo);
				}
				JobShortInfoResponse jobShortInfoResponse = new JobShortInfoResponse();
				jobShortInfoResponse.setInfo(jobShortInfoList);
				GetJobResponse getJobResponse = new GetJobResponse();
				getJobResponse.setInfo(jobShortInfoResponse);
				if(jobs.nextPageable().isPaged()) {
					getJobResponse.setIsFinal(false);
					return getJobResponse;
				} else {
					getJobResponse.setIsFinal(true);
					return getJobResponse;
				}
			} else {
				return null;
			}
		} else if(ordering.equals("startDate")) {
			paging = PageRequest.of(pagingNum, 6);
			if(task == NO_STRING) {
				jobs = jobRepo.findAllByOrderByStartDateTimeAsc(paging);
			} else {
				jobs = jobRepo.getStartDateASC(searchText, paging);
			}
			if(jobs.hasContent()) {
				List<Job> contents = jobs.getContent(); 
				for (int i = 0; i < contents.size(); i++) {
					JobShortInfo jobInfo = new JobShortInfo();
					jobInfo.setAmount(contents.get(i).getAmount());
					jobInfo.setContents(this.clobToString(contents.get(i).getJobDescription()));
					jobInfo.setEndDate(contents.get(i).getEndDate());
					jobInfo.setEnrolledAt(contents.get(i).getChanged());
					jobInfo.setIsNego(contents.get(i).getIsNego());
					if(contents.get(i).getJobType() != null) {
						jobInfo.setJobType(contents.get(i).getJobType());
					}
					jobInfo.setNum(contents.get(i).getJobSeq());
					jobInfo.setRecruiting(contents.get(i).getRecruitDate());
					jobInfo.setRecruitNum(contents.get(i).getNumRecurit());
					jobInfo.setSalary(contents.get(i).getSalary());
					jobInfo.setStartDate(contents.get(i).getStartDate());
					jobInfo.setTitle(this.clobToString(contents.get(i).getSingleLineIntro()));
					jobShortInfoList.add(jobInfo);
				}
				JobShortInfoResponse jobShortInfoResponse = new JobShortInfoResponse();
				jobShortInfoResponse.setInfo(jobShortInfoList);
				GetJobResponse getJobResponse = new GetJobResponse();
				getJobResponse.setInfo(jobShortInfoResponse);
				if(jobs.nextPageable().isPaged()) {
					getJobResponse.setIsFinal(false);
					return getJobResponse;
				} else {
					getJobResponse.setIsFinal(true);
					return getJobResponse;
				}
			} else {
				return null;
			}
			
		} else {
			throw new Exception("잘못된 정렬 기호입니다.");
		}
	}
	
	// 업무 타입 기반 검색
	@Override
	public GetJobResponse getJobs(int task, String jobType, String jobTypeContent, String searchText, int pagingNum) throws Exception {
		List<JobShortInfo> jobShortInfoList = new ArrayList<>();
		if(jobType.equals("jobType")) {
			Pageable paging = PageRequest.of(pagingNum, 6, Sort.Direction.DESC, "changed");
			Page<Job> jobs;
			if(task == NO_STRING) {
				jobs = jobRepo.findAllByJobTypeContaining(jobTypeContent, paging);
			}else {
				jobs = jobRepo.getJobTypeContaining(jobTypeContent, searchText, paging);
			}
			if(jobs.hasContent()) {
				List<Job> contents = jobs.getContent(); 
				for (int i = 0; i < contents.size(); i++) {
					JobShortInfo jobInfo = new JobShortInfo();
					jobInfo.setAmount(contents.get(i).getAmount());
					jobInfo.setContents(this.clobToString(contents.get(i).getJobDescription()));
					jobInfo.setEndDate(contents.get(i).getEndDate());
					jobInfo.setEnrolledAt(contents.get(i).getChanged());
					jobInfo.setIsNego(contents.get(i).getIsNego());
					if(contents.get(i).getJobType() != null) {
						jobInfo.setJobType(contents.get(i).getJobType());
					}
					jobInfo.setNum(contents.get(i).getJobSeq());
					jobInfo.setRecruiting(contents.get(i).getRecruitDate());
					jobInfo.setRecruitNum(contents.get(i).getNumRecurit());
					jobInfo.setSalary(contents.get(i).getSalary());
					jobInfo.setStartDate(contents.get(i).getStartDate());
					jobInfo.setTitle(this.clobToString(contents.get(i).getSingleLineIntro()));
					jobShortInfoList.add(jobInfo);
				}
				JobShortInfoResponse jobShortInfoResponse = new JobShortInfoResponse();
				jobShortInfoResponse.setInfo(jobShortInfoList);
				GetJobResponse getJobResponse = new GetJobResponse();
				getJobResponse.setInfo(jobShortInfoResponse);
				if(jobs.nextPageable().isPaged()) {
					getJobResponse.setIsFinal(false);
					return getJobResponse;
				} else {
					getJobResponse.setIsFinal(true);
					return getJobResponse;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	// 업무 지원하기
	@Override
	public String applyJob(String memberId, long jobSeq) throws Exception {
		Member member = memberRepo.findByMemberId(memberId).orElseThrow(() -> new Exception(NoMemberMsg));
		Freelancer freelancer = freeRepo.findByMember(member).orElseThrow(() -> new Exception(NoFreelancerMsg));
		Job job = jobRepo.findById(jobSeq).orElseThrow(() -> new Exception(NoJobMsg));
		if(appliedRepo.findByJobLikeAndFreelancerLike(job, freelancer).isPresent()) {
			return "기등록";
		} else {
			AppliedJob appliedJob = new AppliedJob();
			appliedJob.setFreelancer(freelancer);
			appliedJob.setJob(job);
			appliedRepo.save(appliedJob);
			return "성공";
		}
	}
	
	
	// 지원 요청
	@Override
	public String suggestJobs(List<Long> jobSeq, long freelancerSeq) throws Exception {
		
		for (int i = 0; i < jobSeq.size(); i++) {
			Optional<Job> job = jobRepo.findById(jobSeq.get(i));
			SuggestedJob suggestedJob = new SuggestedJob();
			if(job.isPresent()) {
				 suggestedJob.setJob(job.get());
			} else {
				throw new Exception("존재하지 않는 업무입니다.");
			}
			Optional<Freelancer> freelancer = freeRepo.findById(freelancerSeq);
			if(freelancer.isPresent()) {
				suggestedJob.setFreelancer(freelancer.get());
			} else {
				throw new Exception("존재하지 않는 프리랜서입니다.");
			}
			suggestedRepo.save(suggestedJob);
		}
		return "성공";
	}
	
	
	// 스케줄 정보 받기
	@Override
	public ScheduleResponse getSchedule(String memberId) throws Exception {
		Member member = this.existingMember(memberId);
		Freelancer freelancer = freeRepo.findByMember(member).orElseThrow(()-> new Exception("프리랜서로 등록하지 않으셨습니다."));
		ScheduleResponse response = new ScheduleResponse();
		Optional<List<AppliedJob>> appliedList = appliedRepo.findAllByFreelancer(freelancer);
		if(appliedList.isPresent()) {
			List<JobShortInfo> jobShortInfoList = new ArrayList<>();
			List<Job> jobList = new ArrayList<>();
			for (int i = 0; i < appliedList.get().size(); i++) {
				jobList.add(appliedList.get().get(i).getJob());
			}
			for (int i = 0; i < jobList.size(); i++) {
				JobShortInfo jobInfo = new JobShortInfo();
				Optional<List<JobLocation>> optionalLocations = locationRepo.findAllByJob(jobList.get(i));
				if(optionalLocations.isPresent()) {
					List<String> result = new ArrayList<>();
					List<JobLocation> locations = optionalLocations.get();
					for (int j = 0; j < locations.size(); j++) {
						result.add(locations.get(j).getLocation().name());
					}
					jobInfo.setJobLocation(result);
				}
				jobInfo.setEndDate(jobList.get(i).getEndDate());
				jobInfo.setIsNego(jobList.get(i).getIsNego());
				jobInfo.setContents(this.clobToString(jobList.get(i).getJobDescription()));
				jobInfo.setNum(jobList.get(i).getJobSeq());
				if(jobList.get(i).getJobType() != null) {
					jobInfo.setJobType(jobList.get(i).getJobType());
				}
				jobInfo.setAmount(jobList.get(i).getAmount());
				jobInfo.setRecruitNum(jobList.get(i).getNumRecurit());
				jobInfo.setSalary(jobList.get(i).getSalary());
				jobInfo.setTitle(this.clobToString(jobList.get(i).getSingleLineIntro()));
				jobInfo.setStartDate(jobList.get(i).getStartDate());
				jobInfo.setEnrolledAt(jobList.get(i).getChanged());
				jobInfo.setRecruiting(jobList.get(i).getRecruitDate());
				jobShortInfoList.add(jobInfo);
			}
			response.setAppliedJobs(jobShortInfoList);
		}
		Optional<List<SuggestedJob>> suggestedList = suggestedRepo.findAllByFreelancer(freelancer);
		if(suggestedList.isPresent()) {
			JobShortInfo jobInfo = new JobShortInfo();
			List<JobShortInfo> jobShortInfoList = new ArrayList<>();
			List<Job> jobList = new ArrayList<>();
			for (int i = 0; i < suggestedList.get().size(); i++) {
				jobList.add(suggestedList.get().get(i).getJob());
			}
			for (int i = 0; i < jobList.size(); i++) {
				Optional<List<JobLocation>> optionalLocations = locationRepo.findAllByJob(jobList.get(i));
				if(optionalLocations.isPresent()) {
					List<String> result = new ArrayList<>();
					List<JobLocation> locations = optionalLocations.get();
					for (int j = 0; j < locations.size(); j++) {
						result.add(locations.get(j).getLocation().name());
					}
					jobInfo.setJobLocation(result);
				}
				jobInfo.setEndDate(jobList.get(i).getEndDate());
				jobInfo.setIsNego(jobList.get(i).getIsNego());
				jobInfo.setContents(this.clobToString(jobList.get(i).getJobDescription()));
				jobInfo.setNum(jobList.get(i).getJobSeq());
				if(jobList.get(i).getJobType() != null) {
					jobInfo.setJobType(jobList.get(i).getJobType());
				}
				jobInfo.setAmount(jobList.get(i).getAmount());
				jobInfo.setRecruitNum(jobList.get(i).getNumRecurit());
				jobInfo.setSalary(jobList.get(i).getSalary());
				jobInfo.setTitle(this.clobToString(jobList.get(i).getSingleLineIntro()));
				jobInfo.setStartDate(jobList.get(i).getStartDate());
				jobInfo.setEnrolledAt(jobList.get(i).getChanged());
				jobInfo.setRecruiting(jobList.get(i).getRecruitDate());
				jobShortInfoList.add(jobInfo);
			}
			response.setSuggestedJobs(jobShortInfoList);
		}
		return response;
	}
	
	private String clobToString(Clob clob) throws SQLException, IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(clob.getCharacterStream());
		String temp = "";
		while((temp = br.readLine()) != null){
			sb.append(temp);
		}
		br.close();
		return sb.toString();
	}
	
	private Clob getClob(String value) {
		java.sql.Clob clob = new MariaDbClob();
		try {
			clob.setString(1, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clob;
	}
	
	private Member existingMember(String memberId) throws Exception{
		Optional<Member> member = memberRepo.findByMemberId(memberId);
		if(member.isPresent()) {
			return member.get();
		} else {
			throw new Exception(NoMemberMsg);
		}
	}

	@Override
	public String abortSuggestedJob(Long jobSeq, String memberId) throws Exception {
		Job job = jobRepo.findById(jobSeq).orElseThrow(()-> new Exception("해당 업무가 존재하지 않습니다."));
		Member member = memberRepo.findByMemberId(memberId).orElseThrow(() -> new Exception("해당 유저정보가 없습니다."));
		Freelancer freelancer = freeRepo.findByMember(member).orElseThrow(()-> new Exception("프리랜서 정보가 없습니다."));
		SuggestedJob suggestedJob = suggestedRepo.findByJobLikeAndFreelancerLike(job, freelancer).orElseThrow(()-> new Exception("해당 정보가 없습니다."));
		suggestedRepo.delete(suggestedJob);
		return "성공";
	}

	@Override
	public String abortAppliedJob(Long jobSeq, String memberId) throws Exception {
		Job job = jobRepo.findById(jobSeq).orElseThrow(()-> new Exception("해당 업무가 존재하지 않습니다."));
		Member member = memberRepo.findByMemberId(memberId).orElseThrow(() -> new Exception("해당 유저정보가 없습니다."));
		Freelancer freelancer = freeRepo.findByMember(member).orElseThrow(()-> new Exception("프리랜서 정보가 없습니다."));
		AppliedJob appliedJob = appliedRepo.findByJobLikeAndFreelancerLike(job, freelancer).orElseThrow(() -> new Exception("해당 정보가 없습니다."));
		appliedRepo.delete(appliedJob);
		return "성공";
	}

	
}
