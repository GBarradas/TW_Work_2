<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="author" content="Gonçalo Barradas, Andre Baião" />
	<title>NEEI | Portal do Alojamento</title>
	<link rel="stylesheet" type="text/css" href="/static/css/style.css" >
	<link rel="icon" href="/static/img/favicon.ico"/>
	<script src="/static/utils/anuncios.js"></script>
</head>
<body>
<header>
	<nav>
		<a href="/"><img class="neei_logo" src="/static/img/Logo_NEEI_Sem_Fundo_cortado.svg" alt="Logo NEEI"></a>
		<div>
			<a href="/" id="name"><h1>Portal do Alojamento</h1></a>
		</div>
		<div id="burger-menu" >

			<div id="menu-button" onclick="showHide(this)" >
				<div class="ham1" ></div>
				<div class="ham2" ></div>
				<div class="ham3" ></div>
			</div>
			<div id="options" >
				<ul>
					<li><a href="/anuncios">Anuncios</a></li>
					<li><a href="/submit">Fazer Anuncio</a></li>
					<li><a href="/login" id="user" >${ar_user}</a></li>
				</ul>
			</div>
		</div>
	</nav>
</header>
<div id="main">
	<div class="container-login box" >
		<h1>Login</h1>

		<form action="j_spring_security_check" method="POST">
			<img src="/static/img/Logo_NEEI_Sem_Fundo_cortado-svg.svg" alt="logo">

			<div class="content-login">
				<c:if test="${not empty error}">
					<div class="error">${error}</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="msg">${msg}</div>
				</c:if>

				<label >Username:<input type="text" id="username" name="username" required></label>
				<label >Password:<input type="password" id="password" name="password" required></label>
			</div>
			<input type="submit" value="Login">
			<a href="/newuser" ><input type="button" value="Registar"></a>


			<input type="hidden" name="${_csrf.parameterName}"
				   value="${_csrf.token}" />
		</form>
	</div>

</div>

<footer>
		${footer}
	</footer>
</body>
</html>
