package cn.com.agree.aweb.common.validation;


import cn.com.agree.aweb.common.validation.validator.EnglishNameValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnglishNameValidator.class)
public @interface EnglishName {

  String message() default "不是全英文名称";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
