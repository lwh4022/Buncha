package com.buncha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



@SpringBootApplication
public class BunchaApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		
		SpringApplication.run(BunchaApplication.class, args);
		
	}
	
	@Override 
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { 
		return builder.sources(BunchaApplication.class);
	}

	


}