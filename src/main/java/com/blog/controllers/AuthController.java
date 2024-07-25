package com.blog.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.models.dtos.JwtAuthResponse;
import com.blog.models.dtos.LoginRequest;
import com.blog.models.dtos.UserDto;
import com.blog.payload.SignUpResponse;
import com.blog.serives.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> Login(@RequestBody LoginRequest loginRequest){
    	
    	String login = this.authService.login(loginRequest);
    	logger.info("username in loginRequest: {}",loginRequest.getUsername());
    	
    	JwtAuthResponse response = new JwtAuthResponse();
    	response.setToken(login);
    	
        return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
       
    }
    
    
    @PostMapping("/register")
	public ResponseEntity<SignUpResponse> registerUser(@Valid @RequestBody UserDto userDto) {
    	SignUpResponse register = this.authService.register(userDto);
		return new ResponseEntity<SignUpResponse>(register, HttpStatus.CREATED);
	}
}
