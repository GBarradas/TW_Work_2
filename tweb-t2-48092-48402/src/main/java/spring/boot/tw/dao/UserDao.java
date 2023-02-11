package spring.boot.tw.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import spring.boot.tw.model.User;
import spring.boot.tw.rowmapper.UserRowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    public User getUser(final String username) {
        return jdbcTemplate.queryForObject(
                "select u.username user_name, u.password user_pass, email user_email, ur.role user_role from user_tw u, user_role ur where u.username = ? and u.username = ur.username",
                new String[]{username}, new UserRowMapper());
    }
    public int numAnuncios(String username) throws Exception
    {
        String sql = "select count(*) as n from anuncios where anunciante ilike '"+username+"';";
        Statement stmt = dataSource.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getInt("n");
    }
    public int numAnunciosEstado(String username,String estado) throws Exception
    {
        String sql = "select count(*) as n from anuncios where anunciante ilike '"+username+"'and estado ilike '"+estado+"';";
        Statement stmt = dataSource.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getInt("n");
    }
    public List<User> getAllUsers() throws Exception
    {
        List<User> us = new ArrayList<User>();
        String sql = "select username from user_tw ";
        Statement stmt = dataSource.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            us.add(getUser(rs.getString("username")));
        }
        return us;


    }

    public void saveUser(final User u) {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        String finalPassword = bcpe.encode(u.getPassword());
        String sql = "INSERT INTO user_tw VALUES ('"
                + u.getUsername() + "','"
                + finalPassword + "','"
                + u.getEmail() + "',0)";   // 0 == not enabled
        jdbcTemplate.execute(sql);
        jdbcTemplate.execute("INSERT INTO user_role VALUES ('"
                        + u.getUsername() + "','ROLE_USER')");
        System.out.println("UserDao - saved\n" + sql + "\n");
    }

    public void deleteUser(String username){
        jdbcTemplate.execute("delete from user_role where username = '"+username+"'");
        jdbcTemplate.execute("delete from user_tw where username= '"+username+"'");
    }


    public List<String> getUsernameList() {
        return jdbcTemplate.queryForList("select user_name FROM user", String.class);
    }



}
