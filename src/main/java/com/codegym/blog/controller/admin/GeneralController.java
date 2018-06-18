package com.codegym.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeneralController {
    @GetMapping("/admin")
    public String homePage() {
        return "redirect:/admin/posts";
    }

    @GetMapping("/404")
    public ModelAndView show404Page() {
        ModelAndView modelAndView = new ModelAndView("/404");
        return modelAndView;
    }
}
