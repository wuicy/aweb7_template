package cn.com.agree.aweb.controller;

import cn.com.agree.aweb.common.base.dao.Range;
import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.common.crypto.DES3Util;
import cn.com.agree.aweb.entity.enmus.UserStatus;
import cn.com.agree.aweb.entity.mapper.UserMapper;
import cn.com.agree.aweb.entity.po.RolePO;
import cn.com.agree.aweb.entity.po.UserPO;
import cn.com.agree.aweb.entity.query.QueryEntity;
import cn.com.agree.aweb.entity.vo.UserVO;
import cn.com.agree.aweb.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "用户管理")
@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('userManagement')")
public class UserController {

  @Autowired
  UserService userService;

  UserMapper mapper = UserMapper.INSTANCE;

  @Autowired
  PasswordEncoder passwordEncoder;

  @ApiOperation(value = "列出所有用户", notes = "查找并以list形式列出所有用户")
  @GetMapping("/list")
  public List<UserVO> list() {
    return mapper.poToVO(userService.findAll());
  }

  @ApiOperation(value = "根据条件排序分页列出所有用户（模糊查询）", notes = "对类型为字符串的属性进行模糊查询，排序分页列出所有用户")
  @PostMapping("/list")
  public Page<UserVO> listByQuery(@RequestBody QueryEntity<UserVO> query) {
    ExampleMatcher matcher = ExampleMatcher.matching()
        .withStringMatcher(StringMatcher.CONTAINING)
        .withIgnoreCase().withIgnorePaths("password");
    Example<UserPO> example = Example.of(mapper.voToPO(query.getQuery()), matcher);
    List<Range> ranges = query.getRanges();
    Pageable page = query.getPage();
    return userService.findAll(example, ranges, page, mapper);
  }

  @ApiOperation(value = "根据条件排序分页列出所有用户（匹配查询）", notes = "类型为字符串的属性进行匹配查询，单个字段模糊匹配即可，排序分页列出所有用户")
  @PostMapping("/list/matching")
  public Page<UserVO> listByString(@RequestBody QueryEntity<String> queryEntity)
      throws IllegalAccessException, InstantiationException {
    return userService.findByString(UserPO.class, queryEntity, mapper);
  }


  @ApiOperation(value = "获取单个用户详情", notes = "根据id获取用户详情")
  @GetMapping("/get/{id}")
  public UserVO get(@PathVariable("id") String id) {
    return mapper.poToVO(userService.findById(id).orElse(null));
  }

  @ApiOperation(value = "添加用户", notes = "添加单个用户")
  @PostMapping("/add")
  public UserVO add(@RequestBody @Valid UserVO vo) throws IOException {
    //设置初始密码为123456
    vo.setPassword(passwordEncoder.encode("123456"));
    //设置为新角色
    vo.setStatus(UserStatus.AVAILABLE.getCode());
    return mapper.poToVO(userService.add(mapper.voToPO(vo)));
  }

  @ApiOperation(value = "修改用户", notes = "修改单个用户的相关信息")
  @PostMapping("/update")
  public UserVO update(@RequestBody @Valid UserVO vo)
      throws GeneralSecurityException, UnsupportedEncodingException {
    vo.setRoles(null);
    vo.setName(null);//不可修改
    if (vo.getPassword() != null) {
      vo.setPassword(passwordEncoder.encode(DES3Util.decode(vo.getPassword())));
    }
    return mapper.poToVO(userService.update(mapper.voToPO(vo), vo.getId()));
  }

  @ApiOperation(value = "关联角色", notes = "对单个用户和任意权限进行关联，id为用户id，ids为角色id集合")
  @PostMapping("/update/role/{id}")
  public UserVO updateRole(@PathVariable("id") String id,
      @RequestBody(required = false) List<String> ids) {
    UserPO userPO = new UserPO().setId(id).setRoles(new ArrayList<>());
    for (String roleId : ids) {
      RolePO rolePO = new RolePO();
      rolePO.setId(roleId);
      userPO.getRoles().add(rolePO);
    }
    return mapper.poToVO(userService.update(userPO, id));
  }

  @ApiOperation(value = "删除用户", notes = "根据id删除单个或多个用户")
  @PostMapping("/delete")
  public RespMessage delete(@RequestBody List<String> ids) {
    userService.delete(ids);
    return RespMessage.ok();
  }


}
