package br.com.trier.spring_matutino.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Component;

import br.com.trier.spring_matutino.repositories.UserRepository;

@Component
public class JwtUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		br.com.trier.spring_matutino.domain.User user = repository.findByName(username).orElseThrow(null);
		return User.builder().username(user.getName()).password(encoder.encode(user.getPassword()))
				.roles(user.getRoles().split(",")).build();
	}*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    br.com.trier.spring_matutino.domain.User user = repository.findByName(username)
	        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	    return User.builder()
	        .username(user.getName())
	        .password(encoder.encode(user.getPassword()))
	        .roles(user.getRoles().split(","))
	        .build();
	}

	

}