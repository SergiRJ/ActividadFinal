package com.nttdata.actividadfinal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService usuarioService;
	
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService ( usuarioService ).passwordEncoder(passwordEncoder());
	}
	
	
	
	String[] resources = new String[] {"/include/**","/js/**","/css/**", "/api/**"};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers(resources).permitAll()
		.and().authorizeRequests().anyRequest().authenticated()
		.and().httpBasic()
		.and().formLogin()
		.failureUrl("/login?error=true")
		.defaultSuccessUrl("/")
		.and().logout().logoutSuccessUrl("/login?logout=true").permitAll()
		.and().rememberMe().key("uniqueAndSecret");
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
}
	