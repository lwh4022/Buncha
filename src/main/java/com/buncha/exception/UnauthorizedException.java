package com.buncha.exception;

public class UnauthorizedException extends RuntimeException{

	private static final long serialVersionUID = -6972511406389747935L;
	
	public UnauthorizedException() {
		super("계정 권한이 유효하지 않습니다.\n 다시 로그인을 해주세요");
	}
	
}
