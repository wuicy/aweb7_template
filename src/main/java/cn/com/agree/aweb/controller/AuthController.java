package cn.com.agree.aweb.controller;

import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.common.util.AuthUtils;
import cn.com.agree.aweb.entity.DefaultUserDetails;
import cn.com.agree.aweb.entity.mapper.UserMapper;
import cn.com.agree.aweb.entity.po.UserPO;
import cn.com.agree.aweb.entity.query.UpdatePasswordQuery;
import cn.com.agree.aweb.entity.vo.UserVO;
import cn.com.agree.aweb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "用户相关功能")
@RestController
public class AuthController {

  @Autowired
  UserService userService;

  @Autowired
  PasswordEncoder passwordEncoder;

  UserMapper mapper = UserMapper.INSTANCE;

  @ApiOperation("用户注册")
  @PostMapping("/register")
  public RespMessage register(@RequestBody @Valid UserVO vo) {
    vo.setStatus("1");
    vo.setPassword(passwordEncoder.encode(vo.getPassword()));
    userService.add((mapper.voToPO(vo)));
    return RespMessage.ok();
  }

  @ApiOperation("用户修改信息")
  @PostMapping("/updateUserInfo")
  public RespMessage update(@RequestBody @Valid UserVO vo) {
    vo.setPassword(null);
    if (vo.getId().equals(AuthUtils.getCurrentUserId())) {
      userService.update((mapper.voToPO(vo)), vo.getId());
      return RespMessage.ok();
    } else {
      return RespMessage.build(false, HttpServletResponse.SC_FORBIDDEN, "没有权限修改他人信息");
    }
  }

  @ApiOperation("用户修改密码")
  @PostMapping("/updateUserPassword")
  public RespMessage update(@RequestBody @Valid UpdatePasswordQuery query,
      HttpServletRequest request,
      HttpServletResponse response)
      throws IOException {
    UserPO userPO = userService.findById(AuthUtils.getCurrentUserId()).get();
    if (!passwordEncoder.matches(query.getOldPassword(), userPO.getPassword())) {
      return RespMessage.error("旧密码输入错误");
    } else if (!query.getPassword1().equals(query.getPassword2())) {
      return RespMessage.error("两次输入密码不一样");
    } else {
      userPO.setPassword(passwordEncoder.encode(query.getPassword1()));
      userService.update(userPO, userPO.getId());
      new SecurityContextLogoutHandler()
          .logout(request, response, SecurityContextHolder.getContext().getAuthentication());
      return RespMessage.ok("修改成功，请重新登录。");
    }
  }

  @ApiOperation("获取当前登录用户信息")
  @GetMapping("/current")
  public Object current() {
    DefaultUserDetails details= (DefaultUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    details.getUser().setPassword(null);
    return details;
  }

}
