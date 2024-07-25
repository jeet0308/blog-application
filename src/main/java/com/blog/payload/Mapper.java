package com.blog.payload;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blog.models.dtos.UserDto;
import com.blog.models.entities.User;

@Component
public class Mapper {

    @Autowired
    private ModelMapper modelMapper;

    public User DtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto,User.class);
    }

    public UserDto userToDto(User user){
        return this.modelMapper.map(user,UserDto.class);
    }
}
