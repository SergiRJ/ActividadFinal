package com.nttdata.actividadfinal.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nttdata.actividadfinal.repository.entity.Usuario;

public interface UsuarioRepoJPA extends JpaRepository<Usuario,String>{

	List<Usuario> findByRolLike(String rol);
	
}
