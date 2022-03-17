package com.nttdata.actividadfinal.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.actividadfinal.repository.AsignaturaRepoJPA;
import com.nttdata.actividadfinal.repository.entity.Asignatura;
import com.nttdata.actividadfinal.service.AsignaturaService;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest	
class AsignaturaServiceImplTest {

	Asignatura a1, a2;

	@Autowired
	AsignaturaRepoJPA repo;
	
	@Autowired
	AsignaturaService service;
	
	@BeforeEach
	void setUp() throws Exception {
		repo.deleteAll();
		
	    a1 = new Asignatura();
		a1.setNombre("Nombre");
		a1.setCurso(1);
		a1.setDescripcion("Descripcion");
		a1=repo.save(a1);
		
		a2 = new Asignatura();
		a2.setNombre("Nombre2");
		a2.setCurso(2);
		a2.setDescripcion("Descripcion2");
		a2=repo.save(a2);
		
	}

	@AfterEach
	void tearDown() throws Exception {
		repo.deleteAll();
	}

	
	@Test
	void testListar() {
		//Given
			assertEquals(2, service.listar().size(), "2 asignaturas en BBDD");

		//When
			List<Asignatura> lista = service.listar();
		//Then
			assertEquals(2,lista.size(),"Hay 2 asignaturas en la lista sacada de BBDD");
	}

	@Test
	void testGetById() {
		//Given
		assertEquals(2, service.listar().size(), "2 asignaturas en BBDD");
		//Then
		Asignatura a = service.getById(a1.getId());
		//Then
		assertEquals(a1, a,"Misma asignatura, conseguida por ID");
		assertNotEquals(a2,a,"Distinta asignatura conseguida por ID");
	}


	@Test
	void testInserta() {
		//Given
		assertEquals(2,service.listar().size(),"Hay 2 Asignaturas en BBDD");

		//When
		Asignatura a3 = new Asignatura();
		a3.setNombre("Nombre2");
		a3.setCurso(2);
		a3.setDescripcion("Descripcion2");
		a3=repo.save(a3);
		
		//Then 
		assertEquals(3,service.listar().size(), "Hay 3 asignaturas en BBDD");
	}
	
	@Test
	void testModificar() {
		//Given
		assertEquals(2, service.listar().size(),"Hay 2 Asignaturas en BBDD");
		//When
		String nNombre="NombreNuevo";
		a2.setNombre(nNombre);
		service.modificar(a2);
		//Then
		assertEquals(2, service.listar().size(),"Hay 2 Asignaturas en BBDD");
		assertEquals(nNombre, service.getById(a2.getId()).getNombre(),"Nombre Modificado");

	}
	
	@Test
	void testEliminar() {
		//Given
		assertEquals(2, service.listar().size(),"Hay 2 Asignaturas en BBDD");
		//When
		service.eliminar(a1.getId());
		//Then
		assertEquals(1,service.listar().size(),"Solo hay una asignatura en BBDD");
		}
	
	@Test
	void testEliminarTodos() {
		//Given
		assertEquals(2, service.listar().size(),"Hay 2 Asignaturas en BBDD");
		//When
		service.eliminarTodos();
		//Then
		//Quiero probar si ambas formas son correctas
		assertEquals(true,service.listar().isEmpty(),"No hay asignaturas en BBDD"); 
		assertEquals(0,service.listar().size(),"No hay asignaturas en BBDD");

	}


}
