package com.codegym.blog.controller;

import com.codegym.blog.model.Category;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ModelAndView showAllCategory(Pageable pageable) {
        ModelAndView modelAndView;

        Iterable<Category> categories = categoryService.findAll(pageable);
        if (categories != null) {
            modelAndView = new ModelAndView("/category/list");
            modelAndView.addObject("categories", categories);
        } else {
            modelAndView = new ModelAndView("/404");
        }
        return modelAndView;
    }
}
