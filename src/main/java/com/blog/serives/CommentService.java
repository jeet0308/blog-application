package com.blog.serives;

import com.blog.models.dtos.CommentDto;

public interface CommentService {

    public CommentDto createComment(CommentDto commentDto,Integer postId);

    public void deleteComment(Integer commentId);
}
