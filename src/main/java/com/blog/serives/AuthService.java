package com.blog.serives;

import com.blog.models.dtos.LoginRequest;
import com.blog.models.dtos.UserDto;
import com.blog.payload.SignUpResponse;

public interface AuthService {

    public String login(LoginRequest loginRequest);

    public SignUpResponse register(UserDto  userDto);
}
