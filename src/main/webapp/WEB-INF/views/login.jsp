<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<title>Iniciar sesión - ECOSALUD</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<div class="login">
		<div class="login-izq">
			<h1>ECO<span>SALUD</span></h1>
			<p>Sistema para la gestión de consultas médicas.</p>
			<ul>
				<li>El administrador registra médicos y consultorios</li>
				<li>El médico revisa sus consultas</li>
				<li>El paciente consulta y cambia el horario de sus citas</li>
			</ul>
		</div>

		<div class="login-der">
			<form action="/login" method="post" onsubmit="return validarLogin()">
				<h3>Iniciar sesión</h3>

				<c:if test="${param.error != null}">
					<div class="aviso-error">Usuario o contraseña incorrectos</div>
				</c:if>
				<c:if test="${param.salir != null}">
					<div class="aviso">Se cerró la sesión</div>
				</c:if>

				<label for="username">Usuario</label>
				<input type="text" name="username" id="username" class="form-control" autofocus>

				<label for="password">Contraseña</label>
				<input type="password" name="password" id="password" class="form-control">

				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<br>
				<button type="submit" class="boton" style="width: 100%">Ingresar</button>
			</form>
		</div>
	</div>
	<script src="/js/validaciones.js"></script>
</body>
</html>
