package spring.boot.tw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "spring.boot.tw")
public class SpringSecurityFormBasedJdbcUserDetailsServiceAuthApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityFormBasedJdbcUserDetailsServiceAuthApp.class, args);
                
                System.out.println("\nPortal do Alojamento | NEEI\nOPEN:  http://localhost:8080/");
	}

}
