package com.nttdata.actividadfinal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.actividadfinal.repository.AsignaturaRepoJPA;
import com.nttdata.actividadfinal.repository.entity.Asignatura;
import com.nttdata.actividadfinal.service.AsignaturaService;

@Service
public class AsignaturaServiceImpl implements AsignaturaService{

	@Autowired
	AsignaturaRepoJPA asignaturaJPA;

	@Override
	public List<Asignatura> listar() {
		return asignaturaJPA.findAll();
	}

	@Override
	public Asignatura getById(Integer id) {
		return asignaturaJPA.findById(id).orElse(null);
	}

	@Override
	public Asignatura inserta(Asignatura asignatura) {
		return asignaturaJPA.save(asignatura);
	}

	@Override
	public Asignatura modificar(Asignatura asignatura) {
		return asignaturaJPA.save(asignatura);
	}

	@Override
	public void eliminar(Integer id) {
		asignaturaJPA.deleteById(id);
		
	}
	
	@Override
	public void eliminarTodos() {
		asignaturaJPA.deleteAll();
	}
	
}
