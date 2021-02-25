package cn.com.agree.aweb.device.utils;

import cn.com.agree.aweb.device.bean.Bank;
import cn.com.agree.aweb.device.bean.ClientDeviceConfig;
import cn.com.agree.aweb.device.bean.ClientInfo;
import cn.com.agree.aweb.device.bean.Result;
import cn.com.agree.aweb.device.communication.InternalHttpClient;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);


    /**
     *
     * @param ip              客户端ip
     * @param url_management  提供信息查询接口
     * @param url_call_device 调用外设接口
     * @param deviceType      调用外设类型
     * @param callMethod      调用外设方法
     * @param timeout         超时时间
     * @param body            请求参数
     * @return
     */
    public static Result callDevice(String ip,
                                    String url_management,
                                    String url_call_device,
                                    String deviceType,
                                    String callMethod,
                                    int timeout,
                                    Map body) {
        InternalHttpClient httpClient = new InternalHttpClient();
        String url_bank_list = url_management + "/bank-list"; // 获取网点列表
        String url_client_info = url_management + "/client-info"; // 获取某个网点下的所有controller信息
        String url_client_devices_config = url_management + "/client-devices-config"; //获取controller的设备配置列表

        //获取网点列表
        String bank_list_str = (String) httpClient.sendGet(url_bank_list, null);
        logger.info("请求网点列表: {}", bank_list_str);
        List<Bank> bankList = JSONObject.parseArray(bank_list_str, Bank.class);

        //获取网点列表下的所有controller信息
        for (Bank bank : bankList) {
            HashMap params = new HashMap();
            params.put("bankId", bank.getId());
            String client_info_str = (String) httpClient.sendGet(url_client_info, params);
            logger.info("bankId: {}, client_info_str: {}", bank.getId(), client_info_str);
            List<ClientInfo> clientInfoList = JSONObject.parseArray(client_info_str, ClientInfo.class);
            for (ClientInfo client : clientInfoList) {
                if (client.getIp().equals(ip)) {
                    logger.info("clientInfo:", client);
                    //10.8.6.4:5101/client/client-devices-config?clientId=1232645
                    HashMap configParams = new HashMap();
                    configParams.put("clientId", client.getClientId());
                    String client_device_config_str = (String) httpClient.sendGet(url_client_devices_config, configParams);
                    List<ClientDeviceConfig> client_device_config_list = JSONObject.parseArray(client_device_config_str, ClientDeviceConfig.class);
                    for (ClientDeviceConfig config : client_device_config_list) {
                        if (config.getCategory().equals(deviceType.toLowerCase())) {
                            logger.info(" config:{}", config);
                            /**
                             * {
                             * 	"header": {
                             * 		"path": "/",
                             * 		"method": "POST"
                             *        },
                             * 	"payload": {
                             * 		"controllerId": "AC220B07744E",
                             * 		"deviceId": "644221CA",
                             * 		"method": "readonce",
                             * 		"timeout": 10000
                             *    }
                             * }
                             */
                            HashMap paramsMap = new HashMap();
                            HashMap headerMap = new HashMap();
                            headerMap.put("path", "/");
                            headerMap.put("method", "POST");
                            paramsMap.put("header", headerMap);
                            HashMap payloadMap = new HashMap();
                            payloadMap.put("controllerId", config.getClientId());
                            payloadMap.put("deviceId", config.getDeviceId());
                            payloadMap.put("method", callMethod);
                            payloadMap.put("timeout", timeout);
                            paramsMap.put("payload", payloadMap);
                            if (null != body) {
                                payloadMap.put("body", body);
                            } else {
                                payloadMap.put("body", new HashMap());
                            }
                            logger.info("调用外设上送参数：{}", paramsMap);
                            String callRes = (String) httpClient.sendPost(url_call_device + deviceType, paramsMap);
                            logger.info("调用外设返回结果：{}", callRes);
                            Map resultMap = JSONObject.parseObject(callRes);
                            Result result;
                            if (0 == (int) resultMap.get("status")) {
                                Object data = resultMap.get("data");
                                result = Result.getSuccessResult(data, (String) resultMap.get("msg"));
                            } else {
                                result = Result.getErrorsResult((int) resultMap.get("status"), (String) resultMap.get("msg"));
                            }
                            return result;
                        }
                    }
                    return Result.getErrorsResult(-1, "调用的外设没有配置");
                }
            }
        }
        return Result.getErrorsResult(-1, "上送的ip配置没找到。");
    }

    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;
        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }
        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }
        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
