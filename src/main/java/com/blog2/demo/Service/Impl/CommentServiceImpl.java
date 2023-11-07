package com.blog2.demo.Service.Impl;

import com.blog2.demo.Entity.Comment;
import com.blog2.demo.Entity.Post;
import com.blog2.demo.Exception.ResourceNotFound;
import com.blog2.demo.Payload.CommentDto;
import com.blog2.demo.Repository.CommentRepository;
import com.blog2.demo.Repository.PostRepository;
import com.blog2.demo.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment= maptoEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("post not found with id:" + postId));
        comment.setPost(post);
        Comment savecomment = commentRepository.save(comment);
        CommentDto dto= maptoDto(savecomment);
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("post not found with id:" + postId));
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> maptoDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("post not found with id:" + postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFound("comment not found with id:" + commentId));
        CommentDto commentDto = maptoDto(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(comment->maptoDto(comment)).collect(Collectors.toList());

    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("post not found with id:" + postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFound("comment not found with id:" + commentId));
        commentRepository.deleteById(commentId);
    }

    private CommentDto maptoDto(Comment savecomment) {
        CommentDto commentDto = modelMapper.map(savecomment, CommentDto.class);
        return commentDto;
    }

    private Comment maptoEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
