package com.arck.springboot.app.auth.filter;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.arck.springboot.app.models.entity.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final long EXPIRATION_TIME = 900_000; // 30 mins
	private final String SECRET = "my%Secret%Key%Spring-Boot.JWT.123456789,validation";

	private Key SECRET_KEY = null;

	private AuthenticationManager authManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
		username = (username != null) ? username.trim() : null;
		String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
		password = (password != null) ? password : null;
		
		if(username != null && password != null) {
			logger.info("Obtenidos por parametros");
		}else {
			Usuario user = null;
			try {
				user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class) ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			username = user.getUsername();
			password = user.getPassword();

			logger.info("Obtenidos por JSON");
		}
		logger.info("Usuario: "+username);
		logger.info("Password: "+password);
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		
		return authManager.authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
		
		User user = ((User)authResult.getPrincipal());
		
		String token = Jwts.builder()
				.claim("sub",user.getUsername())
				.claim("authorities", new ObjectMapper().writeValueAsString(user.getAuthorities()))
		    .expiration(new Date(System.currentTimeMillis()+1800000))
		    .signWith(SECRET_KEY)
		    .compact();


	    response.addHeader("Authorization", "Bearer "+token);
	    Map<String, Object> body = new HashMap<String, Object>();
	    body.put("token",token);
	    body.put("user", user);
	    body.put("mensaje","Buenas: "+user.getUsername());
	    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
	    response.setStatus(200);
		response.setContentType("application/json");
	    
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mensaje", "Error de autentificacion: usuario o contraseña incorrecto");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
	    response.setStatus(401);
		response.setContentType("application/json");
	}

	
}
