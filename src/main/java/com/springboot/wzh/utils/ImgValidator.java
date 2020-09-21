package com.springboot.wzh.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImgValidator implements ConstraintValidator<Img, MultipartFile> {
    String[] allowedTypes;
    @Override
    public void initialize(Img constraintAnnotation) {
       allowedTypes =  constraintAnnotation.type();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        int position = value.getOriginalFilename().lastIndexOf(".");
        if(position == -1){
            return false;
        }
        String type = value.getOriginalFilename().substring(position+1);
        for(String allowedType:allowedTypes){
            if(allowedType.equals(type))
                return true;
        }
        return false;
    }
}
