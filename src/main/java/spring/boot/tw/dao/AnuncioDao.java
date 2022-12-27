package spring.boot.tw.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spring.boot.tw.model.Anuncio;

import java.util.*;

@Repository
public class AnuncioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Anuncio> getAnunciosByEstado(String estado){
        List<Map<String,Object>> result = jdbcTemplate.queryForList(
                "select * from anuncios where estado = '"+estado+"'");
        List<Anuncio> anuncios = new LinkedList<Anuncio>();
        for(Map m : result){
            Anuncio a = new Anuncio();
            a.setAid((Long) m.get("aid"));
            a.setZona((String) m.get("zona"));
            a.setPreco((Double) m.get("data"));
            a.setGenero((String) m.get("genero"));
            a.setTipologia((String) m.get("tipologia"));
            a.setTipo((String) m.get("tipo"));
            a.setData((Date) m.get("data"));
            a.setAnunciante((String) m.get("anunciante"));
            a.setDescricao((String) m.get("descricao"));
            a.setTitulo((String) m.get("titulo"));
            anuncios.add(a);
        }
        return anuncios;
    }
    public void saveAnuncio(Anuncio a){
        String sql = "insert into anuncios(tipo, estado, anunciante, preco, genero, zona, data, tipologia) values('"+
                a.getTipo()+"', '"+
                "inativo', '"+
                a.getAnunciante()+"', '"+
                a.getPreco()+"', '"+
                a.getGenero()+"', '"+
                a.getZona()+"', '"+
                new Date() +"', '"+3
                a.getTipologia()+"')");
        jdbcTemplate.execute(sql);
    }
}
