package cn.com.agree.aweb.common.validation.validator;

import cn.com.agree.aweb.common.validation.IDCardNumber;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IDCardNumberValidator implements ConstraintValidator<IDCardNumber, String> {

  private String regex = "( ^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

    if (value != null && value.length() != 0) {
      return value.matches(regex);
    } else {
      return true;
    }

  }

}
