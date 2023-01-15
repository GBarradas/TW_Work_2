package spring.boot.tw.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import spring.boot.tw.model.User;
import spring.boot.tw.rowmapper.UserRowMapper;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUser(final String username) {
        return jdbcTemplate.queryForObject(
                "select u.username user_name, u.password user_pass, email user_email, ur.role user_role from user_tw u, user_role ur where u.username = ? and u.username = ur.username",
                new String[]{username}, new UserRowMapper());
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

    public List<String> getUsernameList() {
        return jdbcTemplate.queryForList("select user_name FROM user", String.class);
    }



}
