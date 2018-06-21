package com.codegym.blog.validation;

import com.codegym.blog.model.PostForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PostValidatior implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PostForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostForm postForm = (PostForm) target;
        String title = postForm.getTitle();
        String content = postForm.getContent();

        if(title.trim().length() == 0) {
            errors.rejectValue("title", "title.empty");
        }

        if(content.trim().length() == 0) {
            errors.rejectValue("content", "content.empty");
        }
    }
}
