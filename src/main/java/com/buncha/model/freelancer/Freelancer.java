package com.buncha.model.freelancer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.buncha.model.Member;

import lombok.Data;

@Data
@Entity
public class Freelancer {

	@Id@GeneratedValue
	private Long seq;
	private String koName;
	private String vnName;
	private String thumbFileName;
	private String birthday;
	private String email;
}
