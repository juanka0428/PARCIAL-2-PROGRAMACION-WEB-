package co.edu.usco.pw.gestion_consultas.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Hidden;

@Controller
@Hidden
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// despues del login se manda a cada uno a su vista segun el rol
	@GetMapping({ "/", "/inicio" })
	public String inicio(Authentication auth) {
		String rol = auth.getAuthorities().iterator().next().getAuthority();

		if (rol.equals("ROLE_ADMIN")) {
			return "redirect:/admin/consultas";
		}
		if (rol.equals("ROLE_MEDICO")) {
			return "redirect:/medico/consultas";
		}
		if (rol.equals("ROLE_PACIENTE")) {
			return "redirect:/paciente/consultas";
		}
		return "redirect:/acceso-denegado";
	}

	@GetMapping("/acceso-denegado")
	public String accesoDenegado() {
		return "acceso-denegado";
	}
}
