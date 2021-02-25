package cn.com.agree.aweb;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class TestAuthController {

  @GetMapping("/auth/test1")//此请求方法会用到类上的鉴权
  public Object hasRole() {
    return "hasRole";
  }

  @GetMapping("/auth/test2")
  @PreAuthorize("hasAuthority('userManagement')")
  public Object hasAuthority() {
    return "hasAuthorize";
  }

}
