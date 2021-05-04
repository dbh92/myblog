package com.example.demo.service.postService;

import com.example.demo.model.AppUser;
import com.example.demo.model.Post;
import com.example.demo.model.Role;
import com.example.demo.service.GeneralService;

public interface PostService extends GeneralService<Post> {
//    Iterable<Post> getAllByAppUser(AppUser user);

    Iterable<Post> getAllByOrderByDateDesc();

    Iterable<Post> getAllByAppUserOrderByDateDesc(AppUser appUser);
}
