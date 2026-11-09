package co.edu.usco.pw.gestion_consultas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.usco.pw.gestion_consultas.model.Usuario;
import co.edu.usco.pw.gestion_consultas.repository.UsuarioRepository;

/**
 * Carga los usuarios desde la base de datos para Spring Security.
 * @author Juan Mayor
 */
@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> u = usuarioRepository.findByUsername(username);
		if (u.isEmpty()) {
			throw new UsernameNotFoundException("No existe el usuario " + username);
		}
		Usuario usuario = u.get();
		// roles() le agrega el prefijo ROLE_ solo
		return User.withUsername(usuario.getUsername()).password(usuario.getPassword()).roles(usuario.getRol())
				.build();
	}

	public Usuario buscarPorUsername(String username) {
		return usuarioRepository.findByUsername(username).orElse(null);
	}

	public boolean existeUsername(String username) {
		return usuarioRepository.existsByUsername(username);
	}
}
