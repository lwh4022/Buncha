package com.buncha.response.job;

import java.util.List;

import com.buncha.response.freelancer.EduShortInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApplicantsInfo {

	private long freelancerSeq;
	private long jobSeq;
	private String koName;
	private String selfDescription;
	private List<EduShortInfo> education;
}
