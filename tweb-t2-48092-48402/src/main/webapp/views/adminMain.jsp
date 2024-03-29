<%@ page language="java" session="true"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                    <li><a href="/admin/anuncios">Gerir Anuncios</a></li>
                    <li><a href="/logout" id="user" >Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>
</header>
<!--Considerar main em vez de div id="main"-->
<div id="mainAdminUsers">
    <div id="deleteResults" ></div>
    <span class="smaller2" > ${ResultNA}</span>
    <div id="results">
        ${table}
    </div>
    <div id="paginacao" class="box">
        <div class="pagOpt" id="optfp" onclick="showAnuncioAdmin(1)">«</div>
        <div class="pagOpt" id="optpp" onclick="showAnuncioAdmin(${prevPage})">&lt;</div>
        <div class="pagInfo">
            Pagina <span id="actpage">${actPage}</span> de
            <span id="npages">${numPages}</span><br>

        </div>
        <div class="pagOpt" id="optnp" onclick="showAnuncioAdmin(${nextPage})"> &gt; </div>
        <div class="pagOpt" id="optlp" onclick="showAnuncioAdmin(${lastPage})">»</div>
    </div>

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
<script>
    showSelectOptions();
</script>
</body>
</html

