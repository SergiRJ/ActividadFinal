package com.nttdata.actividadfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nttdata.actividadfinal.repository.entity.Usuario;
import com.nttdata.actividadfinal.service.AsignaturaService;

@Controller
public class WebController {

	@Autowired
	AsignaturaService asignaturaService;
	
	@GetMapping("/")
	public String index(Model model) {
		Usuario u = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("usuario", u);
		return "index";
	}
	
	@GetMapping("/error")
	public String error_page() {
		return "error";
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/listaAsignaturas")
	public String listaAsignaturas(Model model) {
		model.addAttribute("lista", asignaturaService.listar());
		return "listadoAsignaturas";
	}

}
