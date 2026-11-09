package co.edu.usco.pw.gestion_consultas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
@Schema(description = "Usuario que inicia sesion en el sistema")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID del usuario", example = "1")
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	@Schema(description = "Nombre de usuario para el login", example = "juanmayor")
	private String username;

	// la clave solo se recibe, nunca se devuelve en el json
	@Column(nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Schema(description = "Contraseña (se guarda encriptada con BCrypt)", example = "juan2026")
	private String password;

	@Column(nullable = false, length = 60)
	@Schema(description = "Nombre completo", example = "Juan Mayor")
	private String nombre;

	// ADMIN, MEDICO o PACIENTE
	@Column(nullable = false, length = 20)
	@Schema(description = "Rol del usuario", example = "MEDICO", allowableValues = { "ADMIN", "MEDICO", "PACIENTE" })
	private String rol;
}
