package cn.com.agree.aweb.common.validation.validator;

import cn.com.agree.aweb.common.validation.EnglishName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnglishNameValidator implements ConstraintValidator<EnglishName, String> {

  private String regex = "^[A-Za-z]+$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (value != null && value.length() != 0) {
      return value.matches(regex);
    } else {
      return true;
    }
  }

}
