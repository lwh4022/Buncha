package com.buncha.response.job;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobShortInfo {

	private long num;
	private String recruiting;
	private String title;
	private String startDate;
	private String endDate;
	private int recruitNum;
	private Date enrolledAt;
	private String contents;
	private String jobType;
	private List<String> jobLocation;
	private String salary;
	private int amount;
	private boolean isNego;
	
	public void setIsNego(boolean isNego){
		this.isNego = isNego;
	}
	
	public boolean getIsNego() {
		return this.isNego;
	}
}
