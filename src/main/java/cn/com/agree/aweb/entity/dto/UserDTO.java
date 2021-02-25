package cn.com.agree.aweb.entity.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDTO implements Serializable {

  private static final long serialVersionUID = -8497426658515313318L;

  private String id;

  private String name;

  private String nickname;

  private String password;

  private String status;

  private String email;

  private String phone;

}
