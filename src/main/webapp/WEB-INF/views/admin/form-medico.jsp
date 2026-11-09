<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Médico</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/encabezado.jsp"%>
	<div class="container" style="max-width: 600px">
		<div class="tarjeta">
			<c:choose>
				<c:when test="${medico.id == null}">
					<h4 class="titulo">Nuevo médico</h4>
				</c:when>
				<c:otherwise>
					<h4 class="titulo">Editar médico</h4>
				</c:otherwise>
			</c:choose>
			<hr>

			<c:if test="${error != null}">
				<div class="alert alert-danger">${error}</div>
			</c:if>

			<form action="/admin/medicos/guardar" method="post" onsubmit="return validarMedico()">
				<input type="hidden" name="id" value="${medico.id}">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

				<div class="mb-3">
					<label class="form-label">Nombre</label>
					<input type="text" name="nombre" id="nombre" class="form-control" maxlength="60" value="${medico.nombre}">
				</div>
				<div class="mb-3">
					<label class="form-label">Especialidad</label>
					<input type="text" name="especialidad" id="especialidad" class="form-control" maxlength="60"
						value="${medico.especialidad}">
				</div>
				<div class="row">
					<div class="col-md-6 mb-3">
						<label class="form-label">Usuario</label>
						<input type="text" name="usuario.username" id="username" class="form-control" maxlength="50"
							value="${medico.usuario.username}">
					</div>
					<div class="col-md-6 mb-3">
						<label class="form-label">Contraseña</label>
						<input type="password" name="usuario.password" id="clave" class="form-control">
						<c:if test="${medico.id != null}">
							<small class="text-muted">Vacía para no cambiarla</small>
						</c:if>
					</div>
				</div>
				<input type="hidden" id="esNuevo" value="${medico.id == null}">

				<button type="submit" class="btn btn-verde mt-2">Guardar</button>
				<a href="/admin/medicos" class="btn btn-outline-secondary mt-2">Cancelar</a>
			</form>
		</div>
	</div>
	<script src="/js/validaciones.js"></script>
</body>
</html>
