package com.buncha.model.freelancer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.buncha.model.converter.CareerTypeConverter;
import com.buncha.model.enumClass.CareerType;

import lombok.Data;

@Data
@Entity
@Table(name="FREELANCER_JOB_AVAILABLE")
public class FreelancerJobAvailable implements Serializable{

	private static final long serialVersionUID = 2248182279440391243L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="JOB_AVAILABLE_SEQ")
	private long jobAvailableSeq;
	
	@Enumerated(EnumType.STRING)
	@Convert(converter = CareerTypeConverter.class)
	@Column(name="JOB_AVAILABLE")
	private CareerType jobAvailable;
	
	@ManyToOne(targetEntity=Freelancer.class, fetch=FetchType.EAGER)
	@JoinColumn(name="FREELANCER_SEQ")
	private Freelancer freelancer;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Date changed;
}
