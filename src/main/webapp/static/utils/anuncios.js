import ("https://kit.fontawesome.com/b870a66f93.js")
import("https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js")

function showHide(a){
    a.classList.toggle("change");
    document.getElementById('options').classList.toggle('active')
}

function checkPassword(){
    let form = document.forms["newUser"]
    let pass = form["password"].value
    let cpas = form["cpass"].value
    let alert = document.getElementById("alerts");

    if( pass.length <= cpas.length && pass != cpas){
        alert.classList = "alerts"
        alert.innerHTML=
            "<i class=\"fa-solid fa-triangle-exclamation\"></i> Password\'s não correspondem"
        return false
    }
    else{
        alert.innerHTML=""
        alert.classList = ""
        return true
    }
}

function cleanValue(){
    let form = document.forms["newUser"]
    form["username"].value = "";
    form["email"].value = "";
    form["password"].value = "";
}
function removeUsernameAlert(){

    try{
        let alert = document.getElementById("nameTaken");
        alert.remove();
    }
    catch (e){

    }

}
function validateNewUserInputs(form){
    return checkPassword();
}

function changeAba(a) {
    if (a.classList.contains('active'))
        return false;
    let act = document.querySelector('.aba-content.active');
    let actp = document.querySelector('.aba-option.active');
    for (n of document.querySelectorAll('.aba-content')) {
        if (n != act) {
            n.classList.add('active');
        }
    }
    actp.classList.remove('active');
    act.classList.remove('active');
    a.classList.add('active');
}

function redirectAnuncio(aid){
    window.location.href = '/anuncio?aid='+aid;
}

function showAnuncioPage(page){
    let url = new URL(window.location.href);
    let paramters = new URLSearchParams(url.search)
    paramters.delete('page')
    paramters.set('page',page)
    window.location.href = '/anuncios?'+paramters.toString()

}
function showAnuncioUser(page){
    let url = new URL(window.location.href);
    window.location.href = '/utilizador?page='+page

}
function showAnuncioAdmin(page){
    let url = new URL(window.location.href);
    let paramters = new URLSearchParams(url.search)
    paramters.delete('page')
    paramters.set('page',page)
    console.log(paramters.toString())
    window.location.href = url.pathname+"?"+paramters.toString()
    console.log(url)

}
// para apagar provavelmente
function showSelectOptions(){
    let url = new URL(window.location.href);
    let paramters = new URLSearchParams(url.search)
    let value = paramters.get("estado")
    if(value == null)
        return
    let form = document.forms["adminAnunForm"];
    form["estado"].value = value

}
function submitChangeEstado(form){
    let xhttp = new XMLHttpRequest()
    xhttp.onload = function (){
        let res = xhttp.responseText
        if(res == 'ok'){
            let div = form.querySelector(".formResult")
            div.classList.toggle('sucess')
            div.innerHTML=" " +
                "<i class=\"fa-solid fa-thumbs-up\"></i> " +
                "Estado Alterado"
            setTimeout(() => {  form.querySelector(".formResult").classList.toggle('sucess');
                form.querySelector(".formResult").innerHTML="" }, 5000);
            setTimeout(()=>{
                let aid = form["aid"].value;
                document.getElementById(aid).remove()
            },6000)


        }
        else{

        }
    }
    xhttp.open(form.method, form.action, true)
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    let args = ''
    for(i of form){
        if( i.type == 'submit'|| i.type == 'reset') {       // este if é para ignorar estes inputs
            continue;
        }
        args += i.name + '='+ i.value.normalize('NFD').replace(/[\u0300-\u036f]/g, '')+'&';
    }
    xhttp.send((args.slice(0,-1)))
    return false;
}
function submitDeleteAD(form){
    let xhttp = new XMLHttpRequest()
    xhttp.onload = function (){
        let res = xhttp.responseText
        if(res == 'ok'){
            let div = document.getElementById("delAdMsg")
            div.classList.toggle('sucess')
            div.innerHTML=" " +
                "<i class=\"fa-solid fa-trash\"></i> " +
                "Anuncio Eliminado"
            setTimeout(() => {  document.getElementById("delAdMsg").classList.toggle('sucess');
                document.getElementById("delAdMsg").innerHTML="" }, 5000);
            setTimeout(()=>{
                window.location.href="/utilizador"
            },6000)


        }
        else{

        }
    }
    xhttp.open(form.method, form.action, true)
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    let args = ''
    for(i of form){
        if( i.type == 'submit'|| i.type == 'reset') {       // este if é para ignorar estes inputs
            continue;
        }
        args += i.name + '='+ i.value.normalize('NFD').replace(/[\u0300-\u036f]/g, '')+'&';
    }
    xhttp.send((args.slice(0,-1)))
    return false;
}

function getUserAdd(aid){
    window.location.href="/utilizador/anuncio?aid="+aid
}