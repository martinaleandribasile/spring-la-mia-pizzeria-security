package com.java.pizzeria.model.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.java.pizzeria.model.User;
import com.java.pizzeria.repository.UserRepository;

public class DatabaseUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<User> utente = userRepository.findByUsername(username);
      
    if(utente.isPresent())
    	return new DatabaseUserDetails(utente.get());
    else
    	throw new UsernameNotFoundException("Non esiste utente!");
      
    }

}
