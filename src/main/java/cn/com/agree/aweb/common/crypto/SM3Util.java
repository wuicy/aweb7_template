package cn.com.agree.aweb.common.crypto;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Arrays;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

public class SM3Util {

  private static final String ENCODING = "UTF-8";

  private static final SM3Digest DIGEST;

  static {
    Security.addProvider(new BouncyCastleProvider());
    DIGEST = new SM3Digest();
  }

  public static String encrypt(String source) throws UnsupportedEncodingException {
    return ByteUtils.toHexString(hash(source.getBytes(ENCODING)));
  }

  public static String encrypt(String source,String key) throws UnsupportedEncodingException {
    return ByteUtils.toHexString(hmac(source.getBytes(ENCODING),key.getBytes(ENCODING)));
  }

  public static byte[] hash(byte[] source) {
    DIGEST.update(source, 0, source.length);
    byte[] target = new byte[DIGEST.getDigestSize()];
    DIGEST.doFinal(target, 0);
    return target;
  }

  public static byte[] hmac(byte[] source,byte[] key) {
    KeyParameter keyParameter=new KeyParameter(key);
    HMac mac=new HMac(DIGEST);
    mac.init(keyParameter);
    mac.update(source,0,source.length);
    byte[] target = new byte[mac.getMacSize()];
    mac.doFinal(target, 0);
    return target;
  }

  public static boolean verifyString(String source, String target) {
    if (source == null && target == null) {
      return true;
    } else if (source == null || target == null) {
      return false;
    } else {
      try {
        return target.equals(encrypt(source));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return false;
      }
    }
  }

  public static boolean verifyString(String source,String key, String target) {
    if (source == null && target == null) {
      return true;
    } else if (source == null || target == null) {
      return false;
    } else {
      try {
        return target.equals(encrypt(source,key));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        return false;
      }
    }
  }


}
