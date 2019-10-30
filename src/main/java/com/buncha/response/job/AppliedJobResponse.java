package com.buncha.response.job;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppliedJobResponse {

	private List<JobShortInfo> jobs;
}
