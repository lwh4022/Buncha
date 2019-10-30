package com.buncha.response.freelancer;

import java.util.Date;

import lombok.Data;

@Data
public class ForeignLangResponse {

private Long foreignLangSeq;
	
	private String language;
	
	private String levelCon;
	
	private String cerOrCon;
	
	private String testName;
	
	private double score;
	
	private double totalScore;

	private String unitType;
	
	private String gotDate;
	
	private Date changed;
}
