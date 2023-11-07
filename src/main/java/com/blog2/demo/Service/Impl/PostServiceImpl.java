package com.blog2.demo.Service.Impl;

import com.blog2.demo.Entity.Post;
import com.blog2.demo.Exception.ResourceNotFound;
import com.blog2.demo.Payload.PostDto;
import com.blog2.demo.Payload.PostResponse;
import com.blog2.demo.Repository.PostRepository;
import com.blog2.demo.Service.PostServices;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostServices {
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {

        Post post = mapToEntity(postDto);
        Post savePost = postRepository.save(post);

        PostDto dto = mapToDto(savePost);
        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override

    public PostDto UpdatePost(long id, PostDto postDto) {

        Post exist = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Post not found with id: " + id));
        exist.setTitle(postDto.getTitle());
        exist.setDescription(postDto.getDescription());
        exist.setContent(postDto.getContent());
        Post savePost = postRepository.save(exist);

        PostDto dto = mapToDto(savePost);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post not found with id:" + id));
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public PostResponse getPost(int pageNo, int pageSize, String sortBY, String sortDir) {
//using order reference to sortBY and on given direction
        Sort orders = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBY).ascending() :
                Sort.by(sortBY).descending();

        PageRequest of = PageRequest.of(pageNo, pageSize, orders);
        Page<Post> pagePost = postRepository.findAll(of);
        List<Post> posts = pagePost.getContent();
//        using lambda to maptoDto
        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDto(postDtos);
        postResponse.setPageNo(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setTotalElement(pagePost.getTotalElements());
        postResponse.setLast(pagePost.isLast());
        return postResponse;
    }

    PostDto mapToDto(Post post) {
        PostDto dto = new PostDto();
        PostDto dto1 = modelMapper.map(post, PostDto.class);
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setContent(post.getContent());
//        dto.setDescription(post.getDescription());
        return dto1;
    }

    Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        Post post1 = modelMapper.map(postDto, Post.class);
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post1;
    }
}
