package com.buncha.response.job;

import java.util.List;

import com.buncha.model.job.Job;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobAllInfoResponse {

	private JobInfo job;
	private List<String> jobLocation;
	
}
