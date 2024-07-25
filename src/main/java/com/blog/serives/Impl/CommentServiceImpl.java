package com.blog.serives.Impl;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.models.dtos.CommentDto;
import com.blog.models.entities.Comment;
import com.blog.payload.MapperService;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.serives.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MapperService mapperService;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        var post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post Not Found !!!"));
        var comment = this.mapperService.map(commentDto, Comment.class);
        comment.setPost(post);
        var save = this.commentRepository.save(comment);
        return this.mapperService.map(save,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        var comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment Not Found !!!!"));
        this.commentRepository.delete(comment);
    }
}
