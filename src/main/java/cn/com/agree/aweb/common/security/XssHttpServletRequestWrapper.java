package cn.com.agree.aweb.common.security;

import cn.com.agree.aweb.common.util.HTMLFilterUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerMapping;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

  public XssHttpServletRequestWrapper(HttpServletRequest request) {
    super(request);
  }

  @Override
  public Object getAttribute(String name) {
    //过滤处理@PathVariable的参数
    if (HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE.equals(name)) {
      Map<String, String> map = (Map<String, String>) super.getAttribute(name);
      for (Entry<String, String> entry : map.entrySet()) {
        map.put(entry.getKey(), cleanXSS(entry.getValue()));
      }
    }
    return super.getAttribute(name);
  }

  @Override
  public String getParameter(String name) {
    String value = super.getParameter(name);
    return cleanXSS(value);
  }

  @Override
  public String[] getParameterValues(String name) {
    String[] values = super.getParameterValues(name);
    return cleanXSS(values);
  }

  @Override
  public Map<String, String[]> getParameterMap() {
    Map<String, String[]> values = super.getParameterMap();
    Map<String, String[]> newValues = new HashMap<>();
    if (values != null) {
      for (String key : values.keySet()) {
        String[] encodedValues = cleanXSS(values.get(key));
        newValues.put(key, encodedValues);
      }
    }
    return newValues;
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    //非json类型，直接返回
    if (!MediaType.APPLICATION_JSON_VALUE
        .equalsIgnoreCase(super.getContentType()) &&
        !MediaType.APPLICATION_JSON_UTF8_VALUE
            .equalsIgnoreCase(super.getContentType())) {
      return super.getInputStream();
    }

    //为空，直接返回
    String json = IOUtils.toString(super.getInputStream(), "utf-8");
    if (StringUtils.isBlank(json)) {
      return super.getInputStream();
    }
    Object object = JSON.parse(json);
    object = cleanJSON(object);

    final ByteArrayInputStream bis = new ByteArrayInputStream(
        JSON.toJSONString(object).getBytes("utf-8"));

    return new ServletInputStream() {
      @Override
      public boolean isFinished() {
        return true;
      }

      @Override
      public boolean isReady() {
        return true;
      }

      @Override
      public void setReadListener(ReadListener readListener) {
      }

      @Override
      public int read() throws IOException {
        return bis.read();
      }
    };
  }

  private Object cleanJSON(Object object) {
    String jsonString = JSON.toJSONString(object);
    if (JSON.isValidObject(jsonString)) {
      return cleanJsonObject(JSON.parseObject(jsonString));
    } else if (JSON.isValidArray(jsonString)) {
      return cleanJsonArray(JSON.parseArray(jsonString));
    }
    return object;
  }

  private JSONArray cleanJsonArray(JSONArray jsonArray) {
    JSONArray cleanArray = new JSONArray();
    for (Object obj : jsonArray) {
      if (obj instanceof String) {
        cleanArray.add(cleanXSS((String) obj));
      } else if (obj instanceof Object) {
        cleanArray.add(cleanJSON(obj));
      } else {
        cleanArray.add(obj);
      }
    }
    return cleanArray;
  }

  private JSONObject cleanJsonObject(JSONObject jsonObject) {
    JSONObject cleanObject = new JSONObject();
    for (Entry<String, Object> entry : jsonObject.entrySet()) {
      if (entry.getValue() instanceof String) {
        cleanObject.put(entry.getKey(), cleanXSS((String) entry.getValue()));
      } else if (entry.getValue() instanceof Object) {
        cleanObject.put(entry.getKey(), cleanJSON(entry.getValue()));
      } else {
        cleanObject.put(entry.getKey(), entry.getValue());
      }
    }
    return cleanObject;
  }

  private String cleanXSS(String value) {
//    String cleanString = value;
//    if (value != null) {
//      cleanString = new HTMLFilter().filter(value);
//    }
//    return cleanString;
    return HTMLFilterUtil.cleanStr(value);
  }

  private String[] cleanXSS(String[] values) {
//    String[] encodedValues = null;
//    if (values != null) {
//      encodedValues = new String[values.length];
//      for (int i = 0; i < values.length; i++) {
//        encodedValues[i] = cleanXSS(values[i]);
//      }
//    }
//    return encodedValues;
    return HTMLFilterUtil.cleanStr(values);
  }

}
