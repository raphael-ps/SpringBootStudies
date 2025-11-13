package io.github.raphael_ps.vendas.validation.constraintValidator;

import io.github.raphael_ps.vendas.validation.NotEmptySet;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class NotEmptySetValidator implements ConstraintValidator<NotEmptySet, Set> {
    @Override
    public void initialize(NotEmptySet constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Set value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty();
    }
}
