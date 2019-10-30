package com.buncha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.buncha.config.jwt.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	private static final String[] EXCLUDE_PATHS = {			
			"/**",
			"/signup",
			"/error",
			"/signin",
			"/idCheck",
			"/public/**"
	};
	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(jwtInterceptor)
			.addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATHS);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())
				.allowCredentials(false)
				.maxAge(3600);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(
				"/css/**",
				"/js/**",
				"/fonts/**",
				"/img/**",
				"/media/**",
				"/favicon.ico/**")
				.addResourceLocations(
						"classpath:/static/css/",
						"classpath:/static/js/",
						"classpath:/static/fonts/",
						"classpath:/static/img/",
						"classpath:/static/media/",
						"classpath:/static/");
	}
}
