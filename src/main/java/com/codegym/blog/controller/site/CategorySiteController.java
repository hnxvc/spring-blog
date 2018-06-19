package com.codegym.blog.controller.site;

import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategorySiteController extends BaseController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories/{id}")
    public ModelAndView showPostsInCategory(@PathVariable("id") Long id, Pageable pageable) {
        ModelAndView modelAndView;
        Category category = categoryService.findById(id);
        if(category != null) {
            Iterable<Post> posts = categoryService.findPosts(category, pageable);
            modelAndView = new ModelAndView("/site/category/view");
            modelAndView.addObject("category", category);
            modelAndView.addObject("posts", posts);
        } else {
            modelAndView = new ModelAndView("/404");
        }
        return modelAndView;
    }
}
