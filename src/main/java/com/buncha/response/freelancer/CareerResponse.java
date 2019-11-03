package com.buncha.response.freelancer;

import java.util.Date;

import lombok.Data;

@Data
public class CareerResponse {
	
	private long careerSeq;
	
	private String career;
	
	private String company;
	
	private String job;
	
	private Date changed;
	
	private String startDate;
	
	private String endDate;
	
}
