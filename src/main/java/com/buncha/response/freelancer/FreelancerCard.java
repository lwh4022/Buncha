package com.buncha.response.freelancer;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreelancerCard {

	private String freelancerId;
	private String koName;
	private String selfDescription;
	private List<String> jobAvailables;
	private long fileSeq;
	private List<EduShortInfo> eduList;
}
