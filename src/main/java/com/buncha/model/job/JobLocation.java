package com.buncha.model.job;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.buncha.model.job.converter.LocationTypeConverter;
import com.buncha.model.job.enumClass.LocationType;

import lombok.Data;

@Data
@Entity
public class JobLocation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long jobLocationSeq;
	
	@Convert(converter = LocationTypeConverter.class)
	@Column(name="location")
	private LocationType location;
	
	@ManyToOne(targetEntity=Job.class, fetch=FetchType.EAGER)
	@JoinColumn(name="JOB_SEQ", referencedColumnName="JOB_SEQ")
	private Job job;
}
