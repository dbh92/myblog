package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data @AllArgsConstructor
@NoArgsConstructor
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImage_id;
    private String img;
    @Transient
    private MultipartFile image;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
