package com.java.spring.service.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.java.spring.exception.ResourceNotFoundException;
import com.java.spring.model.User;

public interface UserService {
	public List<User> getAllUsers();
	public ResponseEntity<User> getUsersById(@PathVariable(value = "id") int userId) throws ResourceNotFoundException;
	public User createUser(@Validated @RequestBody User user);
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") int userId, @Validated @RequestBody User userDetails) throws ResourceNotFoundException;
	public Map<String, Boolean> deleteUser(int userId) throws Exception;
}
