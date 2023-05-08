package oz1.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: ykk
 * @date: 2023/4/3 15:11
 * @version: 1.0
 */
public class MD5util {
  public static String code(String str) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(str.getBytes());
      byte[] byteDigest = md.digest();
      int i;
      StringBuffer buffer = new StringBuffer("");
      for (int offset = 0; offset < byteDigest.length; offset++) {
        i = byteDigest[offset];
        if (i < 0) {
          i += 256;
        }
        if (i < 16) {
          buffer.append("0");
        }
        buffer.append(Integer.toHexString(i));
      }

      // 32位加密
      return buffer.toString();
      // 16位加密
      // return buffer.toString().substring(8,24);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args) {
    System.out.println(code("111111"));
  }
}
