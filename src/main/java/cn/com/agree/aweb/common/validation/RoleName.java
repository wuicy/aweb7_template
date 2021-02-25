package cn.com.agree.aweb.common.validation;


import cn.com.agree.aweb.common.validation.validator.RoleNameValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleNameValidator.class)
public @interface RoleName {

  String message() default "不是正确的角色名称（ROLE_开头，只包含大写字母和下划线）";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
