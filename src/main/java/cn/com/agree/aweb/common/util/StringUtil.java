package cn.com.agree.aweb.common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串操作工具类
 *
 * @author fanzhendong
 * @version 1.0
 * @date 2018年11月6日 上午9:36:17
 */
public class StringUtil {

  private static final int INDEX_NOT_FOUND = -1;
  private static final String LEFT = "LEFT";
  private static final String RIGHT = "RIGHT";
  private static final String BOTH = "BOTH";

  private StringUtil() {
  }

  /**
   * 判断字符串是否为null或者""
   */
  public static boolean isBlank(String str) {
    return str == null || str.length() == 0;
  }

  /**
   * 计算子字符串的重复次数
   */
  public static int countStr(String inStr, String searchStr) {
    if (isBlank(inStr) || isBlank(searchStr)) {
      return 0;
    }

    int count = 0;
    int index = INDEX_NOT_FOUND;
    while ((index = inStr.indexOf(searchStr)) != INDEX_NOT_FOUND) {
      count++;
      inStr = inStr.substring(index + searchStr.length());
    }
    return count;
  }

  /**
   * 字符串补足
   *
   * @param inStr 需要补足的字符串
   * @param len 补足后字符串长度
   * @param direct 补足位置，可选left，right，both
   * @param fillChar 补足字符
   */
  public static String fillStr(String inStr, int len, String direct, char fillChar) {
    if (inStr == null) {
      throw new NullPointerException("需要填充的字符串有误，不能为null");
    }

    if (direct == null) {
      throw new NullPointerException("补足位置参数有误，不能为null");
    }

    int padLen = len - inStr.length();
    if (padLen < 0) {
      throw new IllegalArgumentException("补足最终长度非法，小于原字符串长度");
    }

    String fillStr = inStr;
    if (direct.equalsIgnoreCase(LEFT)) {
      fillStr = leftFill(fillStr, padLen, fillChar);
    } else if (direct.equalsIgnoreCase(RIGHT)) {
      fillStr = rightFill(fillStr, padLen, fillChar);
    } else if (direct.equalsIgnoreCase(BOTH)) {
      fillStr = bothFill(fillStr, padLen, fillChar);
    } else {
      throw new IllegalArgumentException(
          "补足位置参数类型不合法：（" + direct + "）；可选参数有：LEFT、RIGHT、BOTH");
    }

    return fillStr;
  }

  private static String repeat(final char ch, final int count) {
    final char[] buf = new char[count];
    for (int i = 0; i < count; i++) {
      buf[i] = ch;
    }
    return new String(buf);
  }

  private static String leftFill(String text, int padLen, char ch) {
    if (padLen < 0) {
      throw new IllegalArgumentException("追加长度小于0:" + padLen);
    }
    String repeatChar = repeat(ch, padLen);
    return repeatChar.concat(text);
  }

  private static String rightFill(String text, int padLen, char ch) {
    if (padLen < 0) {
      throw new IllegalArgumentException("追加长度小于0:" + padLen);
    }
    String repeatChar = repeat(ch, padLen);
    return text.concat(repeatChar);
  }

  private static String bothFill(String text, int padLen, char ch) {
    if (padLen < 0) {
      throw new IllegalArgumentException("追加长度小于0:" + padLen);
    }
    int half = padLen / 2;
    if (padLen % 2 == 0) {
      String eachSide = repeat(ch, half);
      return eachSide.concat(text).concat(eachSide);
    } else {
      String leftSide = repeat(ch, half);
      String rightSide = repeat(ch, half + 1);
      return leftSide.concat(text).concat(rightSide);
    }
  }

  /**
   * 将指定的子串替换为指定的另外子串， 可指定最大替换次数，-1时表示全部替换
   *
   * @param inStr 源字符串
   * @param old 被替换的子串
   * @param replacement 替换的子串
   * @param count 最大替换次数
   */
  public static String replaceStr(String inStr, String old, String replacement,
      int count) {
    if (isBlank(inStr) || isBlank(old) || replacement == null || count == 0) {
      return inStr;
    }

    if (count < 0) {
      if (count == -1) {
        return inStr.replaceAll(old, replacement);
      } else {
        throw new IllegalArgumentException("最大替换次数参数大小错误：" + count);
      }
    } else {
      return replace(inStr, old, replacement, count);
    }
  }

