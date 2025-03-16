package io.github.raphael_ps.vendas.validation;

import io.github.raphael_ps.vendas.validation.constraintValidator.NotEmptySetValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptySetValidator.class)
public @interface NotEmptySet {
    String message() default "A lista não pode ser vazia.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
