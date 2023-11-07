package com.blog2.demo.Service;

import com.blog2.demo.Payload.PostDto;
import com.blog2.demo.Payload.PostResponse;

import java.util.List;

public interface PostServices {
    PostDto savePost(PostDto postDto);


    void deletePost(long id);

    PostDto UpdatePost(long id, PostDto postDto);

    PostDto getPostById(long id);

    PostResponse getPost(int pageNo, int pageSize, String sortBY, String sortDir);
}
