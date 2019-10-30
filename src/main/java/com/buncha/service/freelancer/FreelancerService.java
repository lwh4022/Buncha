package com.buncha.service.freelancer;


import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.freelancer.FreelancerFile;
import com.buncha.request.FreelancerRequest;
import com.buncha.response.freelancer.FileUploadResponse;
import com.buncha.response.freelancer.FreelancerCardResponse;
import com.buncha.response.freelancer.FreelancerInfoResponse;

public interface FreelancerService {

	
	String enroll(FreelancerRequest free) throws Exception;
	String edit(FreelancerRequest free) throws Exception;
	Freelancer existingFreelancer(FreelancerRequest free) throws Exception;
	
	String enrollThumb(String memberId, FileUploadResponse file) throws Exception;
	
	FreelancerFile getThumbNail(String memberId) throws Exception;
	
	FreelancerCardResponse getFreelancerCards(int paging) throws Exception;
	
	FreelancerCardResponse getFreelancerCards(String job, int paging) throws Exception;
	
	FreelancerCardResponse getFreelancerCards(String job, String edu, int paging) throws Exception;
	
	FreelancerInfoResponse getFreelancerInfo(String memberId) throws Exception;
}
