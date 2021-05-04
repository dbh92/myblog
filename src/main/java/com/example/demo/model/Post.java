package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;
    private String title;
    private String content;
    private LocalDateTime date;
    private String postImage;

    @Transient
    private MultipartFile postImageFile;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;


    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER,targetEntity = CommentPost.class)
    @JsonIgnoreProperties(value = {"post"})
    List<CommentPost> commentPosts;


}
