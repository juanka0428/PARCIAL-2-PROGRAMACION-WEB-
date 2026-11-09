package co.edu.usco.pw.gestion_consultas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.pw.gestion_consultas.model.Consulta;
import co.edu.usco.pw.gestion_consultas.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "Consultas medicas. ADMIN las administra, MEDICO solo ve las suyas y PACIENTE ve sus citas y cambia el horario")
public class ConsultaRestController {

	@Autowired
	private ConsultaService consultaService;

	@Operation(summary = "Listar todas las consultas", description = "Devuelve todas las consultas registradas. Solo ADMIN.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK, lista de consultas"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMIN", content = @Content) })
	@GetMapping
	public List<Consulta> listar() {
		return consultaService.listar();
	}

	@Operation(summary = "Mis consultas (MEDICO o PACIENTE)", description = "Si el usuario es MEDICO devuelve solo las consultas asignadas a el. "
			+ "Si es PACIENTE devuelve solo sus citas (se buscan por el nombre del paciente). El ADMIN recibe 403.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK, consultas del usuario"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es MEDICO ni PACIENTE", content = @Content) })
	@GetMapping("/mis-consultas")
	public List<Consulta> misConsultas(Authentication auth) {
		String rol = auth.getAuthorities().iterator().next().getAuthority();
		if (rol.equals("ROLE_MEDICO")) {
			return consultaService.consultasDelMedico(auth.getName());
		}
		return consultaService.consultasDelPaciente(auth.getName());
	}

	@Operation(summary = "Obtener una consulta", description = "Busca una consulta por su ID. Solo ADMIN.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK, consulta encontrada"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMIN", content = @Content),
			@ApiResponse(responseCode = "404", description = "No existe la consulta", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<Consulta> obtener(@Parameter(description = "ID de la consulta", example = "1") @PathVariable Long id) {
		Consulta c = consultaService.buscarPorId(id);
		if (c == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(c);
	}

	@Operation(summary = "Registrar consulta (asignar consultorio)", description = "Crea una consulta. Validaciones: nombre del paciente alfanumerico "
			+ "de maximo 40 caracteres, motivo de maximo 100, consultorio numerico, hora fin mayor a hora inicio y un medico existente "
			+ "(enviar medico: { id: 1 }). Solo ADMIN.")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Created, consulta registrada"),
			@ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMIN", content = @Content) })
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Consulta consulta) {
		String error = consultaService.validar(consulta);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		consulta.setId(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.guardar(consulta));
	}

	@Operation(summary = "Editar consulta", description = "Actualiza todos los datos de una consulta. Mismas validaciones que al crear. Solo ADMIN.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK, consulta actualizada"),
			@ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMIN", content = @Content),
			@ApiResponse(responseCode = "404", description = "No existe la consulta", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@Parameter(description = "ID de la consulta", example = "1") @PathVariable Long id,
			@RequestBody Consulta datos) {
		if (consultaService.buscarPorId(id) == null) {
			return ResponseEntity.notFound().build();
		}
		String error = consultaService.validar(datos);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		datos.setId(id);
		return ResponseEntity.ok(consultaService.guardar(datos));
	}

	@Operation(summary = "Cambiar horario de una cita (PACIENTE)", description = "El paciente cambia la hora de inicio y fin de una cita. "
			+ "Solo puede cambiar las citas que tiene asignadas, si intenta cambiar la de otro paciente responde 403. "
			+ "Solo se tienen en cuenta horaInicio y horaFin del json.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK, horario actualizado"),
			@ApiResponse(responseCode = "400", description = "Horario invalido", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, no es PACIENTE o la cita no es suya", content = @Content),
			@ApiResponse(responseCode = "404", description = "No existe la cita", content = @Content) })
	@PutMapping("/{id}/horario")
	public ResponseEntity<?> cambiarHorario(@Parameter(description = "ID de la cita", example = "1") @PathVariable Long id,
			@RequestBody Consulta datos,
			Authentication auth) {
		Consulta c = consultaService.buscarPorId(id);
		if (c == null) {
			return ResponseEntity.notFound().build();
		}
		if (!consultaService.esDelPaciente(c, auth.getName())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Esta cita no le pertenece");
		}
		String error = consultaService.validarHorario(datos);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		return ResponseEntity.ok(consultaService.cambiarHorario(c, datos));
	}

	@Operation(summary = "Eliminar consulta", description = "Elimina una consulta por su ID. Solo ADMIN.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK, consulta eliminada"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no envio credenciales o son incorrectas", content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMIN", content = @Content),
			@ApiResponse(responseCode = "404", description = "No existe la consulta", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(@Parameter(description = "ID de la consulta", example = "1") @PathVariable Long id) {
		if (consultaService.buscarPorId(id) == null) {
			return ResponseEntity.notFound().build();
		}
		consultaService.eliminar(id);
		return ResponseEntity.ok("Consulta eliminada");
	}
}
