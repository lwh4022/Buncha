package com.buncha.model.freelancer;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name="FREELANCER_FILE")
public class FreelancerFile{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="File_SEQ")
	private long fileSeq;
	
	@Column(name="FILE_NAME")
	private String filename;
	
	@Column(name="FILE_PATH")
	private String fileDownloadUri;
	
	@Column(name="FILE_TYPE")
	private String fileType;
	
	@Column(name="FILE_SIZE")
	private long size;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Date changed;
	
	@ManyToOne(targetEntity=Freelancer.class, fetch=FetchType.EAGER)
	@JoinColumn(name="FREELANCER_SEQ")
	private Freelancer freelancer;
	
}
