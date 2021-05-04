package com.example.demo.repository;

import com.example.demo.model.AppUser;
import com.example.demo.model.CommentPost;
import com.example.demo.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface CommentPostRepository extends CrudRepository<CommentPost, Long> {
    Iterable<CommentPost> getAllByPost(Post post);
}
