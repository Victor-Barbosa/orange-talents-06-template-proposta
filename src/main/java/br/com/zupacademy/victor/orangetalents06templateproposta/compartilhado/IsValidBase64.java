package br.com.zupacademy.victor.orangetalents06templateproposta.compartilhado;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy =  IsValidBase64Validator.class)
public @interface IsValidBase64 {

    String message() default "O valor já foi cadastrado";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default{};
}