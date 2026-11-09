package co.edu.usco.pw.gestion_consultas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Medico (tabla independiente). Cada medico tiene un usuario para entrar al sistema.
 * @author Juan Mayor
 */
@Entity
@Table(name = "medicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Medico que atiende las consultas")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID del medico", example = "1")
	private Long id;

	@Column(nullable = false, length = 60)
	@Schema(description = "Nombre del medico", example = "Juan Mayor")
	private String nombre;

	@Column(nullable = false, length = 60)
	@Schema(description = "Especialidad", example = "Medicina general")
	private String especialidad;

	// si se borra el medico tambien se borra su usuario
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id")
	@Schema(description = "Usuario con el que el medico inicia sesion")
	private Usuario usuario;
}
