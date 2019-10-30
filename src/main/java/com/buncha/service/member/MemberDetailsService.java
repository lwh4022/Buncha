package com.buncha.service.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.buncha.model.member.Member;
import com.buncha.model.member.Role;
import com.buncha.repository.member.MemberRepository;
import com.buncha.request.UpdateUserRequest;

@Service
public class MemberDetailsService implements MemberService{

	@Autowired private MemberRepository memberRepo;
	
	@Override
	public Member signup(Member member) throws Exception {
		if(isExist(member.getMemberId())) {
			throw new Exception("존재하는 아이디 입니다.");
		} else {
			Member newMember = memberRepo.save(this.setupForSave(member));
			return newMember;
		}
	}

	@Override
	public boolean isExist(String memberId) {
		Optional<Member> member = memberRepo.findByMemberId(memberId);
		if(member.isPresent()) {
			return true;
		}
		return false;
	}
	
	public Member getMemberInfo(String memberId) throws Exception{
		Optional<Member> member = memberRepo.findByMemberId(memberId);
		if(member.isPresent()) {
			Member loginMember = member.get();
			return loginMember;
		} else {
			throw new Exception("토큰 정보가 유효하지 않습니다.");
		}
	}
	
	private Member setupForSave(Member member){
		String password = member.getPassword();
		String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		Member createdMember = new Member();
		createdMember.setPassword(encodedPassword);
		createdMember.setMemberId(member.getMemberId());
		createdMember.setEmail(member.getEmail());
		createdMember.setRole(Role.MEMBER);
		createdMember.setName(member.getName());
		return createdMember;
	}
	
	private boolean isAccordPassword(Member member, String password) {
		String encodedPassword = member.getPassword();
		return BCrypt.checkpw(password, encodedPassword);
	}

	@Override
	public Member signin(String memberId, String password) throws Exception{
		Optional<Member> member = memberRepo.findByMemberId(memberId);
		if(member.isPresent()) {
			Member loginMember = member.get();
			if(!this.isAccordPassword(loginMember, password)) {
				throw new IllegalStateException("아이디와 비밀번호가 맞지 않습니다.");
			}
			return loginMember;
		} else {
			throw new Exception("존재하지 않는 계정입니다.");
		}
		
	}

	@Override
	public int updateMember(UpdateUserRequest member) throws Exception{
		Optional<Member> currentMember = memberRepo.findByMemberId(member.getMemberId());
		if(!currentMember.isPresent()) {
			throw new Exception("존재하지 않는 아이디 입니다.");
		} else if(!this.isAccordPassword(currentMember.get(), member.getCurrentPassword())) {
			throw new Exception("비밀번호가 일치하지 않습니다.");
		} else if(member.getNewPassword().equals("")) {
			currentMember.get().setMemberId(currentMember.get().getMemberId());
			currentMember.get().setEmail(member.getEmail());
			currentMember.get().setName(member.getName());
			memberRepo.save(currentMember.get());
			return 1;
		} else {
			currentMember.get().setMemberId(currentMember.get().getMemberId());
			currentMember.get().setEmail(member.getEmail());
			currentMember.get().setName(member.getName());
			String password = member.getNewPassword();
			String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			currentMember.get().setPassword(encodedPassword);
			memberRepo.save(currentMember.get());
			return 1;
		}
	}
	
	@Override
	public Member getMe(String memberId) throws Exception {
		Optional<Member> currentMember = memberRepo.findByMemberId(memberId);
		if(!currentMember.isPresent()) {
			throw new Exception("존재하지 않는 아이디입니다.");
		} else {
			return currentMember.get();
		}
	}


}
