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
<div id="mainError">
    <h1>ERROR PAGE <br>
    ${h1}
    </h1>
    ${errorImg}
</div>

<footer>
    <p id="ppatrocinios">Patrocinios: </p>
    <div id="patrocinios">
        <!--Considerar links para as imagens-->
        <img src="/static/img/logotipo_Uevora_pt_branco.png" alt="Universidade de Évora">
        <img src="/static/img/LOGOEVORA_CORES.webp" alt="Camara Mununicipal de Évora">
        <img src="/static/img/aaue.png" alt="Associação Academica da Universidade de Évora">
        <img src="/static/img/dinf_ue.png" alt="Departamento de Informatica UE">
    </div>

    <hr>
    <p id="copyright">All content copyright © Gonçalo Barradas and Andre Baião.</p>
</footer>
</body>
</html>
