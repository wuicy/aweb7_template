package cn.com.agree.aweb.common.validation.validator;

import cn.com.agree.aweb.common.validation.RoleName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleNameValidator implements ConstraintValidator<RoleName, String> {

  private String regex = "^ROLE_[A-Z_]+$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

    if (value != null && value.length() != 0) {
      return value.matches(regex);
    } else {
      return true;
    }

  }

}
