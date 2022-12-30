<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-pt">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="author" content="Gonçalo Barradas, Andre Baião" />
	<title>NEEI | Portal do Alojamento</title>
	<link rel="stylesheet" type="text/css" href="/static/css/style.css" >
	<link rel="icon" href="/static/img/favicon.ico"/>
	<script src="/static/utils/anuncios.js"></script>
	<script src="https://kit.fontawesome.com/b870a66f93.js" crossorigin="anonymous"></script>

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
<!--Considerar main em vez de div id="main"-->
<div id="main">
	${sucess}
	<h1>Oferta </h1>
	<div id="desOferta" class="grid3" >${fpOferta}</div>
	<h1>Procura</h1>
	<div id="desProcura" class="grid3">${fpProcura}</div>
</div>
<footer>
	${footer}
</footer>

</body>
</html>