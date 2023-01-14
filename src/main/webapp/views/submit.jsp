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
    <div class="container box" >
        <div class="select-wraper" >
            <div class="aba-option active" onclick="changeAba(this)" ><h3>Procura</h3></div>
            <div class="aba-option" onclick="changeAba(this)" ><h3>Oferta</h3></div>
        </div>
        <div class="content-wraper"  >
            <div class="aba-content active">
                <form class="grid2" action="/registerProcura" method="POST"
                      onsubmit="return submitAnun(this)"
                      enctype="multipart/form-data" >
                    <label>Titulo:
                        <input type="text" name="titulo" required >
                    </label>
                    <label>Genero:
                        <select name="genero" required>
                            <option></option>
                            <option>Masculino</option>
                            <option>Feminino</option>
                        </select>
                    </label>
                    <label>Telemovel:<input type="tel" name="contacto" maxlength="9"  required ></label>
                    <label>
                        Tipo de Alojamento:
                        <select name="tipo_alojamento" required >
                            <option></option>
                            <option>T0</option>
                            <option>T1</option>
                            <option>T2</option>
                            <option>T3</option>
                            <option>T4</option>
                            <option>T5</option>
                            <option>T6</option>
                            <option>Quarto</option>

                        </select>
                    </label>

                    <label>
                        Preço:
                        <input type="number"  name="preco" step="1" required >
                    </label>

                    <label>
                        Localização:
                        <input type="text" name="zona" required >
                    </label>

                    <label>
                        Observações:
                        <textarea name="detalhes" rows="5" cols="30" placeholder="Coloque aqui os seu texto!!" required></textarea>
                    </label>

                    <label>
                        Imagem:
                        <input type="file" name="image"
                               accept="image/png, image/jpeg" >
                    </label>

                    <input type="submit" value="Enviar"  >
                    <input type="reset" value="Limpar" >
                </form>
            </div>
            <div class="aba-content">
                <form class="grid2" action="/registerOferta" method="POST"
                      onsubmit="return submitAnun(this)"
                      enctype="multipart/form-data" >
                    <label> Titulo:
                        <input type="text" name="titulo" required>
                    </label>
                    <label>Genero:
                        <select name="genero" required>
                            <option></option>
                            <option>Individual</option>
                            <option>Masculino</option>
                            <option>Feminino</option>
                            <option>Casal</option>
                        </select>
                    </label>
                    <label>Telemovel: <input type="tel" name="contacto" maxlength="9" required ></label>
                    <label>
                        Tipo de Alojamento:
                        <select name="tipo_alojamento" required >
                            <optgroup label="Apartamento">
                                <option></option>
                                <option>T0</option>
                                <option>T1</option>
                                <option>T2</option>
                                <option>T3</option>
                                <option>T4</option>
                                <option>T5</option>
                                <option>T6</option>
                            </optgroup>
                            <optgroup label="Quartos">
                                <option value="Quarto Individual">Individual</option>
                                <option value="Quarto Duplo" >Duplo</option>
                            </optgroup>
                        </select>
                    </label>
                    <label>
                        Preço:
                        <input type="number"  name="preco" step="1" required >
                    </label>
                    <label>
                        Localização:
                        <input type="text" name="zona" required >
                    </label>
                    <label>
                        Observações:
                        <textarea name="detalhes" rows="5" cols="30" placeholder="Coloque aqui os seu texto!!" required ></textarea>
                    </label>
                    <label>
                        Imagem:
                        <input type="file" name="image"
                        accept="image/png, image/jpeg" >
                    </label>
                    <input type="submit" value="Enviar">
                    <input type="reset" value="Limpar">
                </form>
            </div>
        </div>
    </div>
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
