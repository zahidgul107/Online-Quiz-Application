package com.quiz.app.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler{
	
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean authFlag=false;
		for (GrantedAuthority authority : authorities) {
			if(authority.getAuthority().equals("ROLE_ADMIN")) {
				authFlag=true;
				try {
					redirectStrategy.sendRedirect(request, response, "/dashboard/");
					break;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			  else if(authority.getAuthority().equals("ROLE_USER")) {
				  authFlag=true;
				  try {
						
						redirectStrategy.sendRedirect(request, response, "/dashboard/");
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  }

				else {
					try {
						redirectStrategy.sendRedirect(request, response, "/");
						break;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				}
		
	}

}

	}