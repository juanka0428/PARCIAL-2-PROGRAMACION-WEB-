package co.edu.usco.pw.gestion_consultas.model;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Consulta medica (asignacion de consultorio).
 * Muchas consultas pertenecen a un medico (@ManyToOne).
 * @author Juan Mayor
 */
@Entity
@Table(name = "consultas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Consulta medica con su consultorio y horario")
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID de la consulta", example = "1")
	private Long id;

	@Column(name = "nombre_paciente", nullable = false, length = 40)
	@Schema(description = "Nombre del paciente (alfanumerico, maximo 40 caracteres). Debe ser igual al nombre del usuario paciente", example = "Sofia Herrera", maxLength = 40)
	private String nombrePaciente;

	@Column(nullable = false, length = 100)
	@Schema(description = "Motivo de la consulta (maximo 100 caracteres)", example = "Dolor de cabeza frecuente", maxLength = 100)
	private String motivo;

	@Column(name = "numero_consultorio", nullable = false)
	@Schema(description = "Numero del consultorio", example = "101")
	private Integer numeroConsultorio;

	@Column(name = "hora_inicio", nullable = false)
	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	@Schema(description = "Hora de inicio (HH:mm)", example = "08:00", type = "string")
	private LocalTime horaInicio;

	@Column(name = "hora_fin", nullable = false)
	@DateTimeFormat(pattern = "HH:mm")
	@JsonFormat(pattern = "HH:mm")
	@Schema(description = "Hora de finalizacion (HH:mm)", example = "08:30", type = "string")
	private LocalTime horaFin;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "medico_id", nullable = false)
	@Schema(description = "Medico asignado (en POST/PUT basta con enviar el id)")
	private Medico medico;
}
