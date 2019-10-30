package com.buncha.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.buncha.model.enumClass.GenderType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class GenderTypeConverter implements AttributeConverter<GenderType, Integer>{
	
	@Override
	public Integer convertToDatabaseColumn(GenderType attribute) {
		
		if(attribute == null) {
			return null;
		} else if (attribute.name() == "남자"){
			return 1;
		} else if (attribute.name() == "여자") {
			return 2;
		} else {
			return 0;
		}
	}

	@Override
	public GenderType convertToEntityAttribute(Integer dbData) {
		
		try {
			if(dbData == null) {
				return null;
			} else if (dbData == 1){
				return GenderType.남자;
			} else if (dbData == 2){
				return GenderType.여자;
			} else {
				return GenderType.에러;
			}
		} catch (IllegalArgumentException e) {
			log.error("잘못된 입력값입니다.", dbData, e);
			throw e;
		}
	}

	
}
