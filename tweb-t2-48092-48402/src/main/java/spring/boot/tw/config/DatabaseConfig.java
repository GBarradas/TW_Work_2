package spring.boot.tw.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Statement;
import java.util.Properties;

@Configuration
public class DatabaseConfig {

	@Bean
	public DataSource dataSource() throws Exception {
		Properties properties = new Properties();
		InputStream input = new FileInputStream("src/main/resources/application.properties");
		properties.load(input);

		DriverManagerDataSource driver = new DriverManagerDataSource();
		driver.setDriverClassName("org.postgresql.Driver");
		driver.setUrl(properties.getProperty("spring.datasource.url"));
		driver.setUsername(properties.getProperty("spring.datasource.username"));
		driver.setPassword(properties.getProperty("spring.datasource.password"));
		return driver;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws Exception {
		return new JdbcTemplate(dataSource());
	}


}
/*
@Configuration
public class DatabaseConfig {

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2) // .H2 or .DERBY, etc.
				.addScript("user.sql").addScript("user-role.sql").build();
		return db;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

}
*/