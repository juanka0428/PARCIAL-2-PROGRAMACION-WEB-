package co.edu.usco.pw.gestion_consultas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import co.edu.usco.pw.gestion_consultas.model.Medico;
import co.edu.usco.pw.gestion_consultas.model.Usuario;
import co.edu.usco.pw.gestion_consultas.repository.ConsultaRepository;
import co.edu.usco.pw.gestion_consultas.repository.MedicoRepository;
import co.edu.usco.pw.gestion_consultas.repository.UsuarioRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public List<Medico> listar() {
		return medicoRepository.findAll(Sort.by("id"));
	}

	public Medico buscarPorId(Long id) {
		return medicoRepository.findById(id).orElse(null);
	}

	/**
	 * revisa los datos del medico antes de guardar
	 * @param medico datos que llegan del formulario o del json
	 * @param id id del medico si se esta editando (null si es nuevo)
	 * @return el mensaje de error o null si todo esta bien
	 */
	public String validar(Medico medico, Long id) {
		if (medico.getNombre() == null || medico.getNombre().trim().isEmpty()) {
			return "El nombre del medico es obligatorio";
		}
		if (medico.getNombre().length() > 60) {
			return "El nombre no puede tener mas de 60 caracteres";
		}
		if (medico.getEspecialidad() == null || medico.getEspecialidad().trim().isEmpty()) {
			return "La especialidad es obligatoria";
		}
		if (medico.getUsuario() == null || medico.getUsuario().getUsername() == null
				|| medico.getUsuario().getUsername().trim().isEmpty()) {
			return "El usuario es obligatorio";
		}

		String username = medico.getUsuario().getUsername().trim();
		Usuario otro = usuarioRepository.findByUsername(username).orElse(null);

		if (id == null) {
			// medico nuevo
			if (otro != null) {
				return "El usuario " + username + " ya existe";
			}
			if (medico.getUsuario().getPassword() == null || medico.getUsuario().getPassword().length() < 4) {
				return "La contraseña debe tener minimo 4 caracteres";
			}
		} else {
			// editando: el username no puede ser de otra persona
			Medico actual = buscarPorId(id);
			if (otro != null && actual != null && !otro.getId().equals(actual.getUsuario().getId())) {
				return "El usuario " + username + " ya existe";
			}
		}
		return null;
	}

	public Medico crear(Medico medico) {
		Usuario usuario = medico.getUsuario();
		usuario.setId(null);
		usuario.setUsername(usuario.getUsername().trim());
		usuario.setNombre(medico.getNombre());
		usuario.setRol("MEDICO");
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

		medico.setId(null);
		return medicoRepository.save(medico);
	}

	public Medico actualizar(Long id, Medico datos) {
		Medico medico = buscarPorId(id);
		medico.setNombre(datos.getNombre());
		medico.setEspecialidad(datos.getEspecialidad());

		Usuario usuario = medico.getUsuario();
		usuario.setNombre(datos.getNombre());
		usuario.setUsername(datos.getUsuario().getUsername().trim());
		// si la clave viene vacia se deja la que tenia
		String clave = datos.getUsuario().getPassword();
		if (clave != null && !clave.isEmpty()) {
			usuario.setPassword(passwordEncoder.encode(clave));
		}
		return medicoRepository.save(medico);
	}

	/**
	 * elimina el medico (y su usuario)
	 * @return false si no se puede porque tiene consultas
	 */
	public boolean eliminar(Long id) {
		if (!consultaRepository.findByMedicoIdOrderByHoraInicio(id).isEmpty()) {
			return false;
		}
		medicoRepository.deleteById(id);
		return true;
	}
}
