package com.example.demo.controller;

import com.example.demo.model.AppUser;
import com.example.demo.model.Role;
import com.example.demo.service.appUserService.AppUserService;
import com.example.demo.service.roleService.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @ModelAttribute("roles")
    public Iterable<Role> roles (){
        return roleService.findAll();
    }

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public ModelAndView adminPage(){
        ModelAndView modelAndView = new ModelAndView("adminPage");
        Role role_user = roleService.getById((long) 2);
        Role role_guest = roleService.getById((long) 3);
        modelAndView.addObject("listUsers",appUserService.getAllByRoleOrRole(role_user,role_guest));
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

    @GetMapping("/blockUser/{id}")
    public ModelAndView blockUser(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        Optional<AppUser> currentUser = appUserService.findById(id);
        AppUser currentUser1 = currentUser.get();
        Role role_guest = roleService.getById((long) 3);
        Role role_user = roleService.getById((long) 2);
        currentUser1.setRole(role_guest);
        currentUser1.setIsActive("false");
        appUserService.save(currentUser1);
        modelAndView.addObject("listUsers",appUserService.getAllByRoleOrRole(role_user,role_guest));
        return modelAndView;
    }

    @GetMapping("/unBlockUser/{id}")
    public ModelAndView unBlockUser(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        Optional<AppUser> currentUser = appUserService.findById(id);
        AppUser currentUser1 = currentUser.get();
        Role role_guest = roleService.getById((long) 3);
        Role role_user = roleService.getById((long) 2);
        currentUser1.setRole(role_user);
        currentUser1.setIsActive("true");
        appUserService.save(currentUser1);
        modelAndView.addObject("listUsers",appUserService.getAllByRoleOrRole(role_user,role_guest));
        return modelAndView;
    }
}
