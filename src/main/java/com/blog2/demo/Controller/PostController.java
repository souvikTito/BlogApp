package com.blog2.demo.Controller;

import com.blog2.demo.Payload.PostDto;
import com.blog2.demo.Payload.PostResponse;
import com.blog2.demo.Service.PostServices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private PostServices postServices;

    //constructor based injection
    public PostController(PostServices postServices) {
        this.postServices = postServices;
    }

    // http://localhost:8080/api/post
    @PreAuthorize("hasRole('ADMIN ')")
    @PostMapping("/savepost")
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }
        PostDto dto = postServices.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);//201: entity created
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
        postServices.deletePost(id);
        return new ResponseEntity<>("post is Deleted", HttpStatus.OK);
    }

    //    http://localhost:8080/api/post/7
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> UpdatePost(@PathVariable("id") long id, @RequestBody PostDto postDto) {
        PostDto dto = postServices.UpdatePost(id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //    http://localhost:8080/api/post/7
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
        PostDto dto = postServices.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //    http://localhost:8080/api/post?pageNo=1&pageSize=5&sortBy=title&sortDir=asc
    @GetMapping
    public PostResponse getPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBY,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PostResponse postResponse = postServices.getPost(pageNo, pageSize, sortBY, sortDir);
        return postResponse;
    }
}
