package cn.com.agree.aweb;

import cn.com.agree.aweb.common.crypto.SM3Util;
import java.io.UnsupportedEncodingException;
import org.junit.Test;

public class SM3UtilTest {

  @Test
  public void test() throws UnsupportedEncodingException {
    //普通加密
    String encryptedString1=SM3Util.encrypt("string");
    //普通验证
    SM3Util.verifyString("string",encryptedString1);

    //自定义密钥加密
    String encryptedString2=SM3Util.encrypt("string","key");
   //自定义密钥验证
    SM3Util.verifyString("string","key",encryptedString2);
  }
}
