package spring.boot.tw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import spring.boot.tw.service.UserAuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				/** ADMIN ACCESS **/
				.antMatchers("/admin","/updateAnuncio").access("hasRole('ADMIN')")
				.antMatchers("/registerProcura","/registerOferta","/submit","/sendMensagem","/utilizador/anuncio**",
						"/deleteAnuncio")
				.access("hasRole('USER')")
				.antMatchers("/utilizador").access("hasAnyRole('USER, ADMIN')")
				/** PUBLIC ACCESS **/
				.antMatchers("/**",  "/login", "/static/**", "/error**", "/newuser"
						,"/registeruser", "/anuncios","/anuncio**")
				.permitAll().anyRequest().authenticated()// ,																							// /,login
				.and()// ,
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/j_spring_security_check")
				.defaultSuccessUrl("/")
				.failureUrl("/login?error")
				.usernameParameter("username")
				.passwordParameter("password");
				http.cors().and().csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
