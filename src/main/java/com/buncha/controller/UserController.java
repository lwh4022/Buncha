package com.buncha.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.buncha.model.member.Member;
import com.buncha.request.UpdateUserRequest;
import com.buncha.response.LoginResponse;
import com.buncha.response.Result;
import com.buncha.service.jwt.JwtService;
import com.buncha.service.member.MemberDetailsService;

@RestController
public class UserController {
	
	@Autowired
	private MemberDetailsService memberService;
	
	@Autowired
	private JwtService jwtService;
	
	// 회원가입
	@RequestMapping(value="/signup", method=RequestMethod.POST, consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Result insertUser(@RequestBody Member member) throws Exception {
		Result result = Result.successInstance();
		memberService.signup(member);
		result.setData("성공");
		return result;
	}
	
	//로그인
	@RequestMapping(value="/signin", method=RequestMethod.POST, consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Result createAuthenticationToken(@RequestBody Member member, HttpServletResponse response) throws Exception {
		Result result = Result.successInstance();
		if(member == null || member.getMemberId() == "" || member.getPassword() == "") {
			throw new Exception("로그인 정보가 유효하지 않습니다.");
		}
		Member loginMember = memberService.signin(member.getMemberId(), member.getPassword());
		String token = jwtService.create("member", loginMember, "user");
		response.setHeader("Authorization", token);
		result.setData(new LoginResponse(token, loginMember.getMemberId(), loginMember.getRole().name()));
		return result;
	}
	
	//토큰으로 정보 받기
	@RequestMapping(value="/private/authCheck", method=RequestMethod.GET)
	public Result checkToken(HttpServletRequest request) throws Exception{
		Result result = Result.successInstance();
		String memberId = jwtService.get("member").get("memberId").toString();
		Member loginMember = memberService.getMemberInfo(memberId);
		result.setData(new LoginResponse(request.getHeader("Authorization"), loginMember.getMemberId(), loginMember.getRole().name())); 
		return result;
	}
	

	// 회원정보 변경
	@RequestMapping(value="/private/user", method=RequestMethod.PUT, consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Result updateUser(@RequestBody UpdateUserRequest member) throws Exception {
		Result result = Result.successInstance();
		int output = memberService.updateMember(member);
		result.setData(output);
		return result;
	}
	
	// 아이디 체크
	@RequestMapping(value="/idCheck", method=RequestMethod.POST, consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Result idCheck(@RequestBody Member member) throws Exception {
		Result result = Result.successInstance();
		boolean check = memberService.isExist(member.getMemberId());
		if(check) {
			return result.setData("존재하는 아이디");
		} else {
			return result.setData("가능");
		}
	}
	
	//회원정보 가져오기
	@RequestMapping(value="/private/getMe/{memberId}", method=RequestMethod.GET)
	public Result getMe(@PathVariable("memberId") String memberId) throws Exception {
		Result result = Result.successInstance();
		Member memberInfo = memberService.getMe(memberId);
		result.setData(memberInfo);
		return result;
	}
}
