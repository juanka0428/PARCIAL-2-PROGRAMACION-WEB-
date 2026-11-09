<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Acceso denegado</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/encabezado.jsp"%>
	<div class="container">
		<div class="tarjeta text-center mx-auto mt-5" style="max-width: 550px">
			<h1 class="text-danger">403</h1>
			<h3 class="titulo">Acceso denegado</h3>
			<p class="text-muted">
				Hola <b>${pageContext.request.userPrincipal.name}</b>, tu rol no tiene permiso para realizar esta acción
				o para ver esta información.
			</p>
			<a href="/inicio" class="btn btn-verde">Volver al inicio</a>
		</div>
	</div>
</body>
</html>
