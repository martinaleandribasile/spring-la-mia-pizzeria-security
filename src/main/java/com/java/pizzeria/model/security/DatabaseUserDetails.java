package com.java.pizzeria.model.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.java.pizzeria.model.Role;
import com.java.pizzeria.model.User;

public class DatabaseUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private final User username;
	
	private final Set<GrantedAuthority> authorities;
	
	public DatabaseUserDetails(User user) {
		this.username = user;
		authorities = new HashSet<GrantedAuthority>();
		for(Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.username.getPassword();
	}

	@Override
	public String getUsername() {
		return this.username.getUsername();
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
