package com.codegym.blog.controller.site;

import com.codegym.blog.model.Post;
import com.codegym.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeSiteController extends BaseController {

    @Autowired
    PostService postService;

    @GetMapping("")
    public ModelAndView showHomeSite(Pageable pageable) {
        Iterable<Post> posts = postService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/site/index");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }
}