  /**
   * 将指定的子串替换为指定的另外子串， 可指定最大替换次数，-1时表示全部替换； 可指定从第几个匹配子串开始匹配，负数表示从后面往前匹配。
   *
   * @param inStr 源字符串
   * @param old 被替换的子串
   * @param replacement 替换的子串
   * @param count 最大替换次数
   * @param start 开始替换位置，整数表示从前往后第n个子串开始替换，负数表示从后往前第n个子串开始替换，0时自动转为1
   */
  public static String replaceStr(String inStr, String old, String replacement,
      int count, int start) {
    if (isBlank(inStr) || isBlank(old) || replacement == null || count == 0
        || Math.abs(start) > countStr(inStr, old)) {
      return inStr;
    }

    String result = null;

    if (count < 0) {
      if (count == -1) {
        return inStr.replaceAll(old, replacement);
      } else {
        throw new IllegalArgumentException("最大替换次数参数大小错误：" + count);
      }
    } else {
      if (start >= 0) {
        start = start == 0 ? 1 : start;
        int startIndex = 0;
        for (int i = 0; i < start - 1; i++) {
          int temp = inStr.substring(startIndex).indexOf(old);
          if (temp == INDEX_NOT_FOUND) {
            break;
          } else {
            startIndex += inStr.substring(startIndex).indexOf(old)
                + old.length();
          }
        }

        String replaceStr = replace(inStr.substring(startIndex), old, replacement,
            count);
        result = inStr.substring(0, startIndex).concat(replaceStr);
      } else if (start < 0) {
        int endIndex = inStr.length();

        if (inStr.indexOf(old) != INDEX_NOT_FOUND) {
          for (int i = 0; i < Math.abs(start); i++) {
            endIndex = inStr.lastIndexOf(old, endIndex - old.length());
          }
          endIndex += old.length();
        }

        int startIndex = endIndex;
        for (int j = 0; j < count; j++) {
          int temp = inStr.substring(0, startIndex).lastIndexOf(old);
          if (temp == INDEX_NOT_FOUND) {
            break;
          } else {
            startIndex = inStr.substring(0, startIndex).lastIndexOf(old);
          }
        }

        String replaceStr = replace(inStr.substring(startIndex, endIndex), old,
            replacement, -1);
        result = inStr.substring(0, startIndex).concat(replaceStr)
            .concat(inStr.substring(endIndex));
      }
      return result;
    }
  }

  private static String replace(final String source, final String searchString,
      final String replacement, int max) {

    int start = 0;
    int end = source.indexOf(searchString, start);

    if (end == INDEX_NOT_FOUND) {
      return source;
    }

    final int replLength = searchString.length();
    int increase = replacement.length() - replLength;
    increase = increase < 0 ? 0 : increase;
    increase *= max < 0 ? 16 : max > 64 ? 64 : max;
    final StringBuilder buf = new StringBuilder(source.length() + increase);

    while (end != INDEX_NOT_FOUND) {
      buf.append(source.substring(start, end)).append(replacement);
      start = end + replLength;
      if (--max == 0) {
        break;
      }
      end = source.indexOf(searchString, start);
    }

    buf.append(source.substring(start));
    return buf.toString();
  }

  /**
   * 根据分割标识符分割字符串
   *
   * @param inStr 需要分割的字符串
   * @param separator 分割标识
   */
  public static List<String> split(String inStr, String separator) {
    if (inStr == null) {
      throw new NullPointerException("输入字符串参数有误，不能为null");
    }

    if (separator == null) {
      throw new NullPointerException("分割表示字符参数为null");
    }

    List<String> resultList = new ArrayList<>();
    String[] strArrs = inStr.split(separator);
    for (int i = 0; i < strArrs.length; i++) {
      resultList.add(strArrs[i]);
    }
    return resultList;

  }

