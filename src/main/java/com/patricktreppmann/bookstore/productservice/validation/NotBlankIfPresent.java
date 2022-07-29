package com.patricktreppmann.bookstore.productservice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RequestParamValidator.class)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE,ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresent {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
