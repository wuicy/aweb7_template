package cn.com.agree.aweb.common.util;

import cn.com.agree.aweb.entity.DefaultUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

  public static String getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication!=null&&authentication.getPrincipal() instanceof DefaultUserDetails) {
      return ((DefaultUserDetails) authentication
          .getPrincipal()).getUser().getId();
    } else {
      return null;
    }
  }

  public static String getCurrentUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication!=null&&authentication.getPrincipal() instanceof DefaultUserDetails) {
      return authentication.getName();
    }else{
//      return SecurityContextHolder.getContext().getAuthentication().getName();
      return null;
    }
  }

  // 是否客户端模式
//    public static boolean isClientOnly () {
//        OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
//        return (boolean)Optional.ofNullable(authentication).map(OAuth2Authentication::getUserAuthentication).map(
//            Authentication::getDetails).map(Map.class::cast).map(item -> item.get("clientOnly")).orElseThrow(() -> new SecurityException("用户未登录"));
//    }
}