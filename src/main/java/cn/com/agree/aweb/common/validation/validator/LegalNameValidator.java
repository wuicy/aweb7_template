package cn.com.agree.aweb.common.validation.validator;

import cn.com.agree.aweb.common.validation.LegalName;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LegalNameValidator implements ConstraintValidator<LegalName, String> {

  private String regex = "^[a-zA-Z][a-zA-Z0-9_]+$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (value != null && value.length() != 0) {
      return value.matches(regex);
    } else {
      return true;
    }
  }

}
