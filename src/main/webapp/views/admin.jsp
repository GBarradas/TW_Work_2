<%@ page language="java" session="true"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Security Basic - Form Based JDBC Authentication</title>
</head>
<body>
	<div align="center">
		<h1>${title}</h1>
		<h2>${message}</h2>
		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				Welcome
				: ${pageContext.request.userPrincipal.name} | <a
					href="<c:url value='logout'/>">Logout</a>
			</h2>
		</c:if>
	</div>

	<footer>
		<p id="ppatrocinios">Patrocinios: </p>
		<div id="patrocinios">
			<!--Considerar links para as imagens-->
			<img src="static/img/logotipo_Uevora_pt_branco.png" alt="Universidade de Évora">
			<img src="static/img/LOGOEVORA_CORES.webp" alt="Camara Mununicipal de Évora">
			<img src="static/img/aaue.png" alt="Associação Academica da Universidade de Évora">
			<img src="static/img/dinf_ue.png" alt="Departamento de Informatica UE">
		</div>

		<hr>
		<p id="copyright">All content copyright © Gonçalo Barradas and Andre Baião.</p>
	</footer>
</body>
</html>