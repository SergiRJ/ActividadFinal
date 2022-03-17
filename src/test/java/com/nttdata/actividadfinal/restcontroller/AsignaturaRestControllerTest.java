package com.nttdata.actividadfinal.restcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.actividadfinal.repository.AsignaturaRepoJPA;
import com.nttdata.actividadfinal.repository.entity.Asignatura;
import com.nttdata.actividadfinal.service.AsignaturaService;



@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class AsignaturaRestControllerTest {
	Asignatura a1, a2;

	@Autowired
	AsignaturaRepoJPA repo;
	
	@Autowired
	AsignaturaService service;
	
	@Autowired
	AsignaturaRestController restcontroller;
	
	@Mock
	AsignaturaService serviceMock;
	@InjectMocks
	AsignaturaRestController restcontrollerMock;

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
	void testListarAsignaturas() {
		//Given
		assertEquals(2, service.listar().size(), "2 asignaturas en BBDD");

		//When
		ResponseEntity<List<Asignatura>> lista = restcontroller.listarAsignaturas();
		//Then
		assertEquals(2,lista.getBody().size(),"Hay 2 asignaturas en la lista sacada de BBDD");
	}

	@Test
	void testDevuelveAsignatura() {
		//Given
		assertEquals(2, service.listar().size(), "2 asignaturas en BBDD");
		//When
		ResponseEntity<Asignatura> re = restcontroller.devuelveAsignatura(a1.getId());
		//Then
		assertEquals(a1, re.getBody(),"Misma asignatura, conseguida por ID");
		assertNotEquals(a2,re.getBody(),"Distinta asignatura conseguida por ID");
		assertThat ( re.getStatusCodeValue() ).isEqualTo(200);
		assertThat ( re.getStatusCode()).isEqualTo( HttpStatus.OK);
	}
	
	@Test
	void testInsertarAsignatura() {
		//Given
		assertEquals(2, service.listar().size(), "2 asignaturas en BBDD");
		//When
		Asignatura a3 = new Asignatura();
		a3.setNombre("Nombre3");
		a3.setCurso(3);
		a3.setDescripcion("Desc");
		ResponseEntity<Asignatura> re = restcontroller.insertarAsignatura(a3);
		//Then
		assertAll (
				() -> assertEquals (HttpStatus.CREATED, re.getStatusCode(), "Código 201 creado OK"),
				() -> assertEquals (3, service.listar().size(), "Hay 3 asignaturas en BBDD en BBDD")
				);
	}
	
	@Test 
	void testInsertarAsignaturaNombreIsNull(){
		//When
		Asignatura a3 = new Asignatura();
		ResponseEntity<Asignatura> re = restcontroller.insertarAsignatura(a3);
		//THen
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Error 406 nombre is null");
	}
	
	@Test 
	void testInsertarAsignaturaIdIsNotNull(){
		//When
		Asignatura a3 = new Asignatura();
		a3.setId(55);
		ResponseEntity<Asignatura> re = restcontroller.insertarAsignatura(a3);
		//THen
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Error 406 id is not null");
	}
	
	void testInsertarAsignaturaException() throws Exception{
		//Given
		when(serviceMock.inserta(a1)).thenThrow(new Exception());
		//When
		ResponseEntity<Asignatura> re = restcontrollerMock.insertarAsignatura(a1);
		//Then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Excepción");
	}
	
	@Test
	void testModificarAsignatura() {
		//Given
		assertEquals(2, service.listar().size(), "2 asignaturas en BBDD");
		//When
		String nNombre = "NuevoNombre";
		a2.setNombre(nNombre);
		restcontroller.modificarAsignatura(a2);
		//Then
		assertEquals(2,service.listar().size(),"Sigue habiendo 2 asignaturas en BBDD");
		assertEquals("NuevoNombre", service.getById(a2.getId()).getNombre(),"Modificado Nombre");
	}

	
	@Test
	void testBorrarAsignatura() {
		//Given
		assertEquals(2, service.listar().size(), "2 asignaturas en BBDD");
		//When
		restcontroller.borrarAsignatura(a1.getId());
		//Then
		assertEquals(1,service.listar().size(),"1 Asignatura en BBDD");
	}

	@Test
	void testBorrarTodasAsignaturas() {
		//Given
		assertEquals(2, service.listar().size(), "2 asignaturas en BBDD");	
		//when
		restcontroller.borrarTodasAsignaturas();
		//then
		assertEquals(true,service.listar().isEmpty(),"No hay asignaturas en BBDD");
	}

}
