package com.arck.springboot.app.auth.service;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.arck.springboot.app.sec.SimpleGrantedAuthorityExtra;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTServiceImpl implements JWTService {
	
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final String SECRET = "my%Secret%Key%Spring-Boot.JWT.123456789,validation";

	@Override
	public String create(Authentication authResult) throws InvalidKeyException, JsonProcessingException {
		Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
		
		User user = ((User)authResult.getPrincipal());
		
		String token = Jwts.builder()
				.claim("sub",user.getUsername())
				.claim("authorities", new ObjectMapper().writeValueAsString(user.getAuthorities()))
		    .expiration(new Date(System.currentTimeMillis()+1800000))
		    .signWith(SECRET_KEY)
		    .compact();
		
		return token;
	}

	@Override
	public boolean validate(String token) {
		Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

		String jwtToken = token.replace(PREFIX, "");
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(jwtToken).getBody();
			return true;
		}catch (JwtException | IllegalArgumentException e) {
			System.out.println("por aqui no por favor");
			return false;
		}
	}

	@Override
	public Claims getClaims(String token) {
		Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

		String jwtToken = token.replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(jwtToken).getBody();
	}

	@Override
	public String getUsername(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) {
		Collection<? extends GrantedAuthority> authorities = null;
		try {
			authorities = Arrays.asList(new ObjectMapper().readValue(token.getBytes(), SimpleGrantedAuthorityExtra[].class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authorities;
	}

	@Override
	public String resolve(String token) {
		return token.replace(PREFIX, "");
	}

}
