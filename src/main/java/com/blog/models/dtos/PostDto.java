package com.blog.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

	private int postId;

	private String postTitle;

	private String content;
	
	private String imageName;
	
	private Date addedDate;

	private CategoryDto category;

	private UserDto user;

	private Set<CommentDto> comments= new HashSet<>();
}
