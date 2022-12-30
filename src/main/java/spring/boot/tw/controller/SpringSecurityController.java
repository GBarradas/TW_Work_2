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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
        model.addAttribute("footer", getFooter());
        // show last 3 anaunces
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
    public String AnunciosPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "tipo", required = false) String tipo,
            @RequestParam(name = "zona", required = false) String zona,
            @RequestParam(name = "anunciante", required = false) String anunciante,
            @RequestParam(name = "tipo_alojamneto", required = false) String tipologia,
            @RequestParam(name = "genero", required = false) String genero,
            Model model, HttpServletRequest request) throws Exception {
        System.out.println(page+"|"+tipo+"|"+zona+"|"+anunciante+"|"+tipologia+"|"+genero);
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            String username = request.getRemoteUser();
            model.addAttribute("ar_user", "Ola, "+username);
        }
        model.addAttribute("footer", getFooter());
        String filtros = "estado = 'ativo'";
        if(tipo != null && tipo != ""){
            filtros += "and tipo ilike '"+tipo+"'";
        }
        if(zona != null && zona != ""){
            filtros += "and zona ilike '"+zona+"'";
        }
        if(tipologia != null && tipologia != ""){
            filtros += "and tipologia ilike '"+tipologia+"'";
        }
        if(anunciante != null && anunciante != "" ){
            filtros += "and anunciante ilike '"+anunciante+"'";
        }
        if(genero != null && genero != ""){
            filtros += "and genero ilike '"+genero+"'";
        }
        System.out.println(anuncioDao.getAnunciosFiltro(filtros));

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


    @GetMapping("/submit")
    public String newAnuncio(Model model, HttpServletRequest request){

        String username = request.getRemoteUser();
        model.addAttribute("ar_user", "Ola, "+username);
        model.addAttribute("anunciante",username);
        model.addAttribute("footer", getFooter());
        return "submit";
    }

    @PostMapping("/registerProcura")
    public String newAnuncioProcura(
            @RequestParam String titulo,
            @RequestParam String genero,
            @RequestParam long contacto,
            @RequestParam String tipo_alojamento,
            @RequestParam Double preco,
            @RequestParam String zona,
            @RequestParam String detalhes,
            //@RequestParam MultipartFile image,
            Model model,
            HttpServletRequest request
            ) throws Exception {

        String username = request.getRemoteUser();
        Anuncio a = new Anuncio();
        a.setTipo("Procura");
        a.setEstado("inativo");
        a.setAnunciante(username);
        a.setPreco(preco);
        a.setGenero(genero);
        a.setZona(zona);
        a.setData(new Date());
        a.setTipologia(tipo_alojamento);
        a.setDescricao(detalhes);
        a.setTitulo(titulo);
        a.setContacto(contacto);
        long aid = anuncioDao.saveAnuncio(a);
        /*String dir = "/static/anuncios/img/";
        try {
            byte[] bytes = image.getBytes();
            File img = new File(dir+"img_"+aid+"_000.png");
            OutputStream os = new FileOutputStream(img);
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

      return defaultPage(model,request, "<div class=\"sucess\"><i class=\"fa-solid fa-thumbs-up\"></i> Anuncio Registado com ID: "+aid +"</div>");
    }

    @GetMapping("/anuncio")
    public ModelAndView AnuncioPage(@RequestParam Long aid, @RequestParam(value = "mSend", required = false) String send,
                                    Model model, HttpServletRequest request) throws SQLException {
        ModelAndView mav = new ModelAndView("/anuncio");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Anuncio a = anuncioDao.getAnuncio(aid);
        String username = request.getRemoteUser();
        if(a == null ){
            mav = new ModelAndView("redirect:/error?anuncio");
            return mav;
        }
        else if (a.getEstado().equals("inativo")&&(username == null || !username.equals(a.getAnunciante()))){
            mav = new ModelAndView("redirect:/error?anuncio");
            return mav;
        }

        if(send != null){
            mav.addObject("msg", "<div class=\"sucess\"><i class=\"fa-solid fa-thumbs-up\"></i> Mensagem Enviada !!</div>");
        }
        if(request.getUserPrincipal() == null){
            mav.addObject("ar_user", "Area Reservada");
            mav.addObject("formMsg","<hr><h3>Para enviar mensagens tem de se identificar primeiro<br> <a href=\"/login\" >Login</a> </h3>");
        }
        else{
            mav.addObject("ar_user", "Ola, "+username);

            List<Mensagem> msg = mensagemDao.getMensagensAidUser(aid, username);
            StringBuilder sbMss = new StringBuilder();
            sbMss.append("<hr><div id=\"msg\" > <h2>Mensagens: <span class=\"smaller\"><i class=\"fa-solid fa-messages\"></i> "+msg.size()+"" +
                    " mensagens enviadas </span></h2>");
            for(Mensagem m : msg){
                sbMss.append("<div class=\"box\">" +
                        "<span class=\"remetente\" >"+sdf.format(m.getData())+" <span class=\"smaller2\"> "+m.toStringTime()+"</span> : </span>" +
                        "<span>"+m.getMsg()+"</span></div>");
            }
            sbMss.append("</div>");
            mav.addObject("sendMsg",sbMss);
            StringBuilder sbForm = new StringBuilder();
            sbForm.append(
                    "<form class=\"form-cont\" action=\"/sendMensagem\" method=\"POST\" >" +
                    "<hr>            " +
                    "<h2>Enviar Mensagem:</h2>            " +
                    "<input type=\"number\" name=\"aid\" value=\""+aid+"\" hidden>" +
                    "<textarea id=\"menssagem\" name=\"msg\" rows=\"6\" cols=\"80\" placeholder=\"A sua Mensagem\" required=\"\"></textarea>            " +
                    "<div class=\"optForms\">" +
                    "<input type=\"submit\" value=\"Enviar\">" +
                    "<input type=\"reset\" value=\"Limpar\">" +
                    "</div>" +
                    "</form>");
            mav.addObject("formMsg",sbForm);
        }

        mav.addObject("aid",a.getAid());
        mav.addObject("titulo",a.getTitulo());
        mav.addObject("img_src","/static/img/default.png");
        mav.addObject("tipo_alojamento",a.getTipologia());
        mav.addObject("genero",a.getGenero());
        mav.addObject("zona",a.getZona());
        mav.addObject("preco",a.getStringPreco());
        mav.addObject("anunciante",a.getAnunciante());
        mav.addObject("contacto",a.getContacto());
        mav.addObject("detalhes",a.getDescricao());
        mav.addObject("footer", getFooter());
        return mav;
    }

    @PostMapping("/registerOferta")
    public String newAnuncioOferta(
            @RequestParam String titulo,
            @RequestParam String genero,
            @RequestParam long contacto,
            @RequestParam String tipo_alojamento,
            @RequestParam Double preco,
            @RequestParam String zona,
            @RequestParam String detalhes,
            //@RequestParam MultipartFile image,
            Model model,
            HttpServletRequest request
    ) throws Exception {

        String username = request.getRemoteUser();
        Anuncio a = new Anuncio();
        a.setTipo("Oferta");
        a.setEstado("inativo");
        a.setAnunciante(username);
        a.setPreco(preco);
        a.setGenero(genero);
        a.setZona(zona);
        a.setData(new Date());
        a.setTipologia(tipo_alojamento);
        a.setDescricao(detalhes);
        a.setTitulo(titulo);
        a.setContacto(contacto);
        long aid = anuncioDao.saveAnuncio(a);
        /*String dir = "/static/anuncios/img/";
        try {
            byte[] bytes = image.getBytes();
            File img = new File(dir+"img_"+aid+"_000.png");
            OutputStream os = new FileOutputStream(img);
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

        return defaultPage(model,request, "<div class=\"sucess\"><i class=\"fa-solid fa-thumbs-up\"></i> Anuncio Registado com ID: "+aid +"</div>");
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
