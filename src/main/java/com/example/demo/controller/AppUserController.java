package com.example.demo.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.AppUser;
import com.example.demo.model.CommentPost;
import com.example.demo.model.Post;
import com.example.demo.model.Role;
import com.example.demo.service.appUserService.AppUserService;
import com.example.demo.service.commentService.CommentPostService;
import com.example.demo.service.postService.PostService;
import com.example.demo.service.roleService.RoleService;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class AppUserController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PostService postService;

    String mCloudName = "dnulbp9wi";
    String mApiKey = "388747591265657";
    String mApiSecret = "QrSQljoMltB5OgDmxQM81UBSB-0";

    @Autowired
    private CommentPostService commentPostService;

    @ModelAttribute("post")
    public Post newPost(){
        Post post = new Post();
        return post;
    }

    @PostMapping("/avatar")
    public ModelAndView uploadAvatar(@ModelAttribute("avatar") MultipartFile avatar) {
        ModelAndView modelAndView = new ModelAndView("redirect:/users");
        AppUser user = appUserService.getCurrentUser();
        user.setAvatarFile(avatar);
        Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);
        try {
            File avFile = Files.createTempFile("temp", avatar.getOriginalFilename()).toFile();
            avatar.transferTo(avFile);
            Map responseAV = c.uploader().upload(avFile, ObjectUtils.emptyMap());
            JSONObject jsonAV = new JSONObject(responseAV);
            String urlAV = jsonAV.getString("url");
            user.setAvatar(urlAV);
            appUserService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @PostMapping("/cover")
    public ModelAndView uploadCover(@ModelAttribute("cover") MultipartFile cover) {
        ModelAndView modelAndView = new ModelAndView("redirect:/users");
        AppUser user = appUserService.getCurrentUser();
        user.setCoverFile(cover);
        Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);
        try {
            File cvFile = Files.createTempFile("temp", cover.getOriginalFilename()).toFile();
            cover.transferTo(cvFile);
            Map responseAV = c.uploader().upload(cvFile, ObjectUtils.emptyMap());
            JSONObject jsonAV = new JSONObject(responseAV);
            String urlCV = jsonAV.getString("url");
            user.setCover(urlCV);
            appUserService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @ModelAttribute("comment")
    public CommentPost newComment(){
        CommentPost comment = new CommentPost();
        return comment;
    }

    @ModelAttribute("myListPost")
    public Iterable<Post> MylistPost(){
        Iterable<Post> listPost = postService.getAllByAppUserOrderByDateDesc(appUserService.getCurrentUser());
        return listPost;
    }

    @ModelAttribute("listPost")
    public Iterable<Post> listPost(){
        Iterable<Post> listPost= postService.getAllByOrderByDateDesc();
        return listPost;
    }

    @ModelAttribute("user")
    public AppUser currenUser(){
        return appUserService.getCurrentUser();
    }

    @GetMapping("/myhome")
    public ModelAndView MyUserHome(){
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }

    @PostMapping("/creat/post")
    public ModelAndView homePost(@ModelAttribute("post") Post post, @ModelAttribute("postImageFile") MultipartFile postImageFile){
        ModelAndView modelAndView = new ModelAndView("redirect:/users");
        post.setAppUser(appUserService.getCurrentUser());
        post.setDate(LocalDateTime.now());
        Post post1 = postService.save(post);
        post1.setPostImageFile(postImageFile);
        Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);
        try {
            File avFile = Files.createTempFile("temp", postImageFile.getOriginalFilename()).toFile();
            postImageFile.transferTo(avFile);
            Map responseAV = c.uploader().upload(avFile, ObjectUtils.emptyMap());
            JSONObject jsonAV = new JSONObject(responseAV);
            String urlAV = jsonAV.getString("url");
            post1.setPostImage(urlAV);
            postService.save(post1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("post",post1);
        return modelAndView;
    }

    @PostMapping("/creat/comment")
    public ModelAndView homeComment( @ModelAttribute("idPost") Long id,  @ModelAttribute("content") String content){
        ModelAndView modelAndView = new ModelAndView("redirect:/users");
        Post post = postService.findById(id).get();

        CommentPost commentPost = new CommentPost();//tạo comment lưu và database. rồi mới xét lại commment cho post
        commentPost.setContent(content);
        commentPost.setAppUser(appUserService.getCurrentUser());
        commentPost.setPost(post);
        commentPost.setDate(LocalDateTime.now());
        commentPostService.save(commentPost);

        post.setCommentPosts((List<CommentPost>) commentPostService.getAllByPost(post));
        postService.save(post);

        return modelAndView;
    }

    @GetMapping()
    public ModelAndView home(@ModelAttribute String username){
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }

    @GetMapping("/khongcoquyen")
    public String accessDenied(){
        return "khongcoquyen";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/trangchu")
    public String trangchu(){
        return "timeline";
    }

    @GetMapping("/delete-post/{id}")
    public ModelAndView deletePost(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/users/myhome");
        postService.remove(id);
        return modelAndView;
    }

    @GetMapping("/delete-comment/{id}")
    public ModelAndView deleteComment(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/users/myhome");
        commentPostService.remove(id);
        return modelAndView;
    }
}
