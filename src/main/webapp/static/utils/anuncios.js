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