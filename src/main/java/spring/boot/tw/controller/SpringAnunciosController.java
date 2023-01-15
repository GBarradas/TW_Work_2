package spring.boot.tw.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import spring.boot.tw.dao.AnuncioDao;
import spring.boot.tw.dao.MensagemDao;
import spring.boot.tw.dao.UserDao;
import spring.boot.tw.model.Anuncio;
import spring.boot.tw.model.Mensagem;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class SpringAnunciosController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AnuncioDao anuncioDao;
    @Autowired
    private MensagemDao mensagemDao;
    @Autowired
    private SpringSecurityController ssc;


    int pageSize = 4;
    @GetMapping("/anuncios")
    public String AnunciosPage(
            @RequestParam(value = "tipo", required = false) String tipo,
            @RequestParam(value = "zona", required = false) String zona,
            @RequestParam(value = "anunciante", required = false) String anunciante,
            @RequestParam(value = "tipo_alojamneto", required = false) String tipologia,
            @RequestParam(value = "genero", required = false) String genero,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model, HttpServletRequest request) throws Exception {
        List<Anuncio> PesAnuncios = null;
        if(page == 0){
            return "redirect:/anuncios?page=1";
        }
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            String username = request.getRemoteUser();
            model.addAttribute("ar_user", "Ola, "+username);
        }
        String filtros = "estado = 'ativo'";
        System.out.println(tipo);
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
        PesAnuncios=  anuncioDao.getAnunciosFiltro(filtros);

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
            sbA.append(a.getHtmlAnuncio());
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

        return "anuncios";
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
            @RequestParam MultipartFile image,
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
        String dir = "src/main/webapp/static/anuncios/img/";
        long aid = anuncioDao.saveAnuncio(a);
        try {
            byte[] bytes = image.getBytes();
            File img = new File(dir+"img_"+aid+".png");
            System.out.println(dir);
            FileOutputStream fos = new FileOutputStream(img);
            fos.write(bytes);
            anuncioDao.updateImg( aid,("/static/anuncios/img/"+"img_"+aid+".png" ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            model.addAttribute("ar_user", "Ola, "+username);
        }
        HttpURLConnection cnt = (HttpURLConnection) new URL(
                "http://alunos.di.uevora.pt/tweb/t2/mbref4payment?amount=10.0"
        ).openConnection();
        cnt.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        cnt.setRequestProperty("Accept", "application/json");
        cnt.setRequestMethod("GET");
        cnt.setDoOutput(true);
        try{
            BufferedReader ri = new BufferedReader(new InputStreamReader(cnt.getInputStream()));
            String response = ri.readLine();
            JSONObject json = new JSONObject(response);
            model.addAttribute("entidade",json.getString("mb_entity"));
            model.addAttribute("ref",json.getString("mb_reference"));
            model.addAttribute("value",json.getString("mb_amount"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("sucess","<div class=\"sucess\"><i class=\"fa-solid fa-thumbs-up\"></i> Anuncio Registado com ID:"+aid+"</div>");


        //"<div class=\"sucess\"><i class=\"fa-solid fa-thumbs-up\"></i> Anuncio Registado com ID: "+aid +"</div>";
        return "sucess";
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
        else if(username != null && username.equals(a.getAnunciante())){
            return new ModelAndView("redirect:/utilizador/anuncio?aid="+aid);
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
            sbMss.append("<hr><div id=\"msg\" > <h2>Mensagens: <span class=\"smaller\"><i class=\"fa-solid fa-messages\"></i>"+msg.size()+"" +
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
        mav.addObject("tipo", a.getTipo());
        mav.addObject("aid",a.getAid());
        mav.addObject("titulo",a.getTitulo());
        mav.addObject("img_src",a.getImg());
        mav.addObject("tipo_alojamento",a.getTipologia());
        mav.addObject("genero",a.getGenero());
        mav.addObject("zona",a.getZona());
        mav.addObject("preco",a.getStringPreco());
        mav.addObject("anunciante",a.getAnunciante());
        mav.addObject("contacto",a.getContacto());
        mav.addObject("detalhes",a.getDescricao());
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
            @RequestParam MultipartFile image,
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
        String dir = "src/main/webapp/static/anuncios/img/";
        try {
            byte[] bytes = image.getBytes();
            File img = new File(dir+"img_"+aid+".png");
            FileOutputStream fos = new FileOutputStream(img);
            fos.write(bytes);
            anuncioDao.updateImg( aid,("/static/anuncios/img/"+"img_"+aid+".png" ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(request.getUserPrincipal() == null){
            model.addAttribute("ar_user", "Area Reservada");
        }
        else{
            model.addAttribute("ar_user", "Ola, "+username);
        }
        HttpURLConnection cnt = (HttpURLConnection) new URL(
                "http://alunos.di.uevora.pt/tweb/t2/mbref4payment?amount=10.0"
        ).openConnection();
        cnt.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        cnt.setRequestProperty("Accept", "application/json");
        cnt.setRequestMethod("GET");
        cnt.setDoOutput(true);
        try{
            BufferedReader ri = new BufferedReader(new InputStreamReader(cnt.getInputStream()));
            String response = ri.readLine();
            JSONObject json = new JSONObject(response);
            model.addAttribute("entidade",json.getString("mb_entity"));
            model.addAttribute("ref",json.getString("mb_reference"));
            model.addAttribute("value",json.getString("mb_amount"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("sucess","<div class=\"sucess\"><i class=\"fa-solid fa-thumbs-up\"></i> Anuncio Registado com ID: "+aid+"</div>");

        return "sucess";
    }

}
