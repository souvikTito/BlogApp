package com.blog2.demo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "Comment",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})}
)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String body;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
