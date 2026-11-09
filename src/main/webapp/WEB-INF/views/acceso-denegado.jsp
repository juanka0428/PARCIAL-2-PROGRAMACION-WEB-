<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Acceso denegado</title>
<%@ include file="/WEB-INF/views/cabecera.jsp"%>
</head>
<body>
	<div class="pagina">
		<%@ include file="/WEB-INF/views/encabezado.jsp"%>
		<div class="contenido">
			<div><h2>Acceso denegado (403)</h2></div>
			<div class="aviso-error">
				<b>${pageContext.request.userPrincipal.name}</b>, tu rol no tiene permiso para realizar esta acción
				o para ver esta información.
			</div>
			<p><a href="/inicio" class="boton">Volver al inicio</a></p>
			<%@ include file="/WEB-INF/views/pie.jsp"%>
		</div>
	</div>
</body>
</html>
