package cn.com.agree.aweb.common.validation;


import cn.com.agree.aweb.common.validation.validator.IDCardNumberValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IDCardNumberValidator.class)
public @interface IDCardNumber {

  String message() default "不是正确格式的身份证号码";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
