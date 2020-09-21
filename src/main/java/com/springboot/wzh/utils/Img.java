package com.springboot.wzh.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ImgValidator.class)
public @interface Img {

    boolean required() default true;

    String message() default "图片格式错误";
    String[] type() default {"jpg","png","jpeg","gif"};
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}