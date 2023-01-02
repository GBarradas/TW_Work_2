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


    <div id="filtro" class="box" >

        <div id="filtro-controlo" onclick="showFilter(this)" ></div>
        <h1>Pesquisar </h1>
        <span class="smaller2" > ${ResultNA}</span>
        <div id="filOpts" >
            <hr>
            <form id="search-form" name ="search-form" class="grid2"
                  action="/anuncios" method="POST" onsubmit="" >
                <div>
                    <label for="tipo" >Tipo de Anuncio: </label><br>
                    <select name="tipo" id="tipo" value="${tipo}" required>
                        <option></option>
                        <option value="oferta" >Oferta</option>
                        <option value="procura">Procura</option>
                    </select>
                </div>
                <div>
                    <label for="zona" >Zona: </label><br>
                    <input name="zona" type="text"  id="zona" class="textinp" value="${zona}" >

                </div>
                <div>
                    <label for="anunciante" >Anunciante: </label>
                    <input name="anunciante" id="anunciante" class="textinp" type="text" value="${anunciante}" >
                </div>
                <div>
                    <label for="tipo_alojamento" >Tipo de Alojamento</label>
                    <select name="tipo_alojamento" id="tipo_alojamento" value="${tipologia}" >
                        <option value=""></option>
                        <option value="T0">T0</option>
                        <option value="T1">T1</option>
                        <option value="T2">T2</option>
                        <option value="T3">T3</option>
                        <option value="T4">T4</option>
                        <option value="T5">T5</option>
                        <option value="T6">T6</option>
                        <option value="Quarto">Quarto</option>

                    </select>
                </div>
                <div>
                    <label for="genero" >Genero:</label>
                    <select name="genero" id="genero" value="${genero}">
                        <option></option>
                        <option>Masculino</option>
                        <option>Feminino</option>
                        <option>Indiferente</option>
                    </select>
                </div>
                <div id="search-form-options" >
                    <input type="submit" value=" Filtrar " >
                    <input type="reset" value=" Limpar " onclick="getAnnoun()" >
                </div>
            </form>
        </div>

    </div>
    <div id="results">
        <div class="page active">
            ${anuncios}
        </div>
    </div>
    <div id="paginacao" class="box">
        <div class="pagOpt" id="optfp" onclick="showAnuncioPage(1)">«</div>
        <div class="pagOpt" id="optpp" onclick="showAnuncioPage(${prevPage})">&lt;</div>
        <div class="pagInfo">
            Pagina <span id="actpage">${actPage}</span> de
            <span id="npages">${numPages}</span><br>

        </div>
        <div class="pagOpt" id="optnp" onclick="showAnuncioPage(${nextPage})"> &gt; </div>
        <div class="pagOpt" id="optlp" onclick="showAnuncioPage(${lastPage})">»</div>
    </div>
</div>

<footer>
    ${footer}
</footer>
<script>

    showSelectOptions()
    function showFilter(f){
        f.classList.toggle('active')
        //document.getElementById('search-form').classList.toggle('active')
        document.getElementById('filOpts').classList.toggle('active')
    }
</script>
</body>
</html>
