package com.buncha.request;

import java.util.List;

import com.buncha.model.freelancer.FreelancerCareer;
import com.buncha.model.freelancer.FreelancerEdu;
import com.buncha.model.freelancer.FreelancerForeignLang;

import lombok.Data;

@Data
public class FreelancerRequest {

	private String memberId;
	private String vnName;
	private String birthday;
	private String gender;
	private String cphone;
	private String address;
	private String description;
	
	private List<EduRequest> eduList;
	private List<CareerRequest> careerList;
	private List<ForeignLangRequest> foreignLangList;
	private String[] jobs;
}
