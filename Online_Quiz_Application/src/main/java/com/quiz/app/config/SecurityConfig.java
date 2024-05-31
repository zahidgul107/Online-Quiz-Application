package com.quiz.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.expenses.expenses.serviceImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable()
		   .authenticationProvider(authenticationProvider())
		   .authorizeRequests()
		   .antMatchers("/login","/","/registerCompany","/logout","/saveCompany","/resetPasswordRequest","/resetPassword","/register","/registerShop").permitAll()
		   .antMatchers("/settings/**").hasAuthority("ROLE_ADMIN")
		   .antMatchers("/settings/**").hasAuthority("ROLE_USER")
		   .anyRequest().authenticated()
		    .and() 
		        .formLogin()
		        .loginPage("/login") 
		        .loginProcessingUrl("/login")
		        .successHandler(authHandler())	        
		        .usernameParameter("username")
		        .failureUrl("/login?error=true")		     
		        .permitAll()
		        .and()
		        .logout()
		        .logoutSuccessUrl("/?logout")
		        .permitAll();
		
	            http.headers().frameOptions().sameOrigin();
 
	 
	
	       	return http.build();
				
	}
	
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() throws Exception{
		
		
		return (web)-> web.ignoring().antMatchers("/images/**","/js/**","/css/**");
				
		
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	     
	    authProvider.setUserDetailsService(userDetailsService());
	    authProvider.setPasswordEncoder(passwordEncoder());
	 
	    return authProvider;

	
	}


	/*	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	
	}  */
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	} 
	
	@Bean 
	public UserDetailsService userDetailsService() {
		
		return new UserDetailsServiceImpl();
		
	}

	@Bean
	public AuthenticationSuccessHandler authHandler(){
	    return new AuthenticationHandler();

	
	}
}
