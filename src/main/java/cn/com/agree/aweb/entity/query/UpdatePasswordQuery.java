package cn.com.agree.aweb.entity.query;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordQuery {

  @NotBlank(message = "旧密码不能为空")
  private String oldPassword;

  @NotBlank(message = "新密码不能为空")
  private String password1;

  @NotBlank(message = "确认密码不能为空")
  private String password2;

}
