package com.buncha.model.job.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.buncha.model.job.enumClass.LocationType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class LocationTypeConverter implements AttributeConverter<LocationType, Integer>{
	
	@Override
	public Integer convertToDatabaseColumn(LocationType attribute) {
		if(attribute == null) {
			return null;
		} else if (attribute.name() == "서울"){
			return 1;
		} else if (attribute.name() == "부산") {
			return 2;
		} else if (attribute.name() == "인천") {
			return 3;
		} else if (attribute.name() == "대구") {
			return 4;
		} else if (attribute.name() == "울산") {
			return 5;
		} else if (attribute.name() == "광주") {
			return 6;
		} else if (attribute.name() == "경기") {
			return 7;
		} else if (attribute.name() == "강원") {
			return 8;
		} else if (attribute.name() == "경남") {
			return 9;
		} else if (attribute.name() == "경북") {
			return 10;
		} else if (attribute.name() == "충남") {
			return 11;
		} else if (attribute.name() == "충북") {
			return 12;
		} else if (attribute.name() == "전남") {
			return 13;
		} else if (attribute.name() == "전북") {
			return 14;
		} else if (attribute.name() == "제주") {
			return 15;
		} else if (attribute.name() == "세종") {
			return 16;
		} else {
			return 0;
		}
	}

	@Override
	public LocationType convertToEntityAttribute(Integer dbData) {
		
		try {
			if(dbData == null) {
				return null;
			} else if (dbData == 1){
				return LocationType.서울;
			} else if (dbData == 2){
				return LocationType.부산;
			} else if (dbData == 3){
				return LocationType.인천;
			} else if (dbData == 4){
				return LocationType.대구;
			} else if (dbData == 5){
				return LocationType.울산;
			} else if (dbData == 6){
				return LocationType.광주;
			} else if (dbData == 7){
				return LocationType.경기;
			} else if (dbData == 8){
				return LocationType.강원;
			} else if (dbData == 9){
				return LocationType.경남;
			} else if (dbData == 10){
				return LocationType.경북;
			} else if (dbData == 11){
				return LocationType.충남;
			} else if (dbData == 12){
				return LocationType.충북;
			} else if (dbData == 13){
				return LocationType.전남;
			} else if (dbData == 14){
				return LocationType.전북;
			} else if (dbData == 15){
				return LocationType.제주;
			} else if (dbData == 16){
				return LocationType.세종;
			} else {
				return LocationType.에러;
			}
		} catch (IllegalArgumentException e) {
			log.error("잘못된 입력값입니다.", dbData, e);
			throw e;
		}
	}

	
}
