package com.blog2.demo.Payload;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 5, message = "Post description should be minimum 5 character")
    private String Description;
    @NotEmpty
    @Size(min = 3, message = "Post title should be minimum 3 character")
    private String Title;
    @NotEmpty
    @Size(min = 4, message = "Post description should be minimum 4 character")
    private String Content;
}
