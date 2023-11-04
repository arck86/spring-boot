package com.arck.springboot.app.auth.filter;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.arck.springboot.app.sec.SimpleGrantedAuthorityExtra;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final String SECRET = "my%Secret%Key%Spring-Boot.JWT.123456789,validation";

	private Key SECRET_KEY = null;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
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
		Claims token = null;
		try {
			token= validateToken(request);
			
			tokenValido = true;
		}catch (JwtException | IllegalArgumentException e) {
			System.out.println("por aqui no por favor");
		}
		 
		UsernamePasswordAuthenticationToken authentication = null;
		if(tokenValido) {
			String username = token.getSubject();
			Object roles = token.get("authorities");
			
			String rolesString = roles.toString().replace("authority", "role");
			Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper().readValue(rolesString.getBytes(), SimpleGrantedAuthorityExtra[].class));
			
			authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@SuppressWarnings("deprecation")
	private Claims validateToken(HttpServletRequest request) {
		Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(jwtToken).getBody();
	}
	
	protected boolean requiereAutentificacion(String header) {
		if(header == null || !header.startsWith("Bearer")) {
			return false;
		}
		return true;
	}
}
