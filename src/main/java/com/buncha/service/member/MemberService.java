package com.buncha.service.member;

import com.buncha.model.member.Member;
import com.buncha.request.UpdateUserRequest;

public interface MemberService {
	
	Member signup(Member member) throws Exception;
	
	boolean isExist(String memberId);
	
	Member signin(String memberId, String password) throws Exception;
	
	int updateMember(UpdateUserRequest member) throws Exception;
	
	Member getMe(String memberId) throws Exception;
	
}
