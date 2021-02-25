package cn.com.agree.aweb.common.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import org.hibernate.validator.HibernateValidator;

public class ValidationUtil {

  /**
   * 开启快速结束模式 failFast (true)
   */
  private static Validator failFastValidator = Validation.byProvider(HibernateValidator.class)
      .configure()
      .failFast(true)
      .buildValidatorFactory().getValidator();

  /**
   * 全部校验
   */
  private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  private ValidationUtil() {
  }

  /**
   * 注解验证参数(快速失败模式，有校验错误马上停止校验)
   */
  public static <T> void fastFailValidate(T obj) {
    Set<ConstraintViolation<T>> constraintViolations = failFastValidator.validate(obj);
    //返回异常result
    if (constraintViolations.size() > 0) {
      throw new ValidationException(
          constraintViolations.iterator().next().getPropertyPath().toString() + ":"
              + constraintViolations.iterator().next().getMessage());
    }
  }

  /**
   * 注解验证参数(全部校验模式，有校验错误也会校验所有参数)
   */
  public static <T> void allCheckValidate(T obj) {
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
    //返回异常result
    if (constraintViolations.size() > 0) {
      List<String> errorMessages = new LinkedList<>();
      Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
      while (iterator.hasNext()) {
        ConstraintViolation<T> violation = iterator.next();
        errorMessages.add(
            String.format("%s:%s", violation.getPropertyPath().toString(), violation.getMessage()));
      }
      throw new ValidationException(errorMessages.toString());
    }
  }

  /**
   * 注解验证参数(全部校验) 并设定错误行的序号
   * 该方法一般用于监听器ExcelUploadListener中
   */
  public static <T> void allCheckValidate(T obj, Integer line) {
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
    //返回异常result
    if (constraintViolations.size() > 0) {
      List<String> errorMessages = new LinkedList<>();
      errorMessages.add("Error at line " + line);
      Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
      while (iterator.hasNext()) {
        ConstraintViolation<T> violation = iterator.next();
        errorMessages.add(
            String.format("%s:%s", violation.getPropertyPath().toString(), violation.getMessage()));
      }
      throw new ValidationException(errorMessages.toString());
    }
  }
}
