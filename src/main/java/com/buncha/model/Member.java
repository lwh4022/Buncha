package com.buncha.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class Member{

	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private Long seq;
	
	@Column(name="MEMBER_ID", nullable = false, unique = true, length=25)
	private String memberId;
	
	@Column(nullable = false)
	private String password;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Date createdAt;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(nullable = false)
	private String name;
	 
	@Column(nullable = false)
	private String email;
		
}