  /**
   * 根据分割间隔，每n个字符进行分割
   *
   * @param inStr 需要分割的字符串
   * @param step 分割间隔
   */
  public static List split(String inStr, int step) {
    List<String> resultList = new ArrayList();
    final int size = inStr.length();
    for (int i = 0; i < size; i += step) {
      int nextIndex = i + step;
      if (nextIndex >= size) {
        resultList.add(inStr.substring(i));
      } else {
        resultList.add(inStr.substring(i, i + step));
      }
    }
    return resultList;
  }

  /**
   * 将输入的字符串连接为一个长串
   *
   * @param strList 字符串List
   * @param separator 分隔符
   */
  public static String joinStr(List<String> strList, String separator) {
    if (strList == null) {
      throw new NullPointerException("输入字符串列表参数有误，不能为null");
    }

    if (separator == null) {
      throw new NullPointerException("分割标识字符参数有误，不能为null");
    }

    final StringBuilder buf = new StringBuilder();
    for (int i = 0; i < strList.size(); i++) {
      if (i > 0) {
        buf.append(separator);
      }
      if (strList.get(i) != null) {
        buf.append(strList.get(i));
      }
    }

    return buf.toString();
  }

  /**
   * 使用分隔符和修饰符格式化创建字符串
   *
   * @param strList 字符串List
   * @param separator 分隔符
   * @param pre 前缀
   * @param post 后缀
   */
  public static String createStr(List<String> strList, String separator, String pre,
      String post) {
    if (strList == null) {
      throw new NullPointerException("输入字符串列表参数有误，不能为null");
    }
    if (separator == null) {
      throw new NullPointerException("分隔标识字符参数有误，不能为null");
    }
    if (pre == null) {
      throw new NullPointerException("前置修饰符参数有误，不能为null");
    }
    if (post == null) {
      throw new NullPointerException("后置修饰符参数有误，不能为null");
    }

    List<String> tempList = new ArrayList();
    for (int i = 0; i < strList.size(); i++) {
      String beforeStr = strList.get(i);
      String afterStr = pre + beforeStr + post;
      tempList.add(afterStr);
    }

    return joinStr(tempList, separator);

  }

  /**
   * 查找字符串中的子串位置，可指定开始下标
   *
   * @param substr 被查找子串
   * @param start 开始查找的索引
   */
  public static List<Integer> findStr(String inStr, String substr, int start) {
    if (inStr == null) {
      throw new NullPointerException("被查找字符串参数有误，不能为null");
    }

    if (substr == null) {
      throw new NullPointerException("子字符串参数有误，不能为null");
    }

    List<Integer> positionList = new ArrayList();
    while (inStr.indexOf(substr, start) != INDEX_NOT_FOUND) {
      int position = inStr.indexOf(substr, start);
      positionList.add(position);
      start = position + substr.length();
    }

    return positionList;
  }

  /**
   * 将输入的字符串按照指定类型编码进行转换，编码类型有误时返回null
   *
   * @param inStr 输入字符串
   * @param decodeType 目标编码
   * @param originalCodeType 源编码
   */
  public static String convertCoding(String inStr, String decodeType,
      String originalCodeType) throws UnsupportedEncodingException {
    if (inStr == null) {
      throw new NullPointerException("输入字符串参数有误，不能为null");
    }

    if (decodeType == null) {
      throw new NullPointerException("转换编码类型参数有误，不能为null");
    }

    if (originalCodeType == null) {
      throw new NullPointerException("原编码类型参数有误，不能为null");
    }

    byte[] bytes = inStr.getBytes(originalCodeType);
    return new String(bytes, decodeType);
  }

  /**
   * 判断输入的字符串里面是否有半角字符串
   *
   * @param inStr 需要判断的字符串
   */
  public static boolean haveHalfStr(String inStr) {
    if (inStr == null) {
      throw new NullPointerException("输入字符串参数有误，不能为null");
    }
    final int size = inStr.length();
    for (int i = 0; i < size; i++) {
      int charValue = (int) inStr.charAt(i);
      if (charValue >= 32 && charValue <= 126) {
        return true;
      }
    }
    return false;
  }

