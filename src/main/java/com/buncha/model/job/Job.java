package com.buncha.model.job;

import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

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
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;

import com.buncha.model.job.converter.JobTypeConverter;
import com.buncha.model.job.enumClass.JobType;
import com.buncha.model.member.Member;

import lombok.Data;

@Data
@Entity
@Table(name="JOB")
public class Job {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="JOB_SEQ")
	private long jobSeq;
	
	@Lob
	private Clob singleLineIntro;
	
	private String companyName;
	
	private String pic;
	
	private String comOrIndi;
	
	private String phone;
	
	@Email
	private String email;
	
	@Column(name="JOB_TYPE")
	private String jobType;
	
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}$")
	private String startDate;
	
	private Long startDateTime;
	
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}$")
	private String endDate;
	
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}$")
	private String recruitDate;
	
	private Long recruitDateTime;
	
	private int numRecurit;
	
	private String salary;
	
	private int amount;
	
	private boolean isNego;
	
	@Lob
	private Clob jobDescription;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Date changed;
	
	@ManyToOne(targetEntity=Member.class, fetch=FetchType.LAZY)
	@JoinColumn(name="MEMBER_ID")
	private Member member;
	
	public void setIsNego(boolean nego) {
		this.isNego = nego;
	}
	
	public boolean getIsNego() {
		return this.isNego;
	}
	
	public void setStartDateTime(String startDate) throws ParseException {
		int year = Integer.parseInt(startDate.substring(0,4));
		int month = Integer.parseInt(startDate.substring(5,7));
		int date = Integer.parseInt(startDate.substring(8,10));
		LocalDateTime ldt = LocalDateTime.of(year, month, date, 0, 0, 0);
		ZonedDateTime zdt = ldt.atZone(ZoneId.of("Z"));
		this.startDateTime = zdt.toInstant().toEpochMilli() / 1000 / 60 / 60 / 24;
	}
	
	public long getStartDateTime() {
		return this.startDateTime;
	}
	
	public void setRecruitDateTime(String recruitDate) throws ParseException {
		int year = Integer.parseInt(recruitDate.substring(0,4));
		int month = Integer.parseInt(recruitDate.substring(5,7));
		int date = Integer.parseInt(recruitDate.substring(8,10));
		LocalDateTime ldt = LocalDateTime.of(year, month, date, 0, 0, 0);
		ZonedDateTime zdt = ldt.atZone(ZoneId.of("Z"));
		this.recruitDateTime = zdt.toInstant().toEpochMilli() / 1000 / 60 / 60 / 24;
	}
	
	public long getrecruitDateTime() {
		return this.recruitDateTime;
	}
}
