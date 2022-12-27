package spring.boot.tw.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import spring.boot.tw.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new User(rs.getString("user_name"), rs.getString("user_pass"), rs.getString("user_email"), rs.getString("user_role"));
	}

}
