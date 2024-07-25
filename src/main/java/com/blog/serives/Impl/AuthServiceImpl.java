package com.blog.serives.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.models.dtos.LoginRequest;
import com.blog.models.dtos.UserDto;
import com.blog.models.entities.Role;
import com.blog.models.entities.User;
import com.blog.payload.JwtTokenHelper;
import com.blog.payload.MapperService;
import com.blog.payload.SignUpResponse;
import com.blog.repositories.RoleRepository;
import com.blog.repositories.UserRepository;
import com.blog.serives.AuthService;

@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MapperService mapperService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @SuppressWarnings("unused")
	@Override
    public String login(LoginRequest loginRequest) {
       Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
       
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginRequest.getUsername());
        
       
       String token = this.jwtTokenHelper.generateToken(userDetails);
    	
		return token;

    }
    

    @Override
    public SignUpResponse register(UserDto userDto) {
    	
    	User user = this.mapperService.map(userDto, User.class);
    	
    	user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    	
    	Role role = this.roleRepository.findById(2).orElseThrow(()-> new ResourceNotFoundException("Role Not Found!!"));
    	
    	user.getRoles().add(role);
    	
    	this.userRepository.save(user);
    	
    	SignUpResponse res = new SignUpResponse();
    	res.setUser(this.mapperService.map(user, UserDto.class));
    	
        return  res;
    }
}
