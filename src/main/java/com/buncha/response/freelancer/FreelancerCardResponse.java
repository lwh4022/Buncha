package com.buncha.response.freelancer;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreelancerCardResponse {

	List<FreelancerCard> info;
	boolean isFinal;
}
