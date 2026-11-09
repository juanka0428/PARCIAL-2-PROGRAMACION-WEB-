package co.edu.usco.pw.gestion_consultas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.edu.usco.pw.gestion_consultas.model.Consulta;
import co.edu.usco.pw.gestion_consultas.model.Medico;
import co.edu.usco.pw.gestion_consultas.model.Usuario;
import co.edu.usco.pw.gestion_consultas.service.ConsultaService;
import co.edu.usco.pw.gestion_consultas.service.MedicoService;
import io.swagger.v3.oas.annotations.Hidden;

/**
 * vistas del administrador: crud de medicos y de consultas (asignar consultorio)
 */
@Controller
@RequestMapping("/admin")
@Hidden
public class AdminController {

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private ConsultaService consultaService;

	// medicos

	@GetMapping("/medicos")
	public String listarMedicos(Model model) {
		model.addAttribute("medicos", medicoService.listar());
		return "admin/medicos";
	}

	@GetMapping("/medicos/nuevo")
	public String nuevoMedico(Model model) {
		Medico m = new Medico();
		m.setUsuario(new Usuario());
		model.addAttribute("medico", m);
		return "admin/form-medico";
	}

	@GetMapping("/medicos/editar/{id}")
	public String editarMedico(@PathVariable Long id, Model model, RedirectAttributes ra) {
		Medico m = medicoService.buscarPorId(id);
		if (m == null) {
			ra.addFlashAttribute("error", "El medico no existe");
			return "redirect:/admin/medicos";
		}
		model.addAttribute("medico", m);
		return "admin/form-medico";
	}

	@PostMapping("/medicos/guardar")
	public String guardarMedico(@ModelAttribute Medico medico, Model model, RedirectAttributes ra) {
		String error = medicoService.validar(medico, medico.getId());
		if (error != null) {
			model.addAttribute("error", error);
			model.addAttribute("medico", medico);
			return "admin/form-medico";
		}

		if (medico.getId() == null) {
			medicoService.crear(medico);
			ra.addFlashAttribute("mensaje", "Medico creado");
		} else {
			medicoService.actualizar(medico.getId(), medico);
			ra.addFlashAttribute("mensaje", "Medico actualizado");
		}
		return "redirect:/admin/medicos";
	}

	@PostMapping("/medicos/eliminar/{id}")
	public String eliminarMedico(@PathVariable Long id, RedirectAttributes ra) {
		boolean ok = medicoService.eliminar(id);
		if (ok) {
			ra.addFlashAttribute("mensaje", "Medico eliminado");
		} else {
			ra.addFlashAttribute("error", "No se puede eliminar, el medico tiene consultas asignadas");
		}
		return "redirect:/admin/medicos";
	}

	// consultas

	@GetMapping("/consultas")
	public String listarConsultas(Model model) {
		model.addAttribute("consultas", consultaService.listar());
		return "admin/consultas";
	}

	@GetMapping("/consultas/nueva")
	public String nuevaConsulta(Model model) {
		model.addAttribute("consulta", new Consulta());
		model.addAttribute("medicos", medicoService.listar());
		return "admin/form-consulta";
	}

	@GetMapping("/consultas/editar/{id}")
	public String editarConsulta(@PathVariable Long id, Model model, RedirectAttributes ra) {
		Consulta c = consultaService.buscarPorId(id);
		if (c == null) {
			ra.addFlashAttribute("error", "La consulta no existe");
			return "redirect:/admin/consultas";
		}
		model.addAttribute("consulta", c);
		model.addAttribute("medicos", medicoService.listar());
		return "admin/form-consulta";
	}

	@PostMapping("/consultas/guardar")
	public String guardarConsulta(@ModelAttribute Consulta consulta, Model model, RedirectAttributes ra) {
		String error = consultaService.validar(consulta);
		if (error != null) {
			model.addAttribute("error", error);
			model.addAttribute("consulta", consulta);
			model.addAttribute("medicos", medicoService.listar());
			return "admin/form-consulta";
		}
		consultaService.guardar(consulta);
		ra.addFlashAttribute("mensaje", "Consulta guardada");
		return "redirect:/admin/consultas";
	}

	@PostMapping("/consultas/eliminar/{id}")
	public String eliminarConsulta(@PathVariable Long id, RedirectAttributes ra) {
		consultaService.eliminar(id);
		ra.addFlashAttribute("mensaje", "Consulta eliminada");
		return "redirect:/admin/consultas";
	}
}
