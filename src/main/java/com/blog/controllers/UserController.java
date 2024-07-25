package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.models.dtos.UserDto;
import com.blog.serives.UserService;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/")
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class UserController {

	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public String crateUser(@Valid @RequestBody UserDto userDto) {
		return this.userService.createUser(userDto);
	}

	@GetMapping(value = { "/{id}" })
	public UserDto getUserById(@PathVariable int id) {
		return this.userService.getById(id);
	}

	@GetMapping
	public List<UserDto> getAllUser() {
		return this.userService.getUsers();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = {"/{id}"})
	public UserDto updateUserById(@PathVariable int id,@Valid @RequestBody UserDto userDto) {
		return this.userService.updateUser(id, userDto);
	}
}
