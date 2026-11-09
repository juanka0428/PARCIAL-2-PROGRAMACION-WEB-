<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Cambiar horario</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/encabezado.jsp"%>
	<div class="container" style="max-width: 550px">
		<div class="tarjeta">
			<h4 class="titulo">Cambiar horario de la cita</h4>
			<hr>

			<c:if test="${error != null}">
				<div class="alert alert-danger">${error}</div>
			</c:if>

			<ul class="list-group mb-3">
				<li class="list-group-item"><b>Motivo:</b> ${consulta.motivo}</li>
				<li class="list-group-item"><b>Consultorio:</b> <span class="consultorio">${consulta.numeroConsultorio}</span></li>
				<li class="list-group-item"><b>Médico:</b> ${consulta.medico.nombre}</li>
			</ul>

			<form action="/paciente/consultas/horario/${consulta.id}" method="post" onsubmit="return validarHorario()">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<div class="row">
					<div class="col mb-3">
						<label class="form-label">Hora de inicio</label>
						<input type="time" name="horaInicio" id="horaInicio" class="form-control" value="${consulta.horaInicio}">
					</div>
					<div class="col mb-3">
						<label class="form-label">Hora de finalización</label>
						<input type="time" name="horaFin" id="horaFin" class="form-control" value="${consulta.horaFin}">
					</div>
				</div>
				<button type="submit" class="btn btn-verde">Guardar horario</button>
				<a href="/paciente/consultas" class="btn btn-outline-secondary">Cancelar</a>
			</form>
		</div>
	</div>
	<script src="/js/validaciones.js"></script>
</body>
</html>
