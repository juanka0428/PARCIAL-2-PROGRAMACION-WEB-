package co.edu.usco.pw.gestion_consultas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.usco.pw.gestion_consultas.model.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	/** query automatico: consultas de un medico */
	List<Consulta> findByMedicoIdOrderByHoraInicio(Long medicoId);

	/**
	 * query personalizado: consultas de un paciente por su nombre (sin importar mayusculas)
	 * @param nombre nombre del paciente
	 * @return lista de consultas
	 */
	@Query("SELECT c FROM Consulta c WHERE LOWER(c.nombrePaciente) = LOWER(:nombre) ORDER BY c.horaInicio")
	List<Consulta> buscarPorPaciente(@Param("nombre") String nombre);
}
