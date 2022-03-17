package com.nttdata.actividadfinal.repository.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AsignaturaTest {

	@Test
	void test() {
		Asignatura a1= new Asignatura();
		a1.setId(1);
		assertEquals(1,a1.getId(), "Mismo id");
		
		String nombre = "NombrePrueba";
		a1.setNombre("NombrePrueba");
		assertEquals(nombre,a1.getNombre(),"Mismo nombre");
		
		String descripcion="CursoPrueba";
		a1.setDescripcion(descripcion);
		assertEquals(descripcion,a1.getDescripcion(),"Misma Descripción");
		
		Integer curso = 1;
		a1.setCurso(curso);
		assertEquals(curso,a1.getCurso(),"Mismo Curso");
		
		Asignatura a2= new Asignatura();
		a2.setId(1);
		a2.setNombre(nombre);
		a2.setCurso(curso);
		a2.setDescripcion(descripcion);
		
		assertEquals(a1,a2,"Misma Asignatura");
		
		assertEquals(a1.hashCode(),a2.hashCode(),"Mismo Hash");
		
		assertEquals(a1,a1,"Mismo Objeto");
		
		assertNotEquals(a1,nombre, "Distinto Objeto");
		
		a1.setId(null);
		assertNull(a1.getId(),"id de a1 es null");
		assertNotEquals(a1,a2,"Id de a1 es null, el de a2 no");
		
				
	
	}

}
