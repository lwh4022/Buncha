package com.buncha.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EduRequest {

	private String poOrUnder;
	private String schoolName;
	private String enrollYear;
	private String gradYear;
	private Boolean isStudent;
	private String major;
	private double score;
	private double totalScore;
	private String subMajorType;
	private String subMajor;
	private String thesis;
}
