package cn.com.agree.aweb.validation.controller;

import cn.com.agree.aweb.common.util.ValidationUtil;
import cn.com.agree.aweb.common.validation.EnglishName;
import cn.com.agree.aweb.validation.query.ValidationQuery;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated//在此类中校验方法返回值和方法中平铺的参数需要添加此注解
public class ValidationController {

  //校验请求方法参数中Bean的属性
  @PostMapping("/testValid/bean1")
  public Object validBean1(@Valid @RequestBody ValidationQuery queryBean) {
    return queryBean;
  }

  //通过工具类主动校验Bean的属性
  @PostMapping("/testValid/bean2")
  public Object validBean2(@RequestBody ValidationQuery queryBean) {
    ValidationUtil.fastFailValidate(queryBean);
    //ValidationUtil.allCheckValidate(queryBean);
    return queryBean;
  }

  //校验方法中平铺的参数
  @GetMapping("/testValid/param")
  public Object validParam(@EnglishName @RequestParam String param) {
    return param;
  }

  //校验方法返回值
  @GetMapping("/testValid/method")
  @EnglishName
  public String validMethod(String name) {
    return name;
  }

}
