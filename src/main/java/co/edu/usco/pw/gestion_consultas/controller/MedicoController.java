package co.edu.usco.pw.gestion_consultas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.edu.usco.pw.gestion_consultas.service.ConsultaService;
import io.swagger.v3.oas.annotations.Hidden;

/**
 * el medico solo puede ver sus consultas, no tiene acciones
 */
@Controller
@Hidden
public class MedicoController {

	@Autowired
	private ConsultaService consultaService;

	@GetMapping("/medico/consultas")
	public String misConsultas(Authentication auth, Model model) {
		model.addAttribute("consultas", consultaService.consultasDelMedico(auth.getName()));
		return "medico/consultas";
	}
}
