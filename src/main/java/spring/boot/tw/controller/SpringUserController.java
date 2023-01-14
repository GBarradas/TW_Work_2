package spring.boot.tw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.boot.tw.dao.AnuncioDao;
import spring.boot.tw.dao.MensagemDao;
import spring.boot.tw.dao.UserDao;
import spring.boot.tw.model.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SpringUserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AnuncioDao anuncioDao;
    @Autowired
    private MensagemDao mensagemDao;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("title", "Administrator Control Panel");
        model.addAttribute("message", "This page demonstrates how to use Spring security");
        return "admin";
    }


    @GetMapping("/newuser")
    public String RegistarUser(Model model, HttpServletRequest request){
        if(request.getUserPrincipal()== null){
            model.addAttribute("ar_user", "Area Reservada");
            return "newuser";
        }
        else{
            return "redirect:/";
        }
    }

    @PostMapping("/registeruser")
    public String newUser
            (
                    @RequestParam String username,
                    @RequestParam String email,
                    @RequestParam String password,
                    Model model, RedirectAttributes ra) throws Exception {
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
        return "/login";
    }
    @GetMapping("/utilizador")
    public String paginaUser(Model model, HttpServletRequest request){
        String username = request.getRemoteUser();
        User u = userDao.getUser(username);
        //if(u.getRole())
        model.addAttribute("ar_user", "Ola, "+username);
        model.addAttribute("sucess","<h1>"+u.getRole()+"</h1>");
        return "userPage";
    }
}
