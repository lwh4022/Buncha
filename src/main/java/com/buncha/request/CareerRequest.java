package com.buncha.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CareerRequest {

	private String careerType;
	private String company;
	private String job;
	private String startDate;
	private String endDate;
}
