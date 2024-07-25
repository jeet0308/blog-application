package com.blog.serives.Impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.models.dtos.PageResponse;
import com.blog.models.entities.Category;
import com.blog.models.entities.Post;
import com.blog.models.entities.User;
import com.blog.payload.MapperService;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.UserRepository;
import com.blog.serives.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.models.dtos.PostDto;
import com.blog.repositories.PostRepository;
import com.blog.serives.PostService;
import org.springframework.web.multipart.MultipartFile;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class PostServiceImpl implements PostService {

	private static final String DEFAULT_IMAGE = "default.png";

	@Value("${project.image}")
	public String path;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository ;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private FileService fileService;


	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		var user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found !!!"));
		var category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));

		var post = this.mapperService.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setImageName(DEFAULT_IMAGE);
		post.setUser(user);
		post.setCategory(category);
		var save = this.postRepository.save(post);
		return this.mapperService.map(save, PostDto.class);
	}



	@Override
	public PageResponse getAllPost(Integer pageSize, Integer pageNumber,String sortBy) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());

		var postPage = this.postRepository.findAll(pageable);
		var posts = postPage.getContent();
		var postDtos = posts.stream().map(post -> this.mapperService.map(post, PostDto.class)).collect(Collectors.toList());
		PageResponse response = new PageResponse();
		response.setContent(postDtos);
		response.setPageNumber(postPage.getNumber());
		response.setPageSize(postPage.getSize());
		response.setTotalPages(postPage.getTotalPages());
		response.setTotalElements(postPage.getTotalElements());
		response.setLastPage(postPage.isLast());
		return response;
	}

	public PostDto getPostById(Integer postId){
		var post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Not Found !!!"));
		return  this.mapperService.map(post,PostDto.class);
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		var user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found !!!"));
		var post = this.postRepository.findByUser(user);
		return post.stream().map(post1 -> this.mapperService.map(post1, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		var category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not Found !!!!"));
		var postByCategory = this.postRepository.findByCategory(category);
		return postByCategory.stream().map(p -> this.mapperService.map(p, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostByTitleSearchKey(String key) {
		var posts = this.postRepository.findByTitleSearchKey("%" + key + "%");
		return posts.stream().map(post -> this.mapperService.map(post,PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		var post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found !!!"));
		post.setPostTitle(postDto.getPostTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		this.postRepository.save(post);
		return this.mapperService.map(post, PostDto.class);
	}

	@Override
	public PostDto uploadPostImage(Integer postId, MultipartFile file) throws IOException {
		var fileName = this.fileService.uploadImage(path, file);
		var post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Not Found !!"));
		post.setImageName(fileName);
		this.postRepository.save(post);
		return this.mapperService.map(post,PostDto.class);
	}


}
