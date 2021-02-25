package cn.com.agree.aweb.common.validation.validator;

import cn.com.agree.aweb.common.validation.PhoneNumber;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

  private String regex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

    if (value != null && value.length() != 0) {
      return value.matches(regex);
    } else {
      return true;
    }

  }

}
