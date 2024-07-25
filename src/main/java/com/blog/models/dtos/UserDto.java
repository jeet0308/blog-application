package com.blog.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private Integer userId;
    
    @NotBlank(message = "Name should not be blank")
    private String name;

    @Email(message = "Email Id is Invalid")
    private String emailId;

    private String password;
    
    private String about;
}
