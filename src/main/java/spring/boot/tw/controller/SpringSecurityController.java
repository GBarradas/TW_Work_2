package spring.boot.tw.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring.boot.tw.dao.AnuncioDao;
import spring.boot.tw.dao.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import spring.boot.tw.model.User;

import static org.springframework.http.HttpStatus.*;

@Controller
public class SpringSecurityController
{

    @Autowired
    private UserDao userDao;
    @Autowired
    private AnuncioDao anuncioDao;
    public String getFooter(){
        return "<p id=\"ppatrocinios\">Patrocinios: </p>\n" +
                "<div id=\"patrocinios\">\n" +
                "  <!--Considerar links para as imagens-->\n" +
                "  <img src=\"static/img/logotipo_Uevora_pt_branco.png\" alt=\"Universidade de Évora\">\n" +
                "  <img src=\"static/img/LOGOEVORA_CORES.webp\" alt=\"Camara Mununicipal de Évora\">\n" +
                "  <img src=\"static/img/aaue.png\" alt=\"Associação Academica da Universidade de Évora\" >\n" +
                "  <img src=\"static/img/dinf_ue.png\" alt=\"Departamento de Informatica UE\" >\n" +
                "</div>\n" +
                "\n" +
                "<hr>\n" +
                "<p id=\"copyright\" >All content copyright &copy; Gonçalo Barradas and Andre Baião.</p>";
    }
    @GetMapping("/")
    public String defaultPage(Model model, HttpServletRequest request) throws Exception {
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            String username = request.getRemoteUser();
            model.addAttribute("ar_user", "Ola, "+username);
        }
        model.addAttribute("footer", getFooter());
        // show last 3 anaunces
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            String username = request.getRemoteUser();

            model.addAttribute("ar_user", "Ola, "+username);
            System.out.println(username);
            User u = userDao.getUser(username);
            System.out.println(u);
            if(u.getRole().equals("ADMIN")){
                return "redirect:/admin";
            }
            else{
                return "redirect:/";
            }

        }
        model.addAttribute("footer", getFooter());
        if (error != null) {
            model.addAttribute("error", "Invalid Credentials");
        }
        if (logout != null) {
            model.addAttribute("msg", "You have been successfully logged out");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(Model model, HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login?logout";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("title", "Administrator Control Panel");
        model.addAttribute("message", "This page demonstrates how to use Spring security");
        return "admin";
    }

    @GetMapping("/anuncios")
    public String AnunciosPage(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            String username = request.getRemoteUser();
            model.addAttribute("ar_user", "Ola, "+username);
        }
        model.addAttribute("footer", getFooter());

        return "anuncios";

    }
    @GetMapping("/newuser")
    public String RegistarUser(Model model, HttpServletRequest request){
        model.addAttribute("footer", getFooter());
        if(request.getUserPrincipal()== null){
            model.addAttribute("ar_user", "Area Reservada");
            return "newuser";
        }
        else{
            return "redirect:/";
        }
    }

    @GetMapping("/registeruser")
    public String newUser
            (
                    @RequestParam String username,
                    @RequestParam String email,
                    @RequestParam String password,
                    Model model) throws Exception {
        User user = null;
        try{
            user = userDao.getUser(username);
        }
        catch(Exception e){
        }
        if(user == null){
            user = new User(username,password,email,"ROLE_USER");
            userDao.saveUser(user);
            model.addAttribute("msg",user.getUsername()+": Registado com Sucesso!!");
        }
        else{
            model.addAttribute("msg","<p id=\"nameTaken\" class=\"alerts\"><i class=\"fa-solid fa-triangle-exclamation\"></i> Username ja utilizado!");
            model.addAttribute("username",username);
            model.addAttribute("email",email);
            model.addAttribute("password",password);
            return "newuser";
        }
        return "/";
    }



    @GetMapping("/submit")
    public String newAnuncio(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            String username = request.getRemoteUser();
            model.addAttribute("ar_user", "Ola, "+username);
        }
        model.addAttribute("footer", getFooter());
        return "submit";
    }
    // ERROS
    /*
    @RequestMapping("/error")
    public String errorhandler(Model model, HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        System.out.println(status);
        int statusValue = Integer.parseInt(status.toString());
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            String username = request.getRemoteUser();
            model.addAttribute("ar_user", "Ola, "+username);
        }
        model.addAttribute("footer", getFooter());
        if(statusValue == HttpStatus.NOT_FOUND.value()){
            model.addAttribute("h1", "Page Not Found "+statusValue);
        }
        else if(statusValue == FORBIDDEN.value()){
            model.addAttribute("h1", "Acess Denied \n "+statusValue);
        }
        return "error";
    }


    @Override
    public String getErrorPath() {
        return null;
    }*/
}
