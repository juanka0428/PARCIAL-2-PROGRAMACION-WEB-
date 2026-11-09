package co.edu.usco.pw.gestion_consultas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.edu.usco.pw.gestion_consultas.model.Consulta;
import co.edu.usco.pw.gestion_consultas.service.ConsultaService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;

/**
 * el paciente ve sus citas y solo puede cambiar el horario de las suyas
 */
@Controller
@RequestMapping("/paciente")
@Hidden
public class PacienteController {

	@Autowired
	private ConsultaService consultaService;

	@GetMapping("/consultas")
	public String misCitas(Authentication auth, Model model) {
		model.addAttribute("consultas", consultaService.consultasDelPaciente(auth.getName()));
		return "paciente/consultas";
	}

	@GetMapping("/consultas/horario/{id}")
	public String editarHorario(@PathVariable Long id, Authentication auth, Model model,
			HttpServletResponse response) {
		Consulta c = consultaService.buscarPorId(id);

		// si la cita no es de el se muestra la pagina de acceso denegado
		if (!consultaService.esDelPaciente(c, auth.getName())) {
			response.setStatus(403);
			return "acceso-denegado";
		}
		model.addAttribute("consulta", c);
		return "paciente/form-horario";
	}

	@PostMapping("/consultas/horario/{id}")
	public String guardarHorario(@PathVariable Long id, @ModelAttribute Consulta datos, Authentication auth,
			Model model, HttpServletResponse response, RedirectAttributes ra) {
		Consulta c = consultaService.buscarPorId(id);

		if (!consultaService.esDelPaciente(c, auth.getName())) {
			response.setStatus(403);
			return "acceso-denegado";
		}

		String error = consultaService.validarHorario(datos);
		if (error != null) {
			model.addAttribute("error", error);
			model.addAttribute("consulta", c);
			return "paciente/form-horario";
		}

		consultaService.cambiarHorario(c, datos);
		ra.addFlashAttribute("mensaje", "Horario actualizado");
		return "redirect:/paciente/consultas";
	}
}
