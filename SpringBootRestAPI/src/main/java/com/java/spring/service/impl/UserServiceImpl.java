package com.java.spring.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.java.spring.dao.interfaces.UserRepository;
import com.java.spring.exception.ResourceNotFoundException;
import com.java.spring.model.User;
import com.java.spring.service.interfaces.UserService;

@EnableTransactionManagement
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public ResponseEntity<User> getUsersById(int userId) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
	    return ResponseEntity.ok().body(user);
	}
	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}
	@Override
	public ResponseEntity<User> updateUser(int userId, User userDetails) throws ResourceNotFoundException {
		User user =
		        userRepository
		            .findById(userId)
		            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		    user.setUsername(userDetails.getUsername());
		    user.setPassword(userDetails.getPassword());
		    final User updatedUser = userRepository.save(user);
		    return ResponseEntity.ok(updatedUser);
	}
	@Override
	  public Map<String, Boolean> deleteUser(@PathVariable(value = "id") int userId) throws Exception {
	    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

	    userRepository.delete(user);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("Deleted", Boolean.TRUE);	    
	    return response;
	  }
}