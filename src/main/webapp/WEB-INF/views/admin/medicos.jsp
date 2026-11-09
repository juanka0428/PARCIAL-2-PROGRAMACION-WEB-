<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Médicos</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/encabezado.jsp"%>
	<div class="container">
		<div class="tarjeta">
			<div class="d-flex justify-content-between align-items-center mb-3">
				<h4 class="titulo mb-0">Médicos</h4>
				<a href="/admin/medicos/nuevo" class="btn btn-verde">Nuevo médico</a>
			</div>

			<c:if test="${mensaje != null}">
				<div class="alert alert-success">${mensaje}</div>
			</c:if>
			<c:if test="${error != null}">
				<div class="alert alert-danger">${error}</div>
			</c:if>

			<div class="table-responsive">
				<table class="table table-bordered table-hover tabla">
					<thead>
						<tr>
							<th>#</th>
							<th>Nombre</th>
							<th>Especialidad</th>
							<th>Usuario</th>
							<th>Acciones</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="m" items="${medicos}">
							<tr>
								<td>${m.id}</td>
								<td>${m.nombre}</td>
								<td>${m.especialidad}</td>
								<td>${m.usuario.username}</td>
								<td>
									<a href="/admin/medicos/editar/${m.id}" class="btn btn-warning btn-sm">Editar</a>
									<form action="/admin/medicos/eliminar/${m.id}" method="post" class="d-inline"
										onsubmit="return confirm('¿Seguro que desea eliminar este médico?')">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
										<button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
									</form>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty medicos}">
							<tr>
								<td colspan="5" class="text-center text-muted">No hay médicos registrados</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
