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

import com.buncha.model.converter.LevelConTypeConverter;
import com.buncha.model.converter.UnitTypeConverter;
import com.buncha.model.enumClass.LevelConType;
import com.buncha.model.enumClass.UnitType;

import lombok.Data;

@Data
@Entity
@Table(name="FREELANCER_FOREIGNLANG")
public class FreelancerForeignLang{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FOREIGNLANG_SEQ")
	private Long foreignLangSeq;
	
	private String language;
	
	private String cerOrCon;
	
	@Convert(converter = LevelConTypeConverter.class)
	@Column(name="LEVEL_CON_TYPE")
	private LevelConType levelConType;
	
	private String testName;
	
	private double score;
	
	private double totalScore;

	@Convert(converter = UnitTypeConverter.class)
	@Column(name="UNIT_TYPE")
	private UnitType unitType;
	
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}$")
	private String gotDate;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Date changed;
	
	@ManyToOne(targetEntity=Freelancer.class, fetch=FetchType.EAGER)
	@JoinColumn(name="FREELANCER_SEQ")
	private Freelancer freelancer;
}
