package com.buncha.response.freelancer;

import java.util.Date;
import java.util.List;


import lombok.Data;

@Data
public class FreelancerInfoResponse {

	private String vnName;
	private String birthday;
	private String cphone;
	private Date changed;
	private String koName;
	private String gender;
	private String address;
	private String description;
	private String email;
	private long freelancerSeq;
	
	private List<CareerResponse> career;
	private List<EduResponse> edu;
	private List<ForeignLangResponse> foreignLang;
	private List<String> jobs;
}
