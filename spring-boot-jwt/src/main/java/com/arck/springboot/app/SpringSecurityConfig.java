package com.arck.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.arck.springboot.app.auth.filter.JWTAuthenticationFilter;
import com.arck.springboot.app.auth.filter.JWTAuthorizationFilter;
import com.arck.springboot.app.auth.handler.LoginSuccessHandler;
import com.arck.springboot.app.service.JpaUserDetailsService;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig{
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	 @Autowired
     private BCryptPasswordEncoder passwordEncoder;
		
	@Autowired
	private JpaUserDetailsService userDetailService;
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz->
			authz.requestMatchers("/", "/css/**", "/js/**", "/images/**","/locale").permitAll()
//			.requestMatchers("/listar").hasAnyRole("USER")
//			.requestMatchers("/ver/**").hasAnyRole("USER")
//			.requestMatchers("/uploads/**").hasAnyRole("USER")
//			.requestMatchers("/form/**").hasAnyRole("ADMIN")
//			.requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
//			.requestMatchers("/factura/**").hasAnyRole("ADMIN")
			.anyRequest().authenticated())
//		.formLogin(login -> login.successHandler(loginSuccessHandler).loginPage("/login").permitAll())
//		.logout(logout -> logout.permitAll())
//		.exceptionHandling(exception -> exception.accessDeniedPage("/error_403"))
		.csrf(csrf ->csrf.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))	
		.addFilter(new JWTAuthorizationFilter(authenticationConfiguration.getAuthenticationManager()))	;

		  return http.build();
	}
	
	@Autowired
	public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
		build.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
	}

	
}
