package com.buncha.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.buncha.config.SecurityUser;
import com.buncha.model.Member;
import com.buncha.repository.MemberRepository;

@Service
public class MemberDetailsService implements UserDetailsService{

	private final int ERROR = 0;
	private final int SUCCESS = 1;
	
	@Autowired private MemberRepository memberRepo;
	
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		Optional<Member> optional = memberRepo.findByMemberId(memberId);
		if(!optional.isPresent()) {
			throw new UsernameNotFoundException(memberId + "사용자 없음");
		} else {
			Member member = optional.get();
			return new SecurityUser(member);
		}
	}
	
	public int signup(Member member) throws Exception {
		Optional<Member> newMember = memberRepo.findByMemberId(member.getMemberId());
		if(newMember.isPresent()) {
			return 0;
		} else {
			memberRepo.save(member);
			return 1;
		}
	}
	
	public Member login(String id, String password) throws Exception {
		Member member = memberRepo.findByMemberId(id).orElseThrow(()-> new Exception("아이디가 업습니다"));
		if(! this.isAccordPassword(member, password)) {
			throw new IllegalStateException("로그인 정보가 일치하지 않습니다.");
		}
		
		return member;
	}
	
	private boolean isAccordPassword(Member member, String password) {
		String encodedPassword = member.getPassword();
		return BCrypt.checkpw(password, encodedPassword);
	}
	
	public boolean idCheck(String memberId) throws Exception {
		Optional<Member> member = memberRepo.findByMemberId(memberId);
		if(member.isPresent()) {
			return false;
		} else {
			return true;
		}
	}
}
