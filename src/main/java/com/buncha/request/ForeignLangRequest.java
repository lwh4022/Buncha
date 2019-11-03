package com.buncha.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ForeignLangRequest {

	private String language;
	private String levelCon;
	private String cerOrCon;
	private String testName;
	private double score;
	private double totalScore;
	private String unit;
	private String gotDate;
}
