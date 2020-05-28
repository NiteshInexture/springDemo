/*
 *
 *  Copyright (c) 2018-2020 Givantha Kalansuriya, This source is a part of
 *   Staxrt - sample application source code.
 *   http://staxrt.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.java.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.java.spring.dao.interfaces.UserRepository;
import com.java.spring.exception.ResourceNotFoundException;
import com.java.spring.jwt.JwtUtil;
import com.java.spring.model.User;
import com.java.spring.model.UserResponse;
import com.java.spring.service.impl.MyUserDetailsService;
import com.java.spring.service.interfaces.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

/**
 * The type User controller.
 *
 * @author Givantha Kalansuriya
 */
@Controller
public class UserController {

	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtils;
	
	@GetMapping("/")
	public String index() {
	  return "index2";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		}catch (BadCredentialsException e) {	
			throw new Exception("Incorrect UserName or Password");
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		final String jwt= jwtTokenUtils.generateToken(userDetails);
		
		return ResponseEntity.ok(new UserResponse(jwt));
	}
	
  @GetMapping("/users")
  public @ResponseBody List<User> getAllUsers(Model model) throws ResourceNotFoundException {
	  List<User> userlist =userService.getAllUsers();
	  if(userlist.size()>0)
		  return userlist;
	  else
		  throw new ResourceNotFoundException("List is null");
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUsersById(@PathVariable(value = "id") int userId)
      throws ResourceNotFoundException {
	  return userService.getUsersById(userId);
  }

  /**
   * Create user user.
   *
   * @param user the user
   * @return the user
   */
  @PostMapping("/users")
  public String createUser(@Valid @RequestBody User user) {
	  userService.createUser(user);
	  return "hello user";
  }

  /**
   * Update user response entity.
   *
   * @param userId the user id
   * @param userDetails the user details
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @PutMapping("/users/{id}")
  public ResponseEntity<User> updateUser(@RequestBody @Valid User user, BindingResult bindingResult, @PathVariable(value = "id") int userId
		  								)
		  										throws ResourceNotFoundException {
	  if(bindingResult.hasErrors()) {
		  System.out.println("nodata");
	  }
	  return userService.updateUser(userId, user); 
  }

  /**
   * Delete user map.
   *
   * @param userId the user id
   * @return the map
   * @throws Exception the exception
   */
  @DeleteMapping("/users/{id}")
  public @ResponseBody Map<String, Boolean> deleteUser(@PathVariable(value = "id") int userId) throws Exception {
	  Map<String,Boolean> map=userService.deleteUser(userId);
	  for (Entry<String, Boolean> entry : map.entrySet())  
          System.out.println("Key = " + entry.getKey() + 
                           ", Value = " + entry.getValue()); 
	  return map;
  }
}
