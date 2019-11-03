package com.buncha.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.buncha.model.enumClass.CareerType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class CareerTypeConverter implements AttributeConverter<CareerType, Integer>{

	@Override
	public Integer convertToDatabaseColumn(CareerType attribute) {
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
	public CareerType convertToEntityAttribute(Integer dbData) {
		try {
			if(dbData == null) {
				return null;
			} else if (dbData == 1){
				return CareerType.통역;
			} else if (dbData == 2){
				return CareerType.번역;
			} else if (dbData == 3){
				return CareerType.단체강의;
			} else if (dbData == 4){
				return CareerType.개인과외;
			} else {
				return CareerType.에러;
			}
		} catch (IllegalArgumentException e) {
			log.error("잘못된 입력값입니다.", dbData, e);
			throw e;
		}
		
	}

}
