package br.com.zupacademy.victor.orangetalents06templateproposta.compartilhado;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsValidBase64Validator implements ConstraintValidator<IsValidBase64, Object> {

    @Override
    public void initialize(IsValidBase64 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object base64, ConstraintValidatorContext constraintValidatorContext) {
        if(base64 == null) return false;
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        String possibleValidBase64 = (String) base64;
        try {
            decoder.decode(possibleValidBase64);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}