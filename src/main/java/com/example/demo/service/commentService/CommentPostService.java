package com.example.demo.service.commentService;

import com.example.demo.model.CommentPost;
import com.example.demo.model.Post;
import com.example.demo.service.GeneralService;

public interface CommentPostService extends GeneralService<CommentPost> {
    Iterable<CommentPost> getAllByPost(Post post);
}
