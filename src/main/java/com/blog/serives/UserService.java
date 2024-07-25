package com.blog.serives;

import java.util.List;

import com.blog.models.dtos.UserDto;

public interface UserService {

    String createUser(UserDto userDto);

    UserDto getById(int id);

    List<UserDto> getUsers();

    UserDto updateUser(int id, UserDto userDto);
}
