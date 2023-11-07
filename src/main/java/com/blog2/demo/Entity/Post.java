package com.blog2.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "post",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="description", nullable = false)
    private String Description;
    @Column(name = "title",nullable = false )
    private String Title;
    @Column(name="content", nullable = false)
    private String Content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch =FetchType.LAZY )
    private List<Comment> comments;
}
