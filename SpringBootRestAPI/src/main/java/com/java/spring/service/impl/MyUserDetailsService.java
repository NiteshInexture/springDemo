package com.java.spring.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.spring.dao.interfaces.UserRepository;
import com.java.spring.model.MyUserDetails;
import com.java.spring.model.User;

import javassist.NotFoundException;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		Optional<User> user;
		try {
			user = userRepository.findByUsername(userName);
		} catch (NotFoundException e) {
			throw new UsernameNotFoundException("User Could Not Exist");
		}
		return user.map(MyUserDetails::new).get();
		//return new User("foo","foo",new ArrayList<>()); 
	}
	
}
