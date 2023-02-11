package spring.boot.tw.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.boot.tw.dao.AnuncioDao;
import spring.boot.tw.dao.MensagemDao;
import spring.boot.tw.dao.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import spring.boot.tw.model.Anuncio;
import spring.boot.tw.model.Mensagem;
import spring.boot.tw.model.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Controller
public class SpringSecurityController
{

    @Autowired
    private UserDao userDao;
    @Autowired
    private AnuncioDao anuncioDao;
    @Autowired
    private MensagemDao mensagemDao;

    int pageSize = 4;

    List<Anuncio> PesAnuncios;

    @GetMapping("/")
    public String defaultPage(Model model, HttpServletRequest request, String mensagem) throws Exception {
        if(mensagem != null){
            model.addAttribute("sucess",mensagem);
        }
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            String username = request.getRemoteUser();
            model.addAttribute("ar_user", "Ola, "+username);
        }
        // show last 3 announces
        List<Anuncio> anunciosO = anuncioDao.get3Anuncios("Oferta");
        StringBuilder sbO = new StringBuilder();
        for(Anuncio a : anunciosO){
            sbO.append(a.getHtmlAnuncio());
        }
        model.addAttribute("fpOferta",sbO);

        List<Anuncio> anunciosP = anuncioDao.get3Anuncios("Procura");
        StringBuilder sbP = new StringBuilder();
        for(Anuncio a : anunciosP){
            sbP.append(a.getHtmlAnuncio());
        }
        model.addAttribute("fpProcura",sbP);
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
            User u = userDao.getUser(username);
            if(u.getRole().equals("ADMIN")){
                return "redirect:/admin";
            }
            else{
                return "redirect:/utilizador";
            }

        }
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




    @GetMapping("/submit")
    public String newAnuncio(Model model, HttpServletRequest request){

        String username = request.getRemoteUser();
        model.addAttribute("ar_user", "Ola, "+username);
        model.addAttribute("anunciante",username);
        return "submit";
    }



    @PostMapping("/sendMensagem")
    public String sendMensagem(
        @RequestParam("aid") long aid,
        @RequestParam("msg") String msg,
        Model model,
        HttpServletRequest request

    ) throws Exception {

        String username = request.getRemoteUser();
        Mensagem m = new Mensagem(aid,username,msg,new Date());
        mensagemDao.saveMsg(m);
        return "redirect:anuncio?mSend&aid="+aid;
    }


}
