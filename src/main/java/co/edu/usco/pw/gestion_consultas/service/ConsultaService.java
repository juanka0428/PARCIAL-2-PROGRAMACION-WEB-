package co.edu.usco.pw.gestion_consultas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import co.edu.usco.pw.gestion_consultas.model.Consulta;
import co.edu.usco.pw.gestion_consultas.model.Medico;
import co.edu.usco.pw.gestion_consultas.model.Usuario;
import co.edu.usco.pw.gestion_consultas.repository.ConsultaRepository;
import co.edu.usco.pw.gestion_consultas.repository.MedicoRepository;
import co.edu.usco.pw.gestion_consultas.repository.UsuarioRepository;

@Service
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Consulta> listar() {
		return consultaRepository.findAll(Sort.by("id"));
	}

	public Consulta buscarPorId(Long id) {
		return consultaRepository.findById(id).orElse(null);
	}

	/**
	 * valida los datos del formulario de consulta
	 * @return mensaje de error o null si esta bien
	 */
	public String validar(Consulta c) {
		if (c.getNombrePaciente() == null || c.getNombrePaciente().trim().isEmpty()) {
			return "El nombre del paciente es obligatorio";
		}
		if (c.getNombrePaciente().length() > 40) {
			return "El nombre del paciente no puede superar los 40 caracteres";
		}
		// solo letras, numeros y espacios
		if (!c.getNombrePaciente().matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+")) {
			return "El nombre del paciente solo puede tener letras y numeros";
		}
		if (c.getMotivo() == null || c.getMotivo().trim().isEmpty()) {
			return "El motivo de la consulta es obligatorio";
		}
		if (c.getMotivo().length() > 100) {
			return "El motivo no puede superar los 100 caracteres";
		}
		if (c.getNumeroConsultorio() == null || c.getNumeroConsultorio() <= 0) {
			return "El numero de consultorio debe ser un numero mayor a 0";
		}
		String error = validarHorario(c);
		if (error != null) {
			return error;
		}
		if (c.getMedico() == null || c.getMedico().getId() == null) {
			return "Debe seleccionar un medico";
		}
		if (!medicoRepository.existsById(c.getMedico().getId())) {
			return "El medico seleccionado no existe";
		}
		return null;
	}

	public String validarHorario(Consulta c) {
		if (c.getHoraInicio() == null || c.getHoraFin() == null) {
			return "La hora de inicio y la hora de finalizacion son obligatorias";
		}
		if (!c.getHoraFin().isAfter(c.getHoraInicio())) {
			return "La hora de finalizacion debe ser despues de la hora de inicio";
		}
		return null;
	}

	public Consulta guardar(Consulta c) {
		// se trae el medico completo de la bd
		Medico m = medicoRepository.findById(c.getMedico().getId()).orElse(null);
		c.setMedico(m);
		c.setNombrePaciente(c.getNombrePaciente().trim());
		return consultaRepository.save(c);
	}

	public void eliminar(Long id) {
		consultaRepository.deleteById(id);
	}

	/** consultas asignadas al medico que inicio sesion */
	public List<Consulta> consultasDelMedico(String username) {
		Medico m = medicoRepository.findByUsuarioUsername(username);
		if (m == null) {
			return new ArrayList<>();
		}
		return consultaRepository.findByMedicoIdOrderByHoraInicio(m.getId());
	}

	/** consultas del paciente que inicio sesion (se buscan por su nombre) */
	public List<Consulta> consultasDelPaciente(String username) {
		Usuario u = usuarioRepository.findByUsername(username).orElse(null);
		if (u == null) {
			return new ArrayList<>();
		}
		return consultaRepository.buscarPorPaciente(u.getNombre());
	}

	/** revisa si la consulta es del paciente que inicio sesion */
	public boolean esDelPaciente(Consulta c, String username) {
		Usuario u = usuarioRepository.findByUsername(username).orElse(null);
		if (u == null || c == null) {
			return false;
		}
		return c.getNombrePaciente().equalsIgnoreCase(u.getNombre());
	}

	/** el paciente solo cambia las horas */
	public Consulta cambiarHorario(Consulta c, Consulta nuevas) {
		c.setHoraInicio(nuevas.getHoraInicio());
		c.setHoraFin(nuevas.getHoraFin());
		return consultaRepository.save(c);
	}
}
