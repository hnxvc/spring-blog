package com.codegym.blog.model;

import org.springframework.web.multipart.MultipartFile;


public class PostForm {

    private Long id;
    private String title;
    private String description;
    private String content;
    private MultipartFile image;
    private String imageUrl;

    private Category category;

    public PostForm() {
    }

    public PostForm(String title, String description, String content, MultipartFile image, Category category) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.image = image;
        this.category = category;
    }

    public PostForm(Long id, String title, String description, String content, MultipartFile image, Category category, String imageUrl) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.image = image;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
