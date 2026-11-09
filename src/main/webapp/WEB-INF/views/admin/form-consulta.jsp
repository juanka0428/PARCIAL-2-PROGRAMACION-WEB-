<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Registrar consultorio</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/encabezado.jsp"%>
	<div class="container" style="max-width: 650px">
		<div class="tarjeta">
			<c:choose>
				<c:when test="${consulta.id == null}">
					<h4 class="titulo">Registrar consultorio</h4>
				</c:when>
				<c:otherwise>
					<h4 class="titulo">Editar consulta</h4>
				</c:otherwise>
			</c:choose>
			<hr>

			<c:if test="${error != null}">
				<div class="alert alert-danger">${error}</div>
			</c:if>

			<form action="/admin/consultas/guardar" method="post" onsubmit="return validarConsulta()">
				<input type="hidden" name="id" value="${consulta.id}">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

				<div class="mb-3">
					<label class="form-label">Nombre del paciente</label>
					<input type="text" name="nombrePaciente" id="nombrePaciente" class="form-control" maxlength="40"
						value="${consulta.nombrePaciente}">
					<small class="text-muted">Máximo 40 caracteres</small>
				</div>
				<div class="mb-3">
					<label class="form-label">Motivo de la consulta</label>
					<textarea name="motivo" id="motivo" class="form-control" maxlength="100" rows="2">${consulta.motivo}</textarea>
					<small class="text-muted">Máximo 100 caracteres</small>
				</div>
				<div class="row">
					<div class="col-md-4 mb-3">
						<label class="form-label">Consultorio</label>
						<input type="number" name="numeroConsultorio" id="numeroConsultorio" class="form-control" min="1"
							value="${consulta.numeroConsultorio}">
					</div>
					<div class="col-md-4 mb-3">
						<label class="form-label">Hora inicio</label>
						<input type="time" name="horaInicio" id="horaInicio" class="form-control" value="${consulta.horaInicio}">
					</div>
					<div class="col-md-4 mb-3">
						<label class="form-label">Hora fin</label>
						<input type="time" name="horaFin" id="horaFin" class="form-control" value="${consulta.horaFin}">
					</div>
				</div>
				<div class="mb-4">
					<label class="form-label">Médico</label>
					<select name="medico.id" id="medico" class="form-select">
						<option value="">-- Seleccione --</option>
						<c:forEach var="m" items="${medicos}">
							<option value="${m.id}" ${consulta.medico.id == m.id ? 'selected' : ''}>${m.nombre} (${m.especialidad})</option>
						</c:forEach>
					</select>
				</div>

				<button type="submit" class="btn btn-verde">Guardar</button>
				<a href="/admin/consultas" class="btn btn-outline-secondary">Cancelar</a>
			</form>
		</div>
	</div>
	<script src="/js/validaciones.js"></script>
</body>
</html>
