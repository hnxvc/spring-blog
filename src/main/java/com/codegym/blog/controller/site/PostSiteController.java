package com.codegym.blog.controller.site;

import com.codegym.blog.model.Post;
import com.codegym.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/posts")
public class PostSiteController extends BaseController {
    @Autowired
    PostService postService;

    @GetMapping("/{id}")
    public ModelAndView findPostById(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        ModelAndView modelAndView;
        if(post != null) {
            modelAndView = new ModelAndView("/site/post/view");
            modelAndView.addObject("post", post);
        } else {
            modelAndView = new ModelAndView("/404");
        }
        return modelAndView;
    }
}
