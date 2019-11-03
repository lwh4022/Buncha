package com.buncha.response.job;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetJobResponse {

	private JobShortInfoResponse info;
	private boolean isFinal;
	
	public void setIsFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	public boolean getIsFinal() {
		return this.isFinal;
	}
}
