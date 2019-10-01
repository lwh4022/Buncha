package com.buncha.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.buncha.model.Member;
import com.buncha.model.Role;
import com.buncha.repository.MemberRepository;
import com.buncha.service.JwtService;
import com.buncha.service.MemberDetailsService;

@RestController
public class UserController {
	
	public static final int ERROR = 0;
	public static final int SUCCESS = 1;
	public static final int IDEXISTED = 0;
	public static final int IDNOTEXISTED = 1;
	
	
	@Autowired
	private MemberDetailsService memberService;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private JwtService jwtService;
	
	// 회원가입
	@PostMapping(path="/signup")
	public int insertUser(@RequestBody Member member) throws Exception {
		BCryptPasswordEncoder passwordEncoder  =  new BCryptPasswordEncoder();
		Member newMember = new Member();
		newMember.setMemberId(member.getMemberId());
		newMember.setPassword(passwordEncoder.encode(member.getPassword()));
		newMember.setRole(Role.MEMBER);
		newMember.setEmail(member.getEmail());
		int result = memberService.signup(newMember);
		return result;
	}
	
	// 로그인
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public String login(@RequestBody Member member) {
		Member loginMember = new Member();
		try {
			String token = jwtService.create("member", loginMember, "user");
			System.out.println(token);
			return token;
		} catch (Exception e) {
			return "failed";
		}
	}

	// 회원정보 변경
	@RequestMapping(value="/user", method=RequestMethod.PUT)
	public int userEdit(@RequestBody Member member) throws Exception {
		Optional<Member> memberToEdit = memberRepo.findByMemberId(member.getMemberId());
		if(memberToEdit.isPresent()) {
			memberToEdit.get().setEmail(member.getEmail());
			memberToEdit.get().setName(member.getName());
			memberToEdit.get().setPassword(member.getPassword());
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	// 아이디 체크
	@RequestMapping(value="/idcheck", method=RequestMethod.POST)
	public int idCheck(@RequestBody Member member) throws Exception {
		boolean check = memberService.idCheck(member.getMemberId());
		if(check) {
			return IDEXISTED;
		} else {
			return IDNOTEXISTED;
		}
	}
}
