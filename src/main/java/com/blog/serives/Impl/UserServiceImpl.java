package com.blog.serives.Impl;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.models.dtos.UserDto;
import com.blog.models.entities.User;
import com.blog.payload.Mapper;
import com.blog.repositories.UserRepository;
import com.blog.serives.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String createUser(UserDto userDto) {
        var user = this.mapper.DtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        var save = this.userRepository.save(user);
        return "User Added Successfully";
    }

    @Override
    public UserDto getById(int id) {
        var user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found With User Id"+id));
        return this.mapper.userToDto(user);
    }

    @Override
    public List<UserDto> getUsers() {
    	List<User> users = this.userRepository.findAll();
    	return users.stream().map(user -> this.mapper.userToDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(int id, UserDto userDto) {
    	  var user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not Found With User Id"+id));
    	  user.setPassword(userDto.getPassword());
    	  user.setName(userDto.getName());
    	  user.setEmailId(userDto.getEmailId());
    	  user.setAbout(userDto.getAbout());
    	  User updatedUser = this.userRepository.save(user);
        return this.mapper.userToDto(updatedUser);
    }


}
