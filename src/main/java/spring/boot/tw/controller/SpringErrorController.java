package spring.boot.tw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@Controller
public class SpringErrorController implements ErrorController
{
    @Autowired
    private SpringSecurityController springSecurityController;
    // ERROS
    @GetMapping("/error")
    public String errorhandler(@RequestParam(value = "anuncio", required = false) String anuncio ,
                               Model model, HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        System.out.println(status);
        if(anuncio != null){
            return errorAnuncio(model, request);
        }
        int statusValue = Integer.parseInt(status.toString());
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            String username = request.getRemoteUser();
            model.addAttribute("ar_user", "Ola, "+username);
        }
        if(statusValue == HttpStatus.NOT_FOUND.value()){
            model.addAttribute("h1", "Page Not Found "+statusValue);
            model.addAttribute("errorImg","<img src=\"/static/img/404.jpeg\" >");
        }
        else if(statusValue == FORBIDDEN.value()){
            model.addAttribute("h1", "Acess Denied \n "+statusValue);
            model.addAttribute("errorImg","<img src=\"/static/img/403.jpg\" >");
        }
        return "error";
    }
    @GetMapping("/error?anuncio")
    public String errorAnuncio(Model model,HttpServletRequest request ){
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            String username = request.getRemoteUser();
            model.addAttribute("ar_user", "Ola, "+username);
        }
        model.addAttribute("h1", "Algo correu mal <br> Anuncio não encontrado");
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
