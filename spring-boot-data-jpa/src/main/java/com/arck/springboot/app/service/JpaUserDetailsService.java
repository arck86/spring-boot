package com.arck.springboot.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arck.springboot.app.dao.IUsuarioDao;
import com.arck.springboot.app.models.entity.Rol;
import com.arck.springboot.app.models.entity.Usuario;

@Service
public class JpaUserDetailsService implements UserDetailsService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUsuarioDao usuarioDao;


	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("El usuario no existe: "+username);
			throw new UsernameNotFoundException("Este no entra aquí");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for(Rol rol : usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
		}
		
		if(authorities.isEmpty()) {
			logger.error("El usuario no no tiene roles: "+username);
			throw new UsernameNotFoundException("Este no entra aquí, pero sabemos que existe");
		}
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}
