<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Registrar consultorio</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<div class="pagina">
		<%@ include file="/WEB-INF/views/encabezado.jsp"%>
		<div class="contenido">
			<c:choose>
				<c:when test="${consulta.id == null}">
					<div><h2>Registrar consultorio</h2></div>
				</c:when>
				<c:otherwise>
					<div><h2>Editar consulta #${consulta.id}</h2></div>
				</c:otherwise>
			</c:choose>

			<c:if test="${error != null}">
				<div class="aviso-error">${error}</div>
			</c:if>

			<form action="/admin/consultas/guardar" method="post" onsubmit="return validarConsulta()" style="max-width: 650px">
				<input type="hidden" name="id" value="${consulta.id}">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

				<fieldset>
					<legend>Datos de la consulta</legend>

					<label for="nombrePaciente">Nombre del paciente</label>
					<input type="text" name="nombrePaciente" id="nombrePaciente" class="form-control" maxlength="40"
						value="${consulta.nombrePaciente}">
					<span class="ayuda">Máximo 40 caracteres</span>
					<br>

					<label for="motivo">Motivo de la consulta</label>
					<textarea name="motivo" id="motivo" class="form-control" maxlength="100" rows="2">${consulta.motivo}</textarea>
					<span class="ayuda">Máximo 100 caracteres</span>

					<div class="row">
						<div class="col-md-4">
							<label for="numeroConsultorio">Consultorio</label>
							<input type="number" name="numeroConsultorio" id="numeroConsultorio" class="form-control" min="1"
								value="${consulta.numeroConsultorio}">
						</div>
						<div class="col-md-4">
							<label for="horaInicio">Hora inicio</label>
							<input type="time" name="horaInicio" id="horaInicio" class="form-control" value="${consulta.horaInicio}">
						</div>
						<div class="col-md-4">
							<label for="horaFin">Hora fin</label>
							<input type="time" name="horaFin" id="horaFin" class="form-control" value="${consulta.horaFin}">
						</div>
					</div>

					<label for="medico">Médico</label>
					<select name="medico.id" id="medico" class="form-select">
						<option value="">-- Seleccione --</option>
						<c:forEach var="m" items="${medicos}">
							<option value="${m.id}" ${consulta.medico.id == m.id ? 'selected' : ''}>${m.nombre} - ${m.especialidad}</option>
						</c:forEach>
					</select>
				</fieldset>

				<p style="margin-top: 15px">
					<button type="submit" class="boton">Guardar</button>
					<a href="/admin/consultas" class="boton boton-gris">Cancelar</a>
				</p>
			</form>
			<%@ include file="/WEB-INF/views/pie.jsp"%>
		</div>
	</div>
	<script src="/js/validaciones.js"></script>
</body>
</html>
