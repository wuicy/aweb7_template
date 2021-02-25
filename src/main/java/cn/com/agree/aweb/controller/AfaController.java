//package cn.com.agree.aweb.controller;
//
//import cn.com.agree.asdk.http.HttpEntity;
//import cn.com.agree.asdk.http.ResponseEntity;
//import cn.com.agree.asdk.remoting.ConsumerContext;
//import cn.com.agree.asdk.remoting.service.GenericReference;
//import cn.com.agree.asdk.remoting.web.client.GenericRestOperations;
//import cn.com.agree.aweb.common.base.entity.RespMessage;
//import cn.com.agree.aweb.common.util.HttpClientUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/afa")
////@PreAuthorize("hasAuthority('afa')") //如果接口需要添加权限
//public class AfaController {
//
//  @Value("${afasdk.register.ip}")
//  String registerIp;
//  @Value("${afasdk.register.port}")
//  int registerPort;
//  @Value("${afasdk.serviceCode}")
//  String serviceCode;
//  @Value("${afasdk.serviceVersion}")
//  String serviceVersion;
//  @Value("${afasdk.sceneCode}")
//  String sceneCode;
//  @Value("${afasdk.sceneVersion}")
//  String sceneVersion;
//
//  @Autowired
//  GenericReference genericReference;
//  @Autowired
//  GenericRestOperations genericRestOperations;
//
//  @PostMapping("/redirectToAfa")
//  public Object redirectToAfa(@RequestBody Map<String,Object> data) {
//    String params = (JSONObject.toJSONString(data));
//    String url = "http://"+registerIp+":"+registerPort;
//    String rse = HttpClientUtil.doHttpPost(url,params);
//    return JSON.parse(rse);
//  }
//
//  @PostMapping("/syncAbus")
//  public RespMessage syncAbus() {
//    List<Object> params = new ArrayList<>();
//    params.add("Hello");
//    try {
//      ConsumerContext.removeContext();
//      //1.服务码 2.服务版本 3.场景码 4.场景版本 5.参数
//      Object ret = genericReference.$invoker(serviceCode, serviceVersion, sceneCode, sceneVersion, params);
//      System.out.println("abus============================================================"+ret);
//    } catch (Exception e) {
//      e.printStackTrace();
//      return RespMessage.error("调用失败");
//    }
//    return RespMessage.ok("调用成功");
//  }
//
//  @PostMapping("/syncHttp")
//  public RespMessage syncHttp() {
//    //1.服务码 2.服务版本 3.场景码 4.场景版本 5.服务码 6.场景码
//    String str = "https://" + serviceCode + "!" + serviceVersion + "!" + sceneCode + "!" + sceneVersion + "/" + serviceCode + "/" + sceneCode;
//    URI uri = null;
//    try {
//      uri = new URI(str);
//    } catch (URISyntaxException e) {
//      e.printStackTrace();
//    }
//
//    // 传参
//    Map pack = new HashMap(1);
//    pack.put("token", "test-token");
//
//    HttpEntity<Map> entity = new HttpEntity<>(pack);
////    System.out.println(entity);
//    try {
//      ConsumerContext.removeContext();
//      ResponseEntity<byte[]> result = genericRestOperations.postForEntity(uri, new HashMap<>(), entity, byte[].class);
//      byte[] ret = result.getBody();
//      String string = new String(ret);
//      System.out.println("http============================================================"+string);
//      return RespMessage.ok("调用成功");
//    } catch (Exception e) {
//      e.printStackTrace();
//      return RespMessage.error("调用失败");
//    }
//  }
//}
