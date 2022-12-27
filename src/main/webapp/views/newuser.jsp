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
<div id="main">
    <div class="container-register box" >
        <h1>Novo Utilizador</h1>

        <form action="/registeruser" method="GET" name="newUser"
        onsubmit="return validateNewUserInputs(this)">
            <img src="/static/img/Logo_NEEI_Sem_Fundo_cortado-svg.svg" alt="logo" >
            <p id="alerts" ></p>
            ${msg}
            <div class="grid2">


                <label>username: <input name="username" type="text" value="${username}" oninput="removeUsernameAlert()" required></label>
                <label>email: <input name="email" type="email" value="${email}" required ></label>
                <label>Password:<input name="password" type="password" value="${password}" required></label>
                <label>Confime a Password: <input name="cpass" id="passWord" type="password" oninput="checkPassword()" required ></label>

            <input type="submit" value="Registar">
            <input type="button" value="Limpar" onclick="cleanValue()">
            </div>

        </form>
    </div>

</div>

<footer>
    ${footer}
</footer>
</body>
</html>
