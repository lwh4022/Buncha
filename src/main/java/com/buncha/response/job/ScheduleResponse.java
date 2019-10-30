package com.buncha.response.job;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleResponse {
	List<JobShortInfo> appliedJobs;
	List<JobShortInfo> suggestedJobs;
}
