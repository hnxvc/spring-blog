package com.codegym.blog.controller;

import com.codegym.blog.model.Post;
import com.codegym.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/posts")
    public ModelAndView showAllPost(Pageable pageable) {
        Iterable<Post> posts = postService.findAll(pageable);
        ModelAndView modelAndview;
        if(posts != null) {
            modelAndview = new ModelAndView("/post/list");
            modelAndview.addObject("posts", posts);
        } else {
            modelAndview = new ModelAndView("/404");
        }
        modelAndview.addObject("posts", posts);
        return modelAndview;
    }

    @GetMapping("/create-post")
    public ModelAndView showFormCreatePost() {
        ModelAndView modelAndView = new ModelAndView("/post/create");
        modelAndView.addObject("post", new Post());
        return modelAndView;
    }

    @PostMapping("/create-post")
    public ModelAndView createPost(@ModelAttribute("post") Post post) {
        Date now = new Date();
        post.setCreatedDate(now);
        postService.save(post);
        ModelAndView modelAndView = new ModelAndView("/post/create");
        modelAndView.addObject("message", "Create post successful");
        return modelAndView;
    }

    @GetMapping("/update-post/{id}")
    public ModelAndView showFormUpdatePost(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        ModelAndView modelAndView;
        if(post != null) {
            modelAndView = new ModelAndView("/post/update");
            modelAndView.addObject("post", post);
        } else {
            modelAndView = new ModelAndView("/404");
        }
        return modelAndView;
    }

    @PostMapping("/update-post")
    public ModelAndView updatePost(@ModelAttribute("post") Post post) {
        Date now = new Date();
        post.setCreatedDate(now);

        postService.save(post);
        ModelAndView modelAndView = new ModelAndView("/post/update");
        modelAndView.addObject("message", "Update post successful");
        return modelAndView;
    }

}
