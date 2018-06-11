package com.codegym.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {
    @GetMapping("/")
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView("/category/list");
        return modelAndView;
    }
}
