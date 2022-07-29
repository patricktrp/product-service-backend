package com.patricktreppmann.bookstore.productservice.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequestParamValidator implements ConstraintValidator<NotBlankIfPresent, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if (s == null) {
            return true;
        }
        return !s.isBlank();
    }
}
