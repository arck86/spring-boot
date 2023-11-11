package com.arck.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.arck.springboot.app.auth.service.JWTService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	
	private final String HEADER = "Authorization";

	private JWTService jwtService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(HEADER);
		
		if(!requiereAutentificacion(header)) {
			chain.doFilter(request, response);
			return;
		}
		boolean tokenValido= false;
		try {
			tokenValido = jwtService.validate(request.getHeader(HEADER));
			
			tokenValido = true;
		}catch (JwtException | IllegalArgumentException e) {
			System.out.println("por aqui no por favor");
		}
		 
		UsernamePasswordAuthenticationToken authentication = null;
		if(tokenValido) {
			Claims token = jwtService.getClaims(request.getHeader(HEADER));
			String username = token.getSubject();
			Object roles = token.get("authorities");
			
			String rolesString = roles.toString().replace("authority", "role");
			Collection<? extends GrantedAuthority> authorities = jwtService.getRoles(rolesString);
			
			authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(request, response);
	}
	
	protected boolean requiereAutentificacion(String header) {
		if(header == null || !header.startsWith("Bearer")) {
			return false;
		}
		return true;
	}
}
