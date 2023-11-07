package com.blog2.demo.Service;

import com.blog2.demo.Payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(Long postId);

   CommentDto getCommentsById(Long postId, Long commentId);

    List<CommentDto> getAllComments();

    void deleteCommentById(Long postId, Long commentId);
}
