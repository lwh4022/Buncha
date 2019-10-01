package com.buncha.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.buncha.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("jwtService")
public class JwtServiceImpl implements JwtService{
	
	private static final String SALT = "lwh4022Secret";

	@Override
	public <T> String create(String key, T data, String subject) {
		
		String jwt = Jwts.builder()
						.setHeaderParam("typ","JWT")
						.setHeaderParam("regDate", System.currentTimeMillis())
						.setSubject(subject)
						.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
						.claim(key, data)
						.signWith(SignatureAlgorithm.HS512, this.generateKey())
						.compact();
		return jwt;
	}
	
	private byte[] generateKey() {
		
		byte[] key = null;
		try { 
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if(log.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				log.error("Making JWT Key error ::{}", e.getMessage());
			}
		}
		return key;
	}

	@Override
	public Map<String, Object> get(String key) {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader("Authorization").split(" ")[1];
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
						 .setSigningKey(SALT.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error(e.getMessage());
			}
			throw new UnauthorizedException();
		}
		@SuppressWarnings("unchecked")
        Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
		return value;
	}

	@Override
	public String getMemberId() {
		String memberId = (String) this.get("member").get("id");
		return new String(memberId);
	}

	@Override
	public boolean isUsable(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
			return true;
		} catch (Exception e) {
			if(log.isInfoEnabled()) {
				e.printStackTrace();
			}else {
				log.error(e.getMessage());
			}
			throw new UnauthorizedException();
		}
	}
}
