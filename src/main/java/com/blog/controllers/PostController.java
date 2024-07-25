package com.blog.controllers;

import com.blog.models.dtos.PageResponse;
import com.blog.models.dtos.PostDto;
import com.blog.payload.Constant;
import com.blog.serives.FileService;
import com.blog.serives.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto>  createPost(@RequestBody PostDto postDto , @PathVariable Integer userId, @PathVariable Integer categoryId){
        return new ResponseEntity<>(this.postService.createPost(postDto, userId,categoryId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = Constant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constant.SORT_BY,required = false) String sortBy){
        var allPost = this.postService.getAllPost(pageSize, pageNumber,sortBy);
        return  new ResponseEntity<>(allPost,HttpStatus.OK);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<>(this.postService.getPostById(postId),HttpStatus.OK);
    }

    @GetMapping("posts/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
        return new ResponseEntity<>(this.postService.getPostByUser(userId),HttpStatus.OK);
    }

    @GetMapping("posts/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        return new ResponseEntity<>(this.postService.getPostByCategory(categoryId),HttpStatus.OK);
    }


    @GetMapping("posts/search/{key}")
    public ResponseEntity<List<PostDto>> getPostByKey(@PathVariable String key){
       return new ResponseEntity<>(this.postService.getPostByTitleSearchKey(key),HttpStatus.OK);
    }

    @PostMapping("image/post/{postId}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("Image")MultipartFile file, @PathVariable Integer postId) throws IOException {
       return new ResponseEntity<>(this.postService.uploadPostImage(postId,file),HttpStatus.OK);
    }
}
