package com.buncha.response.freelancer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EduShortInfo {

	private String freelancerId;
	private long freelancerSeq;
	private String poOrUnder;
	private String enrollYear;
	private String gradYear;
	private boolean isStudent;
	
	public void setIsStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}
	
	public boolean getIsStudent() {
		return this.isStudent;
	}
}
