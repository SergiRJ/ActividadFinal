package com.nttdata.actividadfinal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nttdata.actividadfinal.repository.UsuarioRepoJPA;
import com.nttdata.actividadfinal.repository.entity.Usuario;
import com.nttdata.actividadfinal.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService  {
	@Autowired
	UsuarioRepoJPA usuarioRepo;

	@Override
	public List<Usuario> listaPorRol(String rol) {
				return usuarioRepo.findByRolLike(rol);
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		return usuarioRepo.findById(username).get();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return buscarPorUsername(username);
	}
} 