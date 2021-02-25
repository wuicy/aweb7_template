package cn.com.agree.aweb.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

@Data
@Accessors(chain = true)
public class AuthorityDTO implements GrantedAuthority {

  private static final long serialVersionUID = -2451486335699696885L;

  private String name;

  private String desc;

  @Override
  public String getAuthority() {
    return this.getName();
  }

}