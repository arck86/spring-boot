package com.arck.springboot.form.app.interceptors;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TiempoInterceptor implements HandlerInterceptor{
	
	private  static final Logger log = LoggerFactory.getLogger(TiempoInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getMethod().equalsIgnoreCase("post")){
			return true;
		}
		
		if(handler instanceof HandlerMethod){
			log.info("es un metodo del controlador:"+ ((HandlerMethod)handler).getMethod().getName());
		}
		log.info("TiempoInterceptor: preHandle() entrando ........");
		
		long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		Random random = new Random();
		Integer demora = random.nextInt(500);
		Thread.sleep(demora);
		
//		response.sendRedirect(request.getContextPath().concat("/Error"));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(request.getMethod().equalsIgnoreCase("post")){
			return;
		}
		long tiempoInicio =(long)request.getAttribute("tiempoInicio");
		long tiempoFin = System.currentTimeMillis();
		
		if(modelAndView!= null ) {

			modelAndView.addObject("tiempoTranscurrido", tiempoFin-tiempoInicio);
			log.info("Tiempo Transcurrido ........ "+ (tiempoFin-tiempoInicio));
			log.info("TiempoInterceptor: postHandle() saliendo ........");
		}
	}

}
