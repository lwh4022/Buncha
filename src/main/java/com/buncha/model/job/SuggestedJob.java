package com.buncha.model.job;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.buncha.model.freelancer.Freelancer;
import com.buncha.model.member.Member;

import lombok.Data;

@Data
@Entity
public class SuggestedJob {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SUGGESTED_JOB_SEQ")
	private long suggestedJobSeq;
	
	@ManyToOne(targetEntity=Freelancer.class, fetch=FetchType.LAZY)
	@JoinColumn(name="FREELANCER_SEQ", referencedColumnName="FREELANCER_SEQ")
	private Freelancer freelancer;
	
	@ManyToOne(targetEntity=Job.class, fetch=FetchType.LAZY)
	@JoinColumn(name="JOB_SEQ", referencedColumnName="JOB_SEQ")
	private Job job;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Date changed;
}
