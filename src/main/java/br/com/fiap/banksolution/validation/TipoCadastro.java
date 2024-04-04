package br.com.fiap.banksolution.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TipoCadastroValidator.class)
public @interface TipoCadastro {

    String message() default "{cadastro.tipo.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
