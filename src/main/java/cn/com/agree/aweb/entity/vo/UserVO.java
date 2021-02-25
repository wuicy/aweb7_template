package cn.com.agree.aweb.entity.vo;

import cn.com.agree.aweb.common.base.entity.BaseVO;
import cn.com.agree.aweb.common.validation.LegalName;
import cn.com.agree.aweb.common.validation.PhoneNumber;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.Email;
import lombok.Data;

@Data
@ApiModel(value = "UserVO", description = "用户VO")
public class UserVO extends BaseVO {

  @ApiModelProperty(value = "唯一id")
  private String id;
  @ApiModelProperty(value = "用户登录名称，不可修改；合法格式：以字母开头，只能包含字母、数字和下划线")
  @LegalName
  private String name;
  @ApiModelProperty(value = "密码，后台存入前需要加密")
  private String password;
  @ApiModelProperty(value = "用户名称")
  private String nickname;
  @ApiModelProperty(value = "e-mail")
  @Email
  private String email;
  @ApiModelProperty(value = "电话号码")
  @PhoneNumber
  private String phone;
  @ApiModelProperty(value = "用户状态，0：新用户，1：可用，2：锁定")
  private String status;
  @ApiModelProperty(value = "用户描述")
  private String desc;
  @ApiModelProperty(value = "用户所属角色，展示用属性", hidden = true)
  private List<RoleVO> roles;

//  public void setPassword(String password) {
//    PasswordEncoder passwordEncoder = SpringContextUtil.getBean(PasswordEncoder.class);
//    if (password != null) {
//      this.password = passwordEncoder.encode(password);
//    }
//  }
}
