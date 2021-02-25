package cn.com.agree.aweb.entity;

import cn.com.agree.aweb.entity.dto.AuthorityDTO;
import cn.com.agree.aweb.entity.dto.MenuDTO;
import cn.com.agree.aweb.entity.dto.UserDTO;
import cn.com.agree.aweb.entity.enmus.UserStatus;
import cn.com.agree.aweb.entity.po.UserPO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Accessors(chain = true)
public class DefaultUserDetails implements UserDetails {

  private static final long serialVersionUID = 7595960008450636802L;

  private UserDTO user;
  private Collection<? extends GrantedAuthority> authorities;
  private Set<MenuDTO> menus;
  private JSONObject permissions;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public DefaultUserDetails(UserPO user) {
    Set authoritySet = new HashSet();
    Set menuSet = new HashSet();
    user.getRoles().forEach(rolePO -> {
      authoritySet.add(new AuthorityDTO().setName(rolePO.getName()).setDesc(rolePO.getDesc()));
      rolePO.getAuthorities().forEach(
          authorityPO -> authoritySet.add(new AuthorityDTO().setName(authorityPO.getName())
              .setDesc(authorityPO.getDesc())));
      rolePO.getMenus().forEach(menuPO -> menuSet.add(
          new MenuDTO().setId(menuPO.getId()).setParentId(menuPO.getParentId())
              .setName(menuPO.getName()).setTitle(menuPO.getTitle()).setPath(menuPO.getPath())
              .setIcon(menuPO.getIcon()).setOrder(menuPO.getOrder()).setDesc(menuPO.getDesc())
              .setDeploy(menuPO.getDeploy())));
      this.setPermissions(JSON.parseObject(rolePO.getPermissions()));
    });
    this.setAuthorities(authoritySet);
    this.setMenus(menuSet);
    this.setUser(
        new UserDTO().setId(user.getId()).setName(user.getName()).setNickname(user.getNickname())
            .setStatus(user.getStatus()).setPassword(user.getPassword()).setEmail(user.getEmail())
            .setPhone(user.getPhone()));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getName();
  }

  @Override
  public boolean isAccountNonExpired() {
    Optional<UserStatus> userStatus = UserStatus.fromCode(user.getStatus());
    return userStatus.isPresent() && userStatus.get().equals(UserStatus.AVAILABLE);
  }

  @Override
  public boolean isAccountNonLocked() {
    Optional<UserStatus> userStatus = UserStatus.fromCode(user.getStatus());
    return userStatus.isPresent() && userStatus.get().equals(UserStatus.AVAILABLE);
  }

  @Override
  public boolean isCredentialsNonExpired() {
    Optional<UserStatus> userStatus = UserStatus.fromCode(user.getStatus());
    return userStatus.isPresent() && userStatus.get().equals(UserStatus.AVAILABLE);
  }

  @Override
  public boolean isEnabled() {
    Optional<UserStatus> userStatus = UserStatus.fromCode(user.getStatus());
    return userStatus.isPresent() && userStatus.get().equals(UserStatus.AVAILABLE);
  }
}
