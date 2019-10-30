package com.buncha.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.buncha.model.enumClass.SubMajorType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class SubMajorTypeConverter implements AttributeConverter<SubMajorType, Integer>{
	
	@Override
	public Integer convertToDatabaseColumn(SubMajorType attribute) {
		if(attribute == null) {
			return null;
		} else if (attribute.name() == "없음"){
			return 1;
		} else if (attribute.name() == "부전공") {
			return 2;
		} else if (attribute.name() == "이중전공") {
			return 3;
		} else if (attribute.name() == "복수전공") {
			return 4;
		} else {
			return 0;
		}
	}

	@Override
	public SubMajorType convertToEntityAttribute(Integer dbData) {
		try {
			if(dbData == null) {
				return null;
			} else if (dbData == 1){
				return SubMajorType.없음;
			} else if (dbData == 2){
				return SubMajorType.부전공;
			} else if (dbData == 3) {
				return SubMajorType.이중전공;
			} else if (dbData == 4) {
				return SubMajorType.복수전공;
			} else {
				return SubMajorType.에러;
			}
		} catch (IllegalArgumentException e) {
			log.error("잘못된 입력값입니다.", dbData, e);
			throw e;
		}
	}

	
}
