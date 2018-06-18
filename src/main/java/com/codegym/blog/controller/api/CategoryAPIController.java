package com.codegym.blog.controller.api;

import com.codegym.blog.model.Category;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        if(category != null) {
            return new ResponseEntity<Category>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
    }

}
