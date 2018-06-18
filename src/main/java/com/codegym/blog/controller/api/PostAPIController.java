package com.codegym.blog.controller.api;

import com.codegym.blog.model.Post;
import com.codegym.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api/posts")

public class PostAPIController {

    @Autowired
    PostService postService;

    @GetMapping("")
    public ResponseEntity<Iterable<Post>> findAll(Pageable pageable) {
        Iterable<Post> posts = postService.findAll(pageable);
        if(posts != null) {
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable("id") Long id) {
        Post post = postService.findById(id);
        if(post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
