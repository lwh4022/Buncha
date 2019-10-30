package com.buncha.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.buncha.model.enumClass.UnitType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class UnitTypeConverter implements AttributeConverter<UnitType, Integer>{
	
	@Override
	public Integer convertToDatabaseColumn(UnitType attribute) {
		if(attribute == null) {
			return null;
		} else if (attribute.name() == "급"){
			return 1;
		} else if (attribute.name() == "점") {
			return 2;
		} else {
			return 0;
		}
	}

	@Override
	public UnitType convertToEntityAttribute(Integer dbData) {
		
		try {
			if(dbData == null) {
				return null;
			} else if (dbData == 1){
				return UnitType.급;
			} else if (dbData == 2){
				return UnitType.점;
			} else {
				return UnitType.에러;
			}
		} catch (IllegalArgumentException e) {
			log.error("잘못된 입력값입니다.", dbData, e);
			throw e;
		}
		
	}

}
