package com.arck.springboot.web.app.interceptors;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HorarioInterceptor implements HandlerInterceptor {
	
	@Value("${config.horario.apertura}")
	private Integer apertura;
	
	@Value("${config.horario.cierre}")
	private Integer cierre;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		
		if(hora >= apertura && hora <= cierre){
			
			StringBuilder mensaje = new StringBuilder("Bienvenido al horario de atencion al cliente (14 - 18)");
			request.setAttribute("mensaje", mensaje.toString());
			return true;
		}
		response.sendRedirect(request.getContextPath().concat("/cerrado"));
		request.setAttribute("error", "Pero como has llegado aqui animal, tira para casa y vuelves mañana");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String mensaje = (String) request.getAttribute("mensaje");
		if(modelAndView != null && handler instanceof HandlerMethod) {
			modelAndView.addObject("horario", mensaje);
		}
	}
	
	

}
