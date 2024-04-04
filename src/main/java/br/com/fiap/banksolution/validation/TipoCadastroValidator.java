package br.com.fiap.banksolution.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TipoCadastroValidator implements ConstraintValidator<TipoCadastro, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("NOME") || value.equals("NOME E SOBRENOME");
    }
    
}
