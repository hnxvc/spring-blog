package com.codegym.blog.validation;

import com.codegym.blog.model.Category;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CategoryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Category category = (Category) target;
        ValidationUtils.rejectIfEmpty(errors, "category", "cateogry.empty");
    }
}
