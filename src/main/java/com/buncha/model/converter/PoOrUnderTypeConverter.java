package com.buncha.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.buncha.model.enumClass.PoOrUnderType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class PoOrUnderTypeConverter implements AttributeConverter<PoOrUnderType, Integer>{
	
	@Override
	public Integer convertToDatabaseColumn(PoOrUnderType attribute) {
		if(attribute == null) {
			return null;
		} else if (attribute.name() == "대학교"){
			return 1;
		} else if (attribute.name() == "대학원") {
			return 2;
		} else {
			return 0;
		}
	}

	@Override
	public PoOrUnderType convertToEntityAttribute(Integer dbData) {
		try {
			if(dbData == null) {
				return null;
			} else if (dbData == 1){
				return PoOrUnderType.대학교;
			} else if (dbData == 2){
				return PoOrUnderType.대학원;
			} else {
				return PoOrUnderType.에러;
			}
		} catch (IllegalArgumentException e) {
			log.error("잘못된 입력값입니다.", dbData, e);
			throw e;
		}
	}

}
