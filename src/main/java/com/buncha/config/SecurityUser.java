package com.buncha.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.buncha.model.Member;

public class SecurityUser extends User{

	private static final long serialVersionUID = 2025200309947740597L;

	public SecurityUser(Member member) {
		super(member.getMemberId(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
	}
}
