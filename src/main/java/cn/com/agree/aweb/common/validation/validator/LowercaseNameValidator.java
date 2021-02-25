package cn.com.agree.aweb.common.validation.validator;

import cn.com.agree.aweb.common.validation.LowercaseName;
import cn.com.agree.aweb.common.validation.UppercaseName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LowercaseNameValidator implements ConstraintValidator<LowercaseName, String> {

  private String regex = "^[a-z]+$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

    if (value != null && value.length() != 0) {
      return value.matches(regex);
    } else {
      return true;
    }

  }

}
