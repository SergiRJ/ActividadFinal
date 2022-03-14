package com.nttdata.actividadfinal.service;

import java.util.List;

import com.nttdata.actividadfinal.repository.entity.Usuario;

public interface UsuarioService {
	
	List<Usuario> listaPorRol(String rol);

	Usuario buscarPorUsername(String username);
}
