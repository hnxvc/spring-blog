package com.codegym.blog.controller.api;

import com.codegym.blog.model.Category;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api/categories")
public class CategoryAPIController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<Iterable<Category>> findAll() {
        Iterable<Category> categories = categoryService.findAll();
        System.out.println("Find all categories" + categories);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

}
