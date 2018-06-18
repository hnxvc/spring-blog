package com.codegym.blog.controller.admin;

import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ModelAndView showAllCategory() {
        ModelAndView modelAndView;

        Iterable<Category> categories = categoryService.findAll();
        if (categories != null) {
            modelAndView = new ModelAndView("/admin/category/list");
            modelAndView.addObject("categories", categories);
        } else {
            modelAndView = new ModelAndView("/404");
        }
        return modelAndView;
    }

    @GetMapping("/categories/{id}")
    public ModelAndView showCategory(@PathVariable("id") Long id, Pageable pageable) {
        Category category = categoryService.findById(id);
        ModelAndView modelAndView;
        if(category != null) {
            Iterable<Post> posts = categoryService.findPosts(category, pageable);

            modelAndView = new ModelAndView("/admin/category/view");
            modelAndView.addObject("category", category);
            modelAndView.addObject("posts", posts);
        } else {
            modelAndView = new ModelAndView("/404");
        }
        return modelAndView;
    }

    @GetMapping("/create-category")
    public ModelAndView showFormCreateCategory() {
        ModelAndView modelAndView = new ModelAndView("/admin/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create-category")
    public ModelAndView createCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/admin/category/create");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("message", "Create category successful");
        return modelAndView;
    }

    @GetMapping("/update-category/{id}")
    public ModelAndView showFormUpdateCategory(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        ModelAndView modelAndView;

        if(category != null) {
            modelAndView  = new ModelAndView("/admin/category/update");
            modelAndView.addObject("category", category);
        } else {
            modelAndView = new ModelAndView("/404");
        }

        return modelAndView;
    }

    @PostMapping("/update-category")
    public ModelAndView updateCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/admin/category/update");
        modelAndView.addObject("message", "Update category successfull");
        return modelAndView;
    }

    @GetMapping("/delete-category/{id}")
    public ModelAndView showFormDeleteCategory(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        ModelAndView modelAndView;

        if(category != null) {
            modelAndView  = new ModelAndView("/admin/category/delete");
            modelAndView.addObject("category", category);
        } else {
            modelAndView = new ModelAndView("/404");
        }

        return modelAndView;
    }

    @PostMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        if(category != null) {
            categoryService.remove(id);
            return "redirect:/admin/categories";
        } else {
            return  "redirect:/404";
        }
    }

}
