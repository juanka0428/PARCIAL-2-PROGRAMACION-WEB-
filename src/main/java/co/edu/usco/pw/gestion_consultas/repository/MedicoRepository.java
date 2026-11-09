package co.edu.usco.pw.gestion_consultas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.usco.pw.gestion_consultas.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

	/** busca el medico por el username de su usuario */
	Medico findByUsuarioUsername(String username);
}
