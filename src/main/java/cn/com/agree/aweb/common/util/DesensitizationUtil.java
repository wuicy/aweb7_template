package cn.com.agree.aweb.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * 脱敏工具类
 */
public class DesensitizationUtil {

  /**
   * 只显示左边index位
   * @param str
   * @param index
   * @return
   */
  public static String left(String str, int index) {
    return left(str, index, '*');
  }

  /**
   * 只显示左边index位,可指定填充字符
   * @param str
   * @param index
   * @param padChar
   * @return
   */
  public static String left(String str, int index, char padChar) {
    if (StringUtils.isBlank(str)) {
      return "";
    }
    return StringUtils.rightPad(StringUtils.left(str, index), StringUtils.length(str), padChar);
  }

  /**
   * 只显示右边index位
   * @param str
   * @param index
   * @return
   */
  public static String right(String str, int index) {
    return right(str, index, '*');
  }

  /**
   * 只显示右边index位，可指定填充字符
   * @param str
   * @param index
   * @param padChar
   * @return
   */
  public static String right(String str, int index, char padChar) {
    if (StringUtils.isBlank(str)) {
      return "";
    }
    return StringUtils.leftPad(StringUtils.right(str, index), StringUtils.length(str), padChar);
  }

  /**
   * 只显示左left位，右边right位
   * @param str
   * @param left
   * @param right
   * @return
   */
  public static String around(String str, int left, int right) {
    return around(str, left, right, '*');
  }

  /**
   * 只显示左left位，右边right位，可指定填充字符
   * @param str
   * @param left
   * @param right
   * @param padChar
   * @return
   */
  public static String around(String str, int left, int right, char padChar) {
    if (StringUtils.isBlank(str)) {
      return "";
    }
    return StringUtils.left(str, left)
        .concat(str.substring(left, str.length() - right).replaceAll(".", String.valueOf(padChar)))
        .concat(StringUtils.right(str, right));
  }

}
