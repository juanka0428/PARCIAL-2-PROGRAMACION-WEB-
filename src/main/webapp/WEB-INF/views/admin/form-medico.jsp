<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Médico</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<div class="pagina">
		<%@ include file="/WEB-INF/views/encabezado.jsp"%>
		<div class="contenido">
			<c:choose>
				<c:when test="${medico.id == null}">
					<div><h2>Nuevo médico</h2></div>
				</c:when>
				<c:otherwise>
					<div><h2>Editar médico</h2></div>
				</c:otherwise>
			</c:choose>

			<c:if test="${error != null}">
				<div class="aviso-error">${error}</div>
			</c:if>

			<form action="/admin/medicos/guardar" method="post" onsubmit="return validarMedico()" style="max-width: 600px">
				<input type="hidden" name="id" value="${medico.id}">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

				<fieldset>
					<legend>Datos del médico</legend>

					<label for="nombre">Nombre</label>
					<input type="text" name="nombre" id="nombre" class="form-control" maxlength="60" value="${medico.nombre}">

					<label for="especialidad">Especialidad</label>
					<input type="text" name="especialidad" id="especialidad" class="form-control" maxlength="60"
						value="${medico.especialidad}">
				</fieldset>
				<br>
				<fieldset>
					<legend>Usuario para ingresar</legend>

					<label for="username">Usuario</label>
					<input type="text" name="usuario.username" id="username" class="form-control" maxlength="50"
						value="${medico.usuario.username}">

					<label for="clave">Contraseña</label>
					<input type="password" name="usuario.password" id="clave" class="form-control">
					<c:if test="${medico.id != null}">
						<span class="ayuda">Si la deja vacía no se cambia</span>
					</c:if>
				</fieldset>
				<input type="hidden" id="esNuevo" value="${medico.id == null}">

				<p style="margin-top: 15px">
					<button type="submit" class="boton">Guardar</button>
					<a href="/admin/medicos" class="boton boton-gris">Cancelar</a>
				</p>
			</form>
			<%@ include file="/WEB-INF/views/pie.jsp"%>
		</div>
	</div>
	<script src="/js/validaciones.js"></script>
</body>
</html>
