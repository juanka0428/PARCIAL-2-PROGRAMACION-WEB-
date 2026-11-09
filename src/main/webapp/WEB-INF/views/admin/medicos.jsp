<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Médicos</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<div class="pagina">
		<%@ include file="/WEB-INF/views/encabezado.jsp"%>
		<div class="contenido">
			<div><h2>Médicos</h2></div>

			<c:if test="${mensaje != null}">
				<div class="aviso">${mensaje}</div>
			</c:if>
			<c:if test="${error != null}">
				<div class="aviso-error">${error}</div>
			</c:if>

			<p><a href="/admin/medicos/nuevo" class="boton">+ Nuevo médico</a></p>

			<div class="table-responsive">
				<table class="tabla">
					<tr>
						<th>#</th>
						<th>Nombre</th>
						<th>Especialidad</th>
						<th>Usuario</th>
						<th></th>
					</tr>
					<c:forEach var="m" items="${medicos}">
						<tr>
							<td>${m.id}</td>
							<td>${m.nombre}</td>
							<td>${m.especialidad}</td>
							<td>${m.usuario.username}</td>
							<td style="white-space: nowrap">
								<a href="/admin/medicos/editar/${m.id}" class="boton boton-amarillo boton-chico">Editar</a>
								<form action="/admin/medicos/eliminar/${m.id}" method="post" style="display: inline"
									onsubmit="return confirm('¿Seguro que desea eliminar este médico?')">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
									<button type="submit" class="boton boton-rojo boton-chico">Eliminar</button>
								</form>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty medicos}">
						<tr>
							<td colspan="5">No hay médicos registrados</td>
						</tr>
					</c:if>
				</table>
			</div>
			<%@ include file="/WEB-INF/views/pie.jsp"%>
		</div>
	</div>
</body>
</html>
