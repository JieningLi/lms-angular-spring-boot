package com.smoothstack.lms.orchestration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smoothstack.lms.orchestration.entity.User;
import com.smoothstack.lms.orchestration.security.JwtUtil;
import com.smoothstack.lms.orchestration.security.MyUserDetailsService;
import com.smoothstack.lms.orchestration.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("lms/users")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired 
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;

	private static Logger logger = LogManager.getLogger(UserController.class);
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user){
		logger.debug("create authentication token", user.toString());
		
		try {
			this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password", e);
		}
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmail());
        String jwt = this.jwtTokenUtil.generateToken(userDetails);
        Map<String, String> response = new HashMap<>();
        
		user = this.userService.getUser(user.getEmail());
        response.put("token", jwt);
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        response.put("role", user.getRole().toString());
        logger.debug("response:", response.toString());
        return ResponseEntity.ok(response);
	}
	
	@PostMapping("/register/borrower")
    public void createBorrower(@RequestBody @Valid User user) {
        logger.debug("request:", user.toString());
        this.userService.createBorrower(user);
    }

    @PostMapping("/register/librarian")
    public void createLibrarian(@RequestBody @Valid User user) {
        logger.debug("request: {}", user);
        this.userService.createLibrarian(user);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getUsers() {
        List<User> users = this.userService.getAllUsers();
        logger.debug("response: {}", users.toString());
        return users;
    }

	
	
}
