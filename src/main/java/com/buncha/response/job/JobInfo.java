package com.buncha.response.job;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobInfo {
	
	
private long jobSeq;
	
	private String singleLineIntro;
	
	private String companyName;
	
	private String comOrIndi;
	
	private String pic;
	
	private String phone;
	
	private String email;
	
	private String jobType;
	
	private String startDate;
	
	private String endDate;
	
	private String recruitDate;
	
	private int numRecruit;
	
	private String salary;
	
	private int amount;
	
	private boolean isNego;
	
	private String jobDescription;
	
	private Date changed;
	
	public void setIsNego(boolean nego) {
		this.isNego = nego;
	}
	
	public boolean getIsNego() {
		return this.isNego;
	}

}
