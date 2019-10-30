package com.buncha.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobEnrollRequest {

	private String memberId;
	private long jobSeq;
	private String singleLineIntro;
	private String comOrIndi;
	private String companyName;
	private String pic;
	private String phone;
	private String email;
	private String jobType;
	private String startDate;
	private String endDate;
	private String recruitDate;
	private int numRecruit;
	private List<String> jobLocation;
	private String salary;
	private int amount;
	private boolean isNego;
	private String jobDescription;
	
	public boolean getIsNego() {
		return this.isNego;
	}
	
}
