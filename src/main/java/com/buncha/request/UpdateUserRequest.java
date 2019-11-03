package com.buncha.request;

import lombok.Data;

@Data
public class UpdateUserRequest {

	private String memberId;
	private String currentPassword;
	private String newPassword;
	private String name;
	private String email;
}
