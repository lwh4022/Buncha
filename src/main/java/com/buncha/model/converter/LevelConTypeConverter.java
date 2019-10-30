package com.buncha.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.buncha.model.enumClass.LevelConType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class LevelConTypeConverter implements AttributeConverter<LevelConType, Integer>{
	
	@Override
	public Integer convertToDatabaseColumn(LevelConType attribute) {
	
		if(attribute == null) {
			return null;
		} else if (attribute.name() == "원어민"){
			return 1;
		} else if (attribute.name() == "비즈니스회화") {
			return 2;
		} else if (attribute.name() == "일상대화") {
			return 3;
		} else {
			return 0;
		}
	}

	@Override
	public LevelConType convertToEntityAttribute(Integer dbData) {
		
		try {
			if(dbData == null) {
				return null;
			} else if (dbData == 1){
				return LevelConType.원어민;
			} else if (dbData == 2){
				return LevelConType.비즈니스회화;
			} else if (dbData == 3){
				return LevelConType.일상대화;
			} else {
				return LevelConType.에러;
			}
		} catch (IllegalArgumentException e) {
			log.error("잘못된 입력값입니다.", dbData, e);
			throw e;
		}
	}

}
