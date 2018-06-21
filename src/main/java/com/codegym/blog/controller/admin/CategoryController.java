package com.codegym.blog.controller.admin;

import com.codegym.blog.model.Category;
import com.codegym.blog.model.Post;
import com.codegym.blog.service.CategoryService;
import com.codegym.blog.validation.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
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

    @GetMapping("/{id}")
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

    @GetMapping("/create")
    public ModelAndView showFormCreateCategory() {
        ModelAndView modelAndView = new ModelAndView("/admin/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    // FIXME: Add @Valid
    public ModelAndView createCategory(@ModelAttribute("category") Category category, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("/admin/category/create");
        new CategoryValidator(categoryService).validate(category, bindingResult);

        if(!bindingResult.hasFieldErrors()) {
            categoryService.save(category);
            modelAndView.addObject("category", new Category());
            modelAndView.addObject("message", "Create category successful");
        }

        return modelAndView;
    }

    @GetMapping("/{id}/update")
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

    @PostMapping("/{id}/update")
    // FIXME: Add @Valid
    public ModelAndView updateCategory(@ModelAttribute("category") Category category, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("/admin/category/update");
        new CategoryValidator(categoryService).validate(category, bindingResult);

        if(!bindingResult.hasFieldErrors()) {
            categoryService.save(category);
            modelAndView.addObject("message", "Update category successfull");
        }
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
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

    @PostMapping("/{id}/delete")
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
