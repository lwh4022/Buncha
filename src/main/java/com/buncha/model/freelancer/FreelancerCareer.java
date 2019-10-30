package com.buncha.model.freelancer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;

import com.buncha.model.converter.CareerTypeConverter;
import com.buncha.model.enumClass.CareerType;

import lombok.Data;

@Data
@Entity
@Table(name="FREELANCER_CAREER")
public class FreelancerCareer{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CAREER_SEQ")
	private long careerSeq;
	
	@Convert(converter = CareerTypeConverter.class)
	@Column(name="CAREER_TYPE")
	private CareerType careerType;
	
	private String company;
	
	private String job;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Date changed;
	
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}$")
	private String startDate;
	
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}$")
	private String endDate;
	
	@ManyToOne(targetEntity=Freelancer.class, fetch=FetchType.EAGER)
	@JoinColumn(name="FREELANCER_SEQ")
	private Freelancer freelancer;
}
