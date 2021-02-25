package cn.com.agree.aweb.common.validation;


import cn.com.agree.aweb.common.validation.validator.LegalNameValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LegalNameValidator.class)
public @interface LegalName {

  String message() default "该名称合法格式为以字母开头，只能包含字母、数字和下划线";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
