package com.blog.serives;

import java.io.IOException;
import java.util.List;

import com.blog.models.dtos.PageResponse;
import com.blog.models.dtos.PostDto;
import com.blog.models.entities.Category;
import com.blog.models.entities.Post;
import com.blog.models.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

	
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

//	public PostDto newPost(PostDto postDto);

	public PageResponse getAllPost(Integer pageSize, Integer PageNumber,String sortBy);

	public PostDto getPostById(Integer postId);

	public List<PostDto> getPostByUser(Integer userId);

	public List<PostDto> getPostByCategory(Integer categoryId);

	public List<PostDto> getPostByTitleSearchKey(String key);

	public PostDto updatePost(PostDto postDto,Integer postId);

	public PostDto uploadPostImage(Integer postId, MultipartFile file) throws IOException;
}
