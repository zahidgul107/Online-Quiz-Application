package com.quiz.app.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.quiz.app.entity.Role;
import com.quiz.app.entity.User;


public class UserDetailsImpl implements UserDetails {

	
	
		private static final long serialVersionUID = 1L;
		private User user;
		
		
		public UserDetailsImpl(User user) {
			super();
			this.user = user;
		}
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
		  Collection<GrantedAuthority>  authorities= new HashSet<GrantedAuthority>();
		  Role role = user.getRole();
		  authorities.add(new SimpleGrantedAuthority(role.getName().name()));
//		   for (Role role : roles) {
//			   authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//		    }
		   return authorities;
		}

	@Override
	public String getPassword() { 
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
	
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
