package com.arck.springboot.app.auth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		SessionFlashMapManager sessionFlashMapManager = new SessionFlashMapManager();
		FlashMap flash = new FlashMap();
		
		flash.put("success", "Hola "+authentication.getName()+", has iniciado sesion con exito.");
		sessionFlashMapManager.saveOutputFlashMap(flash, request, response);
		
		if(authentication!= null) {
			logger.info("Inicio de session del usuario: "+authentication.getName());
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
