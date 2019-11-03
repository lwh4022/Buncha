package com.buncha.model.freelancer;

import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;

import com.buncha.model.converter.GenderTypeConverter;
import com.buncha.model.enumClass.GenderType;
import com.buncha.model.member.Member;

import lombok.Data;

@Data
@Entity
public class Freelancer{


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FREELANCER_SEQ")
	private Long freelancerSeq;
	
	private String vnName;
	
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}$")
	private String birthday;
	
	private String address;
	
	@Convert(converter = GenderTypeConverter.class)
	@Column(name="GENDER_TYPE")
	private GenderType genderType;
	
	private String cphone;
	
	@Lob
	private Clob description;
	
	@ManyToOne(targetEntity=Member.class, fetch=FetchType.LAZY, cascade=javax.persistence.CascadeType.ALL)
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Date changed;
	
}
