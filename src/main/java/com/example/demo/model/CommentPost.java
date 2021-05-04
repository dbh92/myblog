package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data @AllArgsConstructor
@NoArgsConstructor
public class CommentPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;
    private LocalDateTime date;
    private String content;
    private String noti;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"commentPost"})
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "appUser_id")
    private AppUser appUser;

    public void setNoti(String noti) {
        this.noti = appUser.getName() + "just commented on your post!";
    }
}
