package com.arck.springboot.form.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class McvConfig implements WebMvcConfigurer{

	@Autowired
	private HandlerInterceptor tiempoInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tiempoInterceptor).addPathPatterns("/form**","/ver**");
	}

}
