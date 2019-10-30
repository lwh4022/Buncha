package com.buncha.model.job.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.buncha.model.job.enumClass.ComOrIndiType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class ComOrIndiTypeConverter implements AttributeConverter<ComOrIndiType, Integer>{
	
	@Override
	public Integer convertToDatabaseColumn(ComOrIndiType attribute) {
		if(attribute == null) {
			return null;
		} else if (attribute.name() == "회사"){
			return 1;
		} else if (attribute.name() == "개인") {
			return 2;
		} else {
			return 0;
		}
	
	}

	@Override
	public ComOrIndiType convertToEntityAttribute(Integer dbData) {
		try {
			if(dbData == null) {
				return null;
			} else if (dbData == 1){
				return ComOrIndiType.회사;
			} else if (dbData == 2){
				return ComOrIndiType.개인;
			} else {
				return ComOrIndiType.에러;
			}
		} catch (IllegalArgumentException e) {
			log.error("잘못된 입력값입니다.", dbData, e);
			throw e;
		}
	}

	
}
