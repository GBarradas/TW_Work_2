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
        <div id="filOpts" >
            <hr>
            <form id="search-form" name ="search-form" class="grid2"
                  onsubmit="" >
                <div>
                    <label for="tipo" >Tipo de Anuncio: </label><br>
                    <select name="tipo" id="tipo" required>
                        <option></option>
                        <option value="oferta" >Oferta</option>
                        <option value="procura">Procura</option>
                    </select>
                </div>
                <div>
                    <label for="zona" >Zona: </label><br>
                    <input name="zona" type="text"  id="zona" class="textinp" >

                </div>
                <div>
                    <label for="anunciante" >Anunciante: </label>
                    <input name="anunciante" id="anunciante" class="textinp" type="text" >
                </div>
                <div>
                    <label for="tipo_alojamento" >Tipo de Alojamento</label>
                    <select name="tipo_alojamento" id="tipo_alojamento" >
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
                    <select name="genero" id="genero" >
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
        ${Anuncio1}
        ${Anuncio2}
        ${Anuncio3}
        ${Anuncio4}
    </div>
    <div id="paginacao" class="box">
        <div class="pagOpt" id="optfp" onclick="showPage(1)">«</div>
        <div class="pagOpt" id="optpp" onclick="showPage(1)">&lt;</div>
        <div class="pagInfo">
            Pagina <span id="actpage">${actPage}</span> de
            <span id="npages">${numPages}</span>
        </div>
        <div class="pagOpt" id="optnp" onclick="showPage(2)"> &gt; </div>
        <div class="pagOpt" id="optlp" onclick="showPage(3)">»</div>
    </div>
</div>
<footer>
    ${footer}
</footer>
<script>

    function showFilter(f){
        f.classList.toggle('active')
        //document.getElementById('search-form').classList.toggle('active')
        document.getElementById('filOpts').classList.toggle('active')
    }
</script>
</body>
</html>