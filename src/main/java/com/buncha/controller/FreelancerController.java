package com.buncha.controller;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.buncha.request.FreelancerRequest;
import com.buncha.response.Result;
import com.buncha.service.freelancer.FreelancerService;

@RestController
public class FreelancerController {

	@Autowired FreelancerService service;
	
	//프리랜서 등록
	@RequestMapping(value="/private/enrollFreelancer", method=RequestMethod.POST, consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Result createFreelancer(@RequestBody FreelancerRequest free) throws Exception{
		Result result = Result.successInstance();
		service.enroll(free);
		result.setData("성공");
		return result;
	}
	
	//프리랜서 정보 수정시 자신의 아이디로 프리랜서 정보 받기
	@RequestMapping(value="/private/getFreelancerByMemberId/{memberId}", method=RequestMethod.GET)
	public Result getFreelancer(@PathVariable("memberId") String memberId) throws Exception{
		Result result = Result.successInstance();
		result.setData(service.getFreelancerInfo(memberId));
		return result;
	}
	
	
	@RequestMapping(value="/public/getFreelancerByFreelancerId/{freelancerId}", method=RequestMethod.GET)
	public Result getFreelancerByFreelancerId(@PathVariable("freelancerId") String freelancerId) throws Exception{
		Result result = Result.successInstance();
		result.setData(service.getFreelancerInfo(freelancerId));
		return result;
	}
	
	//프리랜서 정보 수정
	@RequestMapping(value="/private/editFreelancer", method=RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}) 
	public Result editFreelancer(@RequestBody FreelancerRequest free) throws Exception {
		Result result = Result.successInstance();
		result.setData(service.edit(free));
		return result;
	}
	
	//프리랜서 카드 표시 정보
	@RequestMapping(value="/public/getFreelancerCards/{paging}", method=RequestMethod.GET)
	public Result getFreelancerCards(@PathVariable("paging") int paging) throws Exception{
		Result result = Result.successInstance();
		result.setData(service.getFreelancerCards(paging));
		return result;
	}

	// 조건 기반(업무, 학력 중 1개) 카드 정보 받기 
	@RequestMapping(value="/public/getFreelancerCards/{job}/{paging}", method=RequestMethod.GET)
	public Result getFreelancerOneOrderingCards(@PathVariable("job") String job, @PathVariable("paging") int paging) throws Exception{
		Result result = Result.successInstance();
		String decodedJob = URLDecoder.decode(job,"UTF-8");
		result.setData(service.getFreelancerCards(decodedJob, paging));
		return result;
	}
	
	// 조건 기반(업무, 학력 둘다) 카드 정보 받기
	@RequestMapping(value="/public/getFreelancerCards/{job}/{edu}/{paging}", method=RequestMethod.GET)
	public Result getFreelancerTwoOrderingCards(@PathVariable("job") String job, @PathVariable("edu") String edu, @PathVariable("paging") int paging) throws Exception{
		Result result = Result.successInstance();
		String decodedJob = URLDecoder.decode(job,"UTF-8");
		String decodedEdu = URLDecoder.decode(edu,"UTF-8");
		result.setData(service.getFreelancerCards(decodedJob, decodedEdu, paging));
		return result;
	}
	
	// 확인 요망
	@RequestMapping(value="/private/suggest", method=RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Result suggest(@PathVariable("job") String job, @PathVariable("edu") String edu, @PathVariable("paging") int paging) throws Exception{
		Result result = Result.successInstance();
		String decodedJob = URLDecoder.decode(job,"UTF-8");
		String decodedEdu = URLDecoder.decode(edu,"UTF-8");
		result.setData(service.getFreelancerCards(decodedJob, decodedEdu, paging));
		return result;
	}
	
}
