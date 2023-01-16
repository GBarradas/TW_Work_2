package spring.boot.tw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.boot.tw.dao.AnuncioDao;
import spring.boot.tw.dao.MensagemDao;
import spring.boot.tw.dao.UserDao;
import spring.boot.tw.model.Anuncio;
import spring.boot.tw.model.Mensagem;
import spring.boot.tw.model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class SpringUserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AnuncioDao anuncioDao;
    @Autowired
    private MensagemDao mensagemDao;
    int pageSize = 4;

    @GetMapping("/admin")
    public String AdminUserPage(
            Model model,
            HttpServletRequest request,
            @RequestParam(name = "page",defaultValue = "1") int page
    ) throws Exception
    {

        List<User> Users = userDao.getAllUsers();
         model.addAttribute("ResultNA","&emsp;"+Users.size()+" Utilizadores Encontrados!");
        int npages = (int) Math.ceil((double) Users.size()/pageSize);
        model.addAttribute("actPage",page);
        model.addAttribute("numPages",npages);
        int start, end;
        start = (page - 1)*pageSize;
        end = (page * pageSize);
        StringBuilder sb = new StringBuilder();
        sb.append("<table><thead><tr>" +
                "<th>User Name</th><th>Email</th><th>Role</th><th>Nº Anuncios</th>" +
                "<th>Ver</th><th>Eliminar</th></tr></theda><tbody>");
        for(int i = start; i < end && i < Users.size(); i++){
            User u = Users.get(i);
            sb.append("<tr>" +
                    "<td>"+u.getUsername()+"</td>" +
                    "<td>"+u.getEmail()+"</td>" +
                    "<td>"+u.getRole().substring(5)+"</td>" +
                    "<td>"+userDao.numAnuncios(u.getUsername())+"</td>" +
                    "<td style=\"text-align: center;\" ><a href=\"/admin/ads/"+u.getUsername()+"\"  style=\"\n" +
                    "    color: black;\n" +
                    "\" ><i class=\"fa-solid fa-magnifying-glass-arrow-right\"></i></a></td>" +
                    "<td style=\"text-align: center;\"><form action=\"/deleteUser\" method=\"post\" > " +
                    "<input type=\"hidden\" name=\"user\" value=\""+u.getUsername()+ "\" >" +
                    "<button style=\"\n" +
                    "    border: none;\n" +
                    "    font-size: 1em;\n" +
                    "    background: none;\n" +
                    "\" type=\"submit\" ><i class=\"fa-solid fa-trash\"></i> </button>  </form></td>" +
                    "</tr>");
        }
        sb.append("</tbody></table>");
        model.addAttribute("table",sb);

        if ((page != 1)) {
            model.addAttribute("prevPage", page - 1);
        } else {
            model.addAttribute("prevPage", 1);
        }
        if(page == npages){
            model.addAttribute("nextPage", npages);
        }
        else{
            model.addAttribute("nextPage", page + 1);
        }
        model.addAttribute("lastPage", npages);

        return "adminMain";
    }
    @GetMapping("/admin/ads/{user}")
    public String adminAnunciosUserPage(
            @PathVariable(value = "user") String user,
            @RequestParam(value = "estado", defaultValue = "ativo") String estado,
            @RequestParam(value="page", defaultValue = "1") int page,
            Model model,HttpServletRequest request) throws Exception {

        String filtros = "estado = '"+estado+"'";
        if(user == null)
            return "redirect:/error?anuncio";
        filtros += "AND anunciante ilike '"+user+"'";
        List<Anuncio> PesAnuncios=  anuncioDao.getAnunciosFiltro(filtros);

        model.addAttribute("ResultNA","&emsp;"+PesAnuncios.size()+" Anuncios Encontrados!");
        int npages = (int) Math.ceil((double) PesAnuncios.size()/pageSize);
        model.addAttribute("actPage",page);
        model.addAttribute("numPages",npages);
        int start, end;
        start = (page - 1)*pageSize;
        end = (page * pageSize);
        StringBuilder sbA = new StringBuilder();
        for(int i = start; i < end && i < PesAnuncios.size(); i++){
            Anuncio a = PesAnuncios.get(i);
            sbA.append(a.getHtmlAnuncioAdmin());
        }
        model.addAttribute("anuncios",sbA);
        if ((page != 1)) {
            model.addAttribute("prevPage", page - 1);
        } else {
            model.addAttribute("prevPage", 1);
        }
        if(page == npages){
            model.addAttribute("nextPage", npages);
        }
        else{
            model.addAttribute("nextPage", page + 1);
        }
        model.addAttribute("lastPage", npages);

        return "admin";
    }

    @GetMapping("/admin/anuncios")
    public String adminAnunciosPage(
            @RequestParam(value = "estado", defaultValue = "ativo") String estado,
            @RequestParam(value = "user",required = false) String user,
            @RequestParam(value="page", defaultValue = "1") int page,
            Model model,HttpServletRequest request) throws Exception {


        List<Anuncio> PesAnuncios=  anuncioDao.getAnunciosByEstado(estado);

        model.addAttribute("ResultNA","&emsp;"+PesAnuncios.size()+" Anuncios Encontrados!");
        int npages = (int) Math.ceil((double) PesAnuncios.size()/pageSize);
        model.addAttribute("actPage",page);
        model.addAttribute("numPages",npages);
        int start, end;
        start = (page - 1)*pageSize;
        end = (page * pageSize);
        StringBuilder sbA = new StringBuilder();
        for(int i = start; i < end && i < PesAnuncios.size(); i++){
            Anuncio a = PesAnuncios.get(i);
            sbA.append(a.getHtmlAnuncioAdmin());
        }
        model.addAttribute("anuncios",sbA);
        if ((page != 1)) {
            model.addAttribute("prevPage", page - 1);
        } else {
            model.addAttribute("prevPage", 1);
        }
        if(page == npages){
            model.addAttribute("nextPage", npages);
        }
        else{
            model.addAttribute("nextPage", page + 1);
        }
        model.addAttribute("lastPage", npages);

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
    public String paginaUser(Model model, HttpServletRequest request,
                             @RequestParam(value="page", defaultValue = "1") int page) throws Exception
    {
        String username = request.getRemoteUser();
        User u = userDao.getUser(username);
        if(u.getRole().equals("ROLE_ADMIN"))
            return "redirect:/admin";

        ///////
        List <Anuncio> pesAnuncios = anuncioDao.getAnunciosByUser(username);
        model.addAttribute("ResultNA","&emsp;"+pesAnuncios.size()+" Anuncios Encontrados!");
        model.addAttribute("ResultNA","&emsp;"+pesAnuncios.size()+" Anuncios Encontrados!");
        int npages = (int) Math.ceil((double) pesAnuncios.size()/pageSize);
        if(pesAnuncios.size()==0){
            model.addAttribute("actPage",0);
        }
        else{
            model.addAttribute("actPage",page);
        }
        model.addAttribute("numPages",npages);
        int start, end;
        start = (page - 1)*pageSize;
        end = (page * pageSize);
        StringBuilder sbA = new StringBuilder();
        for(int i = start; i < end && i < pesAnuncios.size(); i++){
            Anuncio a = pesAnuncios.get(i);
            sbA.append(a.getHtmlAnuncioUser());
        }
        model.addAttribute("anuncios",sbA);
        if ((page != 1)) {
            model.addAttribute("prevPage", page - 1);
        } else {
            model.addAttribute("prevPage", 1);
        }
        if(page == npages){
            model.addAttribute("nextPage", npages);
        }
        else{
            model.addAttribute("nextPage", page + 1);
        }
        model.addAttribute("lastPage", npages);

        return "userPage";
    }
    @GetMapping("/utilizador/anuncio")
    public String AnuncioPage(@RequestParam Long aid,
                                    Model model, HttpServletRequest request) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Anuncio a = anuncioDao.getAnuncio(aid);
        String username = request.getRemoteUser();
        if(a == null ){
            return "redirect:/error?anuncio";
        }
        else if ( !username.equals(a.getAnunciante())){
            return "redirect:/error?anuncio";
        }
        else{
            model.addAttribute("ar_user", "Ola, "+username);
            model.addAttribute("msg","<div id=\"delAdMsg\" ></div>");
            List<Mensagem> msg = mensagemDao.getMensagensAid(aid);
            StringBuilder sbMss = new StringBuilder();
            sbMss.append("<hr><div id=\"msg\" > <h2>Mensagens: <span class=\"smaller\"><i class=\"fa-solid fa-messages\"></i>"+msg.size()+"" +
                    " mensagens recebidas</span></h2>");
            for(Mensagem m : msg){
                sbMss.append("<div class=\"box\">" +
                        "<span class=\"remetente\" >"+m.getRemetente()+" <span class=\"smaller2\"> "+sdf.format(m.getData())+"-"+m.toStringTime()+"</span> : </span>" +
                        "<span>"+m.getMsg()+"</span></div>");
            }
            sbMss.append("</div>");
            model.addAttribute("sendMsg",sbMss);
            model.addAttribute("formMsg","<hr> <form  class=\"delAd\" action=\"/deleteAnuncio\" method=\"POST\"" +
                    "onsubmit=\"return submitDeleteAD(this)\">" +
                    "<input name= \"aid\" type=\"hidden\" value=\""+a.getAid()+"\" >" +
                    "<input  type=\"submit\" value=\"Eliminar Anuncio\" > "+
                    "</form>");
        }
        model.addAttribute("tipo", a.getTipo());
        model.addAttribute("aid",a.getAid());
        model.addAttribute("titulo",a.getTitulo());
        model.addAttribute("img_src",a.getImg());
        model.addAttribute("tipo_alojamento",a.getTipologia());
        model.addAttribute("genero",a.getGenero());
        model.addAttribute("zona",a.getZona());
        model.addAttribute("preco",a.getStringPreco());
        model.addAttribute("anunciante",a.getAnunciante());
        model.addAttribute("contacto",a.getContacto());
        model.addAttribute("detalhes",a.getDescricao());

        return "anuncio";
    }
}
