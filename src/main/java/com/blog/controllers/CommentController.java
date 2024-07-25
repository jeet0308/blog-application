package com.blog.controllers;

import com.blog.models.dtos.CommentDto;
import com.blog.serives.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments/")
public class CommentController {

    private static final String DELETED_SUCCESSFULLY = "Deleted Successfully";

    @Autowired
    private CommentService commentService;

    @PostMapping("post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
       return new ResponseEntity<>(this.commentService.createComment(commentDto,postId), HttpStatus.OK);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
       return new ResponseEntity<>(DELETED_SUCCESSFULLY,HttpStatus.OK);
    }
}
