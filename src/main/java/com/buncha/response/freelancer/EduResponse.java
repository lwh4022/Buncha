package com.buncha.response.freelancer;

import java.util.Date;


import lombok.Data;

@Data
public class EduResponse {

	private long eduSeq;
	
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
	
	private Date changed;
}