  /**
   * 字符串全角转半角，半角字符原样返回
   *
   * @param inStr 需要转换的字符串
   */
  public static String SBC2DBC(String inStr) {
    if (inStr == null) {
      throw new NullPointerException("输入字符串参数有误，不能为null");
    }

    char[] chars = inStr.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '\u3000') {
        chars[i] = ' ';
      } else if (chars[i] > '\uFF00' && chars[i] < '\uFF5F') {
        chars[i] = (char) (chars[i] - 65248);
      }
    }
    return new String(chars);
  }

  /**
   * 字符串半角转全角，全角字符原样返回
   *
   * @param inStr 需要转换的字符串
   */
  public static String DBC2SBC(String inStr) {
    if (inStr == null) {
      throw new NullPointerException("输入字符串参数有误，不能为null");
    }

    char[] chars = inStr.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == ' ') {
        chars[i] = '\u3000';
      } else if (chars[i] < '\177') {
        chars[i] = (char) (chars[i] + 65248);
      }
    }
    return new String(chars);
  }

  /**
   * 首字母变大写
   *
   * @param inStr 需要转换的字符串
   */
  public static String capitalize(String inStr) {
    if (isBlank(inStr)) {
      return inStr;
    }

    if (Character.isUpperCase(inStr.charAt(0))) {
      return inStr;
    } else {
      return new StringBuilder().append(Character.toUpperCase(inStr.charAt(0)))
          .append(inStr.substring(1)).toString();
    }
  }

  /**
   * 首字母变小写
   *
   * @param inStr 需要转换的字符串
   */
  public static String uncapitalize(String inStr) {
    if (isBlank(inStr)) {
      return inStr;
    }

    if (Character.isLowerCase(inStr.charAt(0))) {
      return inStr;
    } else {
      return new StringBuilder().append(Character.toLowerCase(inStr.charAt(0)))
          .append(inStr.substring(1)).toString();
    }
  }

  /**
   * 英文字母全部转换为大写
   *
   * @param inStr 需要转换的字符串
   */
  public static String toUpperStr(String inStr) {
    if (isBlank(inStr)) {
      return inStr;
    } else {
      return inStr.toUpperCase();
    }
  }

  /**
   * 英文字母全部转换为小写
   *
   * @param inStr 需要转换的字符串
   */
  public static String toLowerStr(String inStr) {
    if (isBlank(inStr)) {
      return inStr;
    } else {
      return inStr.toLowerCase();
    }
  }

  /**
   * 下划线转驼峰式
   *
   * @param inStr 需要转换的字符串
   */
  public static String camelCase(String inStr) {
    if (isBlank(inStr)) {
      return inStr;
    }
    inStr = inStr.toLowerCase();

    String[] strArray = (String[]) split(inStr, "_").toArray(new String[0]);
    StringBuilder buf = new StringBuilder(inStr.length());
    for (int i = 0; i < strArray.length; i++) {
      if (i == 0) {
        buf.append(strArray[i]);
      } else {
        buf.append(capitalize(strArray[i]));
      }
    }

    return buf.toString();
  }

  /**
   * 下划线转驼峰式，首字母大写
   *
   * @param inStr 需要转换的字符串
   */
  public static String capCamelCase(String inStr) {
    if (isBlank(inStr)) {
      return inStr;
    }

    return capitalize(camelCase(inStr));
  }

  /**
   * 驼峰式转下划线
   *
   * @param inStr 需要转换的字符串
   */
  public static String underlineCase(String inStr) {
    if (isBlank(inStr)) {
      return inStr;
    }

    StringBuilder buf = new StringBuilder(inStr);
    int temp = 0;// 定位
    for (int i = 0; i < inStr.length(); i++) {
      if (Character.isUpperCase(inStr.charAt(i))) {
        buf.insert(i + temp, "_");
        temp += 1;
      }
    }

    return buf.toString().toLowerCase();
  }

}
