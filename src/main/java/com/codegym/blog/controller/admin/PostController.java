package com.codegym.blog.controller.admin;

import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import com.codegym.blog.model.PostForm;
import com.codegym.blog.service.CategoryService;
import com.codegym.blog.service.PostService;
import com.codegym.blog.utils.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/admin/posts")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("")
    public ModelAndView showAllPost(Pageable pageable) {
        Iterable<Post> posts = postService.findAll(pageable);
        ModelAndView modelAndview;
        if(posts != null) {
            modelAndview = new ModelAndView("/admin/post/list");
            modelAndview.addObject("posts", posts);
        } else {
            modelAndview = new ModelAndView("/404");
        }
        modelAndview.addObject("posts", posts);
        return modelAndview;
    }

    @GetMapping("/create")
    public ModelAndView showFormCreatePost() {
        ModelAndView modelAndView = new ModelAndView("/admin/post/create");
        modelAndView.addObject("postForm", new PostForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createPost(@ModelAttribute("postForm") PostForm postForm) {

        String originalFileName = postForm.getImage().getOriginalFilename();
        String randomFileName =  StorageUtils.generateRandomFileName(originalFileName);
        try {
            postForm.getImage().transferTo(new File(StorageUtils.IMAGE_LOCATION + randomFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        Post post = new Post(postForm.getTitle(), postForm.getDescription(), postForm.getContent(), randomFileName, now, postForm.getCategory());

        postService.save(post);
        ModelAndView modelAndView = new ModelAndView("/admin/post/create");
        modelAndView.addObject("message", "Create post successful");
        modelAndView.addObject("postForm", new PostForm());
        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView showFormUpdatePost(@PathVariable("id") Long id) {
        Post post = postService.findById(id);

        PostForm postForm = new PostForm();
        postForm.setId(post.getId());
        postForm.setTitle(post.getTitle());
        postForm.setDescription(post.getDescription());
        postForm.setContent(post.getContent());
        postForm.setImageUrl(post.getImageUrl());
        postForm.setCategory(post.getCategory());

        ModelAndView modelAndView;
        if(post != null) {
            modelAndView = new ModelAndView("/admin/post/update");
            modelAndView.addObject("postForm", postForm);
        } else {
            modelAndView = new ModelAndView("/404");
        }
        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView updatePost(@PathVariable("id") Long id ,@ModelAttribute("postForm") PostForm postForm) {

        Post post = postService.findById(id);

        if(postForm.getImage() != null) {

            StorageUtils.removeImage(post.getImageUrl());

            String originalFileName = postForm.getImage().getOriginalFilename();
            String randomFileName =  StorageUtils.generateRandomFileName(originalFileName);

            post.setImageUrl(randomFileName);
            postForm.setImageUrl(randomFileName);

            try {
                postForm.getImage().transferTo(new File(StorageUtils.IMAGE_LOCATION + randomFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        post.setTitle(postForm.getTitle());
        post.setDescription(postForm.getDescription());
        post.setContent(postForm.getContent());
        post.setCategory(postForm.getCategory());
        post.setCreatedDate(new Date());

        postService.save(post);
        ModelAndView modelAndView = new ModelAndView("/admin/post/update");
        modelAndView.addObject("postForm", postForm);
        modelAndView.addObject("message", "Update post successful");
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView showFormDeletePost(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        ModelAndView modelAndView;
        if(post != null) {
            modelAndView = new ModelAndView("/admin/post/delete");
            modelAndView.addObject("post", post);
        } else {
            modelAndView = new ModelAndView("/404");
        }
        return modelAndView;
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        if(post != null) {
            postService.remove(id);
            return "redirect:/admin/posts";
        } else {
            return "redirect:/404";
        }
    }

}
