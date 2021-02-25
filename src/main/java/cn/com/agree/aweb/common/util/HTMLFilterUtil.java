package cn.com.agree.aweb.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.Map.Entry;

/**
 * 可以对JSON的字符串类型参数进行遍历并预处理
 * 处理逻辑写在cleanStr(String value)
 * 不规则的数组不处理，比如[1,"abc",{}]
 */
public class HTMLFilterUtil {

  public static Object cleanJSON(Object object) {
    //不处理不规则数组，若需要处理可以改用instanceof进行判断
    String jsonString=JSON.toJSONString(object);
    if (JSON.isValidObject(jsonString)) {
      return cleanJsonObject(JSON.parseObject(jsonString));
    } else if (JSON.isValidArray(jsonString)) {
      return cleanJsonArray(JSON.parseArray(jsonString));
    }
    return object;
  }

  private static JSONArray cleanJsonArray(JSONArray jsonArray) {
    JSONArray cleanArray = new JSONArray();
    for (Object obj : jsonArray) {
      if (obj instanceof String) {
        cleanArray.add(cleanStr((String) obj));
      } else if (obj instanceof Object) {
        cleanArray.add(cleanJSON(obj));
      } else {
        cleanArray.add(obj);
      }
    }
    return cleanArray;
  }

  private static JSONObject cleanJsonObject(JSONObject jsonObject) {
    JSONObject cleanObject = new JSONObject();
    for (Entry<String, Object> entry : jsonObject.entrySet()) {
      if (entry.getValue() instanceof String) {
        cleanObject.put(entry.getKey(), cleanStr((String) entry.getValue()));
      } else if (entry.getValue() instanceof Object) {
        cleanObject.put(entry.getKey(),cleanJSON(entry.getValue()));
      } else {
        cleanObject.put(entry.getKey(), entry.getValue());
      }
    }
    return cleanObject;
  }

  public static String cleanStr(String value) {
    String cleanString = value;
    if (value != null) {
      cleanString = new HTMLFilter().filter(value);
    }
    return cleanString;
  }

  public static String[] cleanStr(String[] values) {
    String[] cleanValues = null;
    if (values != null) {
      cleanValues = new String[values.length];
      for (int i = 0; i < values.length; i++) {
        cleanValues[i] = cleanStr(values[i]);
      }
    }
    return cleanValues;
  }

}
