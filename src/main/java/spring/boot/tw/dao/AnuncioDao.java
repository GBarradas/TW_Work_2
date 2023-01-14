package spring.boot.tw.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spring.boot.tw.model.Anuncio;

import javax.sql.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository
public class AnuncioDao {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Anuncio> getAnunciosByEstado(String estado) throws SQLException {
        List<Anuncio> anuncios = new LinkedList<Anuncio>();
        Statement stmt = dataSource.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("select * from anuncios where estado = '"+estado+"'");
        while(rs.next()) {
            Anuncio a = new Anuncio();
            a.setAid( rs.getLong("aid"));
            a.setZona(rs.getString("zona"));
            a.setPreco(rs.getDouble("preco"));
            a.setGenero(rs.getString("genero"));
            a.setTipologia(rs.getString("tipologia"));
            a.setTipo(rs.getString("tipo"));
            a.setData(rs.getDate("data"));
            a.setEstado(rs.getString("estado"));
            a.setAnunciante(rs.getString("anunciante"));
            a.setDescricao(rs.getString("descricao"));
            a.setTitulo(rs.getString("titulo"));
            a.setContacto(rs.getLong("contacto"));
            a.setImg(rs.getString("img"));
            anuncios.add(a);
        }
        return anuncios;
    }

    public List<Anuncio> get3Anuncios(String tipo) throws SQLException {
            List<Anuncio> anuncios = new ArrayList<Anuncio>();
            Statement stmt = dataSource.getConnection().createStatement();

            ResultSet rs = stmt.executeQuery("select * from anuncios where estado = 'ativo' and tipo = '" + tipo + "' ORDER BY data desc LIMIT 3");
            while(rs.next()) {
                Anuncio a = new Anuncio();
                a.setAid( rs.getLong("aid"));
                a.setZona(rs.getString("zona"));
                a.setPreco(rs.getDouble("preco"));
                a.setGenero(rs.getString("genero"));
                a.setTipologia(rs.getString("tipologia"));
                a.setTipo(rs.getString("tipo"));
                a.setData(rs.getDate("data"));
                a.setAnunciante(rs.getString("anunciante"));
                a.setDescricao(rs.getString("descricao"));
                a.setTitulo(rs.getString("titulo"));
                a.setEstado(rs.getString("estado"));
                a.setContacto(rs.getLong("contacto"));
                a.setImg(rs.getString("img"));
                anuncios.add(a);
            }
            return anuncios;
    }

    public Anuncio getAnuncio(long aid) throws SQLException {
        Statement stmt = dataSource.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("select * from anuncios where aid="+aid+";");
       if(rs == null){
           return null;
       }
       else if(rs.next()){
            Anuncio a = new Anuncio();
            a.setAid( rs.getLong("aid"));
            a.setZona(rs.getString("zona"));
            a.setPreco(rs.getDouble("preco"));
            a.setGenero(rs.getString("genero"));
            a.setTipologia(rs.getString("tipologia"));
            a.setTipo(rs.getString("tipo"));
            a.setData(rs.getDate("data"));
            a.setAnunciante(rs.getString("anunciante"));
            a.setDescricao(rs.getString("descricao"));
            a.setTitulo(rs.getString("titulo"));
            a.setEstado(rs.getString("estado"));
            a.setContacto(rs.getLong("contacto"));
           a.setImg(rs.getString("img"));
            return a;
        }
        else{
            return null;
        }
    }

    public long saveAnuncio(Anuncio a){

        String sql = "insert into anuncios(tipo, estado, anunciante, preco, genero, zona, data, tipologia,titulo,descricao, contacto) values('"
                + a.getTipo() +
                "', 'inativo', '" +
                a.getAnunciante() +
                "', '" + a.getPreco() +
                "', '" + a.getGenero() +
                "', '" + a.getZona() +
                "', '" + new Date() +
                "', '" + a.getTipologia() +
                "', '" + a.getTitulo() +
                "','" + a.getDescricao() +
                "','"+a.getContacto() +
                "') returning aid";

        long result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result;
    }

    public List<Anuncio> getAnunciosFiltro(String filtros) throws Exception
    {
        List<Anuncio> anuncios = new ArrayList<Anuncio>();
        Statement stmt = dataSource.getConnection().createStatement();
        System.out.println("select * from anuncios where "+filtros +";");
        ResultSet rs = stmt.executeQuery("select * from anuncios where "+filtros +";");
        while(rs.next()) {
            Anuncio a = new Anuncio();
            a.setAid( rs.getLong("aid"));
            a.setZona(rs.getString("zona"));
            a.setPreco(rs.getDouble("preco"));
            a.setGenero(rs.getString("genero"));
            a.setTipologia(rs.getString("tipologia"));
            a.setTipo(rs.getString("tipo"));
            a.setData(rs.getDate("data"));
            a.setAnunciante(rs.getString("anunciante"));
            a.setDescricao(rs.getString("descricao"));
            a.setTitulo(rs.getString("titulo"));
            a.setEstado(rs.getString("estado"));
            a.setContacto(rs.getLong("contacto"));
            a.setImg(rs.getString("img"));
            anuncios.add(a);
        }
        return anuncios;
    }
    public void updateImg(long aid, String dir) throws Exception
    {
        Statement stmt = dataSource.getConnection().createStatement();
        stmt.executeUpdate("update anuncios set img='"+dir+"' where aid = "+aid);
    }
}
