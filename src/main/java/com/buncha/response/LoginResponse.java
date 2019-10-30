package com.buncha.response;

import java.io.Serializable;

public class LoginResponse implements Serializable{

	private static final long serialVersionUID = 2680789857610938505L;
	
	private final String jwtToken;
	private final String memberId;
	private final String role;
	
	public LoginResponse(String jwtToken, String memberId, String role) {
		this.jwtToken = jwtToken;
		this.memberId = memberId;
		this.role =role;
	}
	
	public String getToken() {
		return this.jwtToken;
	}
	
	public String getMemberId() {
		return this.memberId;
	}
	
	public String getRole() {
		return this.role;
	}

}
