package com.buncha.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SuggestJobsRequest {

	private List<Long> jobs;
	private long freelancerSeq;
}
