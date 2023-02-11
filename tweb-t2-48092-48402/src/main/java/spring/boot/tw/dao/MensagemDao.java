package spring.boot.tw.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spring.boot.tw.model.Mensagem;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Repository
public class MensagemDao {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Statement stmt;


    public List<Mensagem> getMensagensAid(long aid) throws SQLException {
        stmt = dataSource.getConnection().createStatement();
        List<Mensagem> msg = new LinkedList<>();
        ResultSet rs = stmt.executeQuery("select * from mensagens where anuncio = "+aid+" order by data, tempo ;");
        while(rs.next()){
            Mensagem m =  new Mensagem();
            m.setDate(rs.getDate("data"));
            m.setAid(rs.getLong("anuncio"));
            m.setAid(rs.getLong("anuncio"));
            m.setRemetente(rs.getString("remetente"));
            m.setMsg(rs.getString("msg"));
            m.setTempo(rs.getTime("tempo"));
            msg.add(m);
        }
        return  msg;
    }

    public List<Mensagem> getMensagensAidUser(long aid, String userName) throws SQLException {
        stmt = dataSource.getConnection().createStatement();
        List<Mensagem> msg = new LinkedList<>();
        ResultSet rs = stmt.executeQuery("select * from mensagens where anuncio = "+aid+"" +
                "and remetente ilike '"+userName+"'order by data, tempo;");
        while(rs.next()){
            Mensagem m =  new Mensagem();
            m.setDate(rs.getDate("data"));
            m.setAid(rs.getLong("anuncio"));
            m.setRemetente(rs.getString("remetente"));
            m.setMsg(rs.getString("msg"));
            m.setTempo(rs.getTime("tempo"));
            msg.add(m);
        }
        return  msg;
    }

    public void saveMsg(Mensagem m) throws Exception{
        stmt = dataSource.getConnection().createStatement();
        stmt.execute("INSERT INTO mensagens" +
                "(anuncio, remetente, msg, data, tempo) VALUES (" +
                "'"+m.getAid()+"'," +
                "'"+m.getRemetente()+"'," +
                "'"+m.getMsg()+"'," +
                "'"+new Date() +"'," +
                "'"+new Time(System.currentTimeMillis())+"');");
    }


}
