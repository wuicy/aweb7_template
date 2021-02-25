package cn.com.agree.aweb.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtil {

  /**
   * 全局删除id标示
   */
  public static String GLOB_DELETE_ID_VAL = "globDeleteIdVal";

  /**
   * 获取request
   */
  public static HttpServletRequest getRequest() {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    return requestAttributes == null ? null : requestAttributes.getRequest();
  }

  /**
   * 获取session
   */
  public static HttpSession getSession() {
    return getRequest().getSession(false);
  }

  /**
   * 获取真实路径
   */
  public static String getRealRootPath() {
    return getRequest().getServletContext().getRealPath("/");
  }

  /**
   * 获取ip
   */
  public static String getIp() {
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    if (servletRequestAttributes != null) {
      HttpServletRequest request = servletRequestAttributes.getRequest();
      return request.getRemoteAddr();
    }
    return null;
  }

  /**
   * 获取session中的Attribute
   */
  public static Object getSessionAttribute(String name) {
    HttpServletRequest request = getRequest();
    return request == null ? null : request.getSession().getAttribute(name);
  }

  /**
   * 设置session的Attribute
   */
  public static void setSessionAttribute(String name, Object value) {
    HttpServletRequest request = getRequest();
    if (request != null) {
      request.getSession().setAttribute(name, value);
    }
  }

  /**
   * 获取request中的Attribute
   */
  public static Object getRequestAttribute(String name) {
    HttpServletRequest request = getRequest();
    return request == null ? null : request.getAttribute(name);
  }

  /**
   * 设置request的Attribute
   */
  public static void setRequestAttribute(String name, Object value) {
    HttpServletRequest request = getRequest();
    if (request != null) {
      request.setAttribute(name, value);
    }
  }

  /**
   * 获取上下文path
   */
  public static String getContextPath() {
    return getRequest().getContextPath();
  }

  /**
   * 获取 servlet path
   * @return ServletPath
   */
  public static String getServletPath() {
    HttpServletRequest request = getRequest();
    String servletPath = request.getServletPath();
    String requestURI = null;
    if (StringUtil.isBlank(servletPath) && !StringUtil.isBlank((requestURI = getRequest().getRequestURI()))) {
      servletPath = requestURI.replace(getContextPath(), "");
    }
    return servletPath;
  }

  /**
   * 删除session中的Attribute
   */
  public static void removeSessionAttribute(String name) {
    getRequest().getSession().removeAttribute(name);
  }

}
