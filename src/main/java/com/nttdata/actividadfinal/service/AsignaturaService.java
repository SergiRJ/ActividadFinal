package com.nttdata.actividadfinal.service;

import java.util.List;

import com.nttdata.actividadfinal.repository.entity.Asignatura;

public interface AsignaturaService {

	public List<Asignatura> listar();
	public Asignatura getById(Integer id);
	public Asignatura inserta(Asignatura asignatura);
	public Asignatura modificar(Asignatura asignatura);
	public void eliminar(Integer id);
	public void eliminarTodos();
	
}
