<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Iniciar sesión - ECOSALUD</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-5 col-lg-4">
				<div class="card caja-login">
					<div class="card-header text-center">
						<h2 class="logo">ECO<span>SALUD</span></h2>
					</div>
					<div class="card-body p-4">
						<p class="text-center text-muted">Gestión de consultas médicas</p>

						<c:if test="${param.error != null}">
							<div class="alert alert-danger">Usuario o contraseña incorrectos</div>
						</c:if>
						<c:if test="${param.salir != null}">
							<div class="alert alert-success">Sesión cerrada</div>
						</c:if>

						<form action="/login" method="post" onsubmit="return validarLogin()">
							<div class="mb-3">
								<label class="form-label">Usuario</label>
								<input type="text" name="username" id="username" class="form-control" autofocus>
							</div>
							<div class="mb-3">
								<label class="form-label">Contraseña</label>
								<input type="password" name="password" id="password" class="form-control">
							</div>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
							<button type="submit" class="btn btn-verde w-100">Ingresar</button>
						</form>
					</div>
				</div>
				<p class="text-center text-muted mt-3"><small>Proyecto Programación Web - USCO</small></p>
			</div>
		</div>
	</div>
	<script src="/js/validaciones.js"></script>
</body>
</html>
