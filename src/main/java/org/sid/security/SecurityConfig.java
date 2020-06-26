package org.sid.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = passwordEncoder();
		System.out.println(passwordEncoder.encode("1234"));
		//auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder.encode("1234")).roles("USER");
		//auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN");
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username, password, active from users where username=?")
		.authoritiesByUsernameQuery("select username, role from users_roles where username=?")
		.passwordEncoder(passwordEncoder)
		.rolePrefix("ROLE_");
	}
	 @Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.formLogin();
		 http.authorizeRequests().antMatchers("/save**/**,/delete**/**,/form**/**").hasRole("ADMIN");
		 http.authorizeRequests().anyRequest().authenticated();
		 http.csrf();
		 http.exceptionHandling().accessDeniedPage("/notAuthorized");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
}
