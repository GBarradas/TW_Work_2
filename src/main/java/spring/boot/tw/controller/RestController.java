package spring.boot.tw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.boot.tw.dao.AnuncioDao;
import spring.boot.tw.dao.MensagemDao;
import spring.boot.tw.dao.UserDao;
import spring.boot.tw.model.Anuncio;

import javax.servlet.http.HttpServletRequest;
import java.sql.Statement;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AnuncioDao anuncioDao;
    @Autowired
    private MensagemDao mensagemDao;
    @Autowired
    private SpringSecurityController ssc;
    @PostMapping("/updateAnuncio")
    public String updateAnuncio(
            @RequestParam(name = "aid") long aid,
            @RequestParam(name="estado") String estado
    ) throws Exception {
        Anuncio a = anuncioDao.getAnuncio(aid);
        if(a.getEstado().equals(estado)){
            return "Estado nao alterado!";
        }
        anuncioDao.updateState(aid,estado);
        return "ok";
    }
    @PostMapping("/deleteAnuncio")
    public String deleteAnuncio(
            @RequestParam(name="aid") long aid
            //HttpServletRequest request
    ) throws Exception
    {
        System.out.println(aid);
        //String username = request.getRemoteUser();
        Anuncio a = anuncioDao.getAnuncio(aid);
        if(a == null){
            return "erro";
        }
        String aname = a.getAnunciante();
        //if(!username.equals(aname)){
         //   return "erro";
        //}
        anuncioDao.removeAnuncio(aid);
        return "ok";
    }
}
