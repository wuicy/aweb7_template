package cn.com.agree.aweb.common.security;

import cn.com.agree.aweb.common.crypto.DES3Util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class PasswordDecodeRequestWrapper extends HttpServletRequestWrapper {

    public PasswordDecodeRequestWrapper(HttpServletRequest request) {
        super(request);
    }

  @Override
  public String getParameter(String name) {
    String value = super.getParameter(name);
    if ("password".equals(name)) {
      try {
        return DES3Util.decode(value);
      } catch (Exception e) {
          e.printStackTrace();
          return "";
      }
    }
    return value;
  }
}
