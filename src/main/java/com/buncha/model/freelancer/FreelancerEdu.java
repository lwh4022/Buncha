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

import org.hibernate.annotations.CreationTimestamp;

import com.buncha.model.converter.LevelConTypeConverter;
import com.buncha.model.converter.PoOrUnderTypeConverter;
import com.buncha.model.converter.SubMajorTypeConverter;
import com.buncha.model.enumClass.LevelConType;
import com.buncha.model.enumClass.PoOrUnderType;
import com.buncha.model.enumClass.SubMajorType;

import lombok.Data;

@Data
@Entity
@Table(name="FREELANCER_EDU")
public class FreelancerEdu{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="EDU_SEQ")
	private long eduSeq;
	
	@Convert(converter = PoOrUnderTypeConverter.class)
	@Column(name="PO_OR_UNDER")
	private PoOrUnderType postUnder;
	
	private String schoolName;
	
	private String enrollYear;
	
	private String gradYear;
	
	private Boolean isStudent;
	
	private String major;
	
	private double score;
	
	private double totalScore;
	
	@Convert(converter = SubMajorTypeConverter.class)
	@Column(name="SUBMAJOR_TYPE")
	private SubMajorType subMajorType;
	
	private String subMajor;
	
	private String thesis;
	
	@ManyToOne(targetEntity=Freelancer.class, fetch=FetchType.EAGER)
	@JoinColumn(name="FREELANCER_SEQ")
	private Freelancer freelancer;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Date changed;
	
}
