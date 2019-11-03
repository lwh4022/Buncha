package com.buncha.model.job.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.buncha.model.job.enumClass.JobType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class JobTypeConverter implements AttributeConverter<JobType, Integer>{

	@Override
	public Integer convertToDatabaseColumn(JobType attribute) {
		if(attribute == null) {
			return null;
		} else if (attribute.name() == "통역"){
			return 1;
		} else if (attribute.name() == "번역") {
			return 2;
		} else if (attribute.name() == "단체강의") {
			return 3;
		} else if (attribute.name() == "개인과외") {
			return 4;
		} else {
			return 0;
		}
	}

	@Override
	public JobType convertToEntityAttribute(Integer dbData) {
		try {
			if(dbData == null) {
				return null;
			} else if (dbData == 1){
				return JobType.통역;
			} else if (dbData == 2){
				return JobType.번역;
			} else if (dbData == 3){
				return JobType.단체강의;
			} else if (dbData == 4){
				return JobType.개인과외;
			} else {
				return JobType.에러;
			}
		} catch (IllegalArgumentException e) {
			log.error("잘못된 입력값입니다.", dbData, e);
			throw e;
		}
		
	}

}
