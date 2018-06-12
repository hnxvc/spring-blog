package com.codegym.blog.controller;

import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import com.codegym.blog.model.PostForm;
import com.codegym.blog.service.CategoryService;
import com.codegym.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

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
        modelAndView.addObject("postForm", new PostForm());
        return modelAndView;
    }

    @PostMapping("/create-post")
    public ModelAndView createPost(@ModelAttribute("postForm") PostForm postForm) {

        String originalFileName = postForm.getImage().getOriginalFilename();

        String PATH_IMAGE = "/Users/hoa/Pictures/temp/";
        try {
            postForm.getImage().transferTo(new File( PATH_IMAGE + originalFileName ));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        Post post = new Post(postForm.getTitle(), postForm.getDescription(), postForm.getContent(), PATH_IMAGE + originalFileName, now);

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
        modelAndView.addObject("post", post);
        modelAndView.addObject("message", "Update post successful");
        return modelAndView;
    }

    @GetMapping("/delete-post/{id}")
    public ModelAndView showFormDeletePost(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        ModelAndView modelAndView;
        if(post != null) {
            modelAndView = new ModelAndView("/post/delete");
            modelAndView.addObject("post", post);
        } else {
            modelAndView = new ModelAndView("/404");
        }
        return modelAndView;
    }

    @PostMapping("/delete-post/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        if(post != null) {
            postService.remove(id);
            return "redirect:/posts";
        } else {
            return "redirect:/404";
        }
    }

}
