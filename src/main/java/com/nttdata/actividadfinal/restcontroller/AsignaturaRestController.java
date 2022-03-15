package com.nttdata.actividadfinal.restcontroller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.actividadfinal.repository.entity.Asignatura;
import com.nttdata.actividadfinal.service.AsignaturaService;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaRestController {

	@Autowired 
	AsignaturaService asignaturaService;
	
	@GetMapping
	@Cacheable(value="asignaturas")
	public ResponseEntity<List<Asignatura>> listarAsignaturas(){
		HttpHeaders headers = new HttpHeaders();
		List<Asignatura> lista = asignaturaService.listar();
		if(lista.isEmpty()) {
			headers.set("Message", "No se ha encontrado ninguna asignatura");
			return new ResponseEntity<>(null,headers,HttpStatus.NOT_FOUND);
		}
		headers.set("Message", "Asignaturas encontradas");
		return new ResponseEntity<>(lista,headers,HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Asignatura> devuelveAsignatura(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		Asignatura asig = asignaturaService.getById(id);
		if(asig==null) {
			headers.set("Message", "No se ha encontrado la asignatura con este ID");
			return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
		}
		headers.set("Message", "Asignatura encontrada");
		return new ResponseEntity<>(asig, headers, HttpStatus.OK);
	}
	
	@PostMapping
	@CacheEvict(value="asignaturas", allEntries=true)
	public ResponseEntity<Asignatura> insertarAsignatura(@RequestBody Asignatura asignatura){
		try {
			HttpHeaders headers = new HttpHeaders();
			if(asignatura.getId()!=null) {
				headers.set("Message", "Para dar de alta una asignatura, su ID debe llegar vacío");
				return new ResponseEntity<>(headers,HttpStatus.NOT_ACCEPTABLE);
			} else if (asignatura.getNombre()==null) { //En este ejemplo solo comprobaré que el nombre está incluido
				headers.set("Message", "NOMBRE no puede ser nulo");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			}
			Asignatura asig = asignaturaService.inserta(asignatura);
			URI newPath = new URI("/api/asignaturas/"+asig.getId());
			headers.setLocation(newPath);
			headers.set("Message", "Asignatura insertada correctamente con id: " + asig.getId());
			return new ResponseEntity<>(asig,headers,HttpStatus.CREATED);
			
		} catch (Exception ex){
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@PutMapping
	@CacheEvict(value="asignaturas", allEntries=true)
	public ResponseEntity<Asignatura> modificarAsignatura(@RequestBody Asignatura asig) {
		try {
			HttpHeaders headers = new HttpHeaders();
			Asignatura as = asignaturaService.modificar(asig);
			headers.set("Message", "Asignatura modificada correctamente");
			return new ResponseEntity<>(as,headers,HttpStatus.OK);

		} catch (Exception ex) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	

	@DeleteMapping(value="/{id}")
	@CacheEvict(value="asignaturas", allEntries=true)
    public ResponseEntity<Asignatura> borrarAsignatura(@PathVariable("id") Integer id) {
		try {
			HttpHeaders headers = new HttpHeaders();
			asignaturaService.eliminar(id);
			headers.set("Message","Asignatura eliminada correctamente");
			return new ResponseEntity<>(null,headers,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
	@DeleteMapping
	@CacheEvict(value="asignaturas", allEntries=true)
    public ResponseEntity<Asignatura> borrarTodasAsignaturas() {
		try {
			HttpHeaders headers = new HttpHeaders();
			asignaturaService.eliminarTodos();
			headers.set("Message","Todas las asignaturas eliminadas correctamente");
			return new ResponseEntity<>(null,headers,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
}


