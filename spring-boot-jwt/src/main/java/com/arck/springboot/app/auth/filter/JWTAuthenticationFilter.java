package com.arck.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final long EXPIRATION_TIME = 900_000; // 15 mins
	
	private SecretKey secretKey;

	private AuthenticationManager authManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login","POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
		username = (username != null) ? username.trim() : "";
		String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
		password = (password != null) ? password : "";
		
		logger.info("Usuario: "+username);
		logger.info("Password: "+password);
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		
		return authManager.authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		User user = ((User)authResult.getPrincipal());
		secretKey = Jwts.SIG.HS512.key().build();
		
		String token = Jwts.builder()
				.claim("firstName",user.getUsername())
		    .expiration(new Date(System.currentTimeMillis()+900000))
		    .signWith(secretKey)
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

}
