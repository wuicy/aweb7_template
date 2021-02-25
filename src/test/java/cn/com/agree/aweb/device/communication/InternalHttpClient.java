/**
 * Copyright (C), 1993-2018, 赞同科技股份有限公司
 * FileName: InternalHttpClient
 * Description: 内部服务http通讯类
 */
package cn.com.agree.aweb.device.communication;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 〈内部服务http通讯类〉
 */

public class InternalHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(InternalHttpClient.class);

    private static final String UTF_8 = "UTF-8";

    private static final String CONTENT_TYPE = "Content-Type";

    private static Object sendHttpRequest(String uri, HttpRequestBase httpRequest) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        Object result = null;
        try {
            CloseableHttpResponse response = httpclient.execute(httpRequest);
            try {
                int statusCode = response.getStatusLine().getStatusCode();
                logger.info("status:{}", statusCode);
                if (statusCode == 200) {
                    // 获取响应实体
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        Header header = entity.getContentType();
                        if (header != null) {
                            String type = header.toString();
                            if (type.contains("stream")) {
                                result = EntityUtils.toByteArray(entity);
                                // 打印响应内容
                                logger.debug("Response content:{}", ((byte[]) result).length);
                            } else {
                                result = EntityUtils.toString(entity);
                                // 打印响应内容
                                logger.debug("Response content:{}", result);
                            }
                        }
                    }
                } else {
                    httpRequest.abort();
                }
            } finally {
                response.close();
            }
        }catch (ClientProtocolException | ParseException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            result = "Connection refused";
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    private static String dealWithUrlParams(Map<String, String> param) {
        // 参数处理
        String paramStr = "";
        if (param != null && param.size() != 0) {
            for (Map.Entry entry : param.entrySet()) {
                paramStr = paramStr + entry.getKey() + "=" + URLEncoder.encode((String) entry.getValue()) + "&";
            }
        }
        if (!paramStr.equals("")) {
            paramStr = paramStr.substring(0, paramStr.length() - 1);
        }
        return paramStr;
    }

    private static HttpEntity dealWithBodyParams(Object params) {
        HttpEntity requestEntity = null;
        try {
            if(params != null) {
                if (params instanceof Map) {
                    //生成一个JSON格式的字符串，将http全部请求参数设置到httpPost的Entity中
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(params);
                    requestEntity = new StringEntity(jsonString, UTF_8);
                } else {
                    requestEntity = new StringEntity((String) params, UTF_8);
                }
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return requestEntity;
    }

    public static Object sendGet(String url, Map<String, String> param) {
        /**
         * 功能描述: <br>
         * 〈http get request〉
         *
         * @param: url
         * @param: param
         * @return: java.lang.Object
         */
        String paramStr = dealWithUrlParams(param);
        String requestUrl = url;
        if(!"".equals(paramStr)){
            requestUrl = url + "?" + paramStr;
        }
        // 创建httpget.
        HttpGet httpget = new HttpGet(requestUrl);
        String uri = httpget.getURI().toString();
        logger.info("executing request:{}  GET", uri);
        Object result = sendHttpRequest(uri, httpget);
        return result;
    }

    public static Object sendPost(String url, Object params) {
        /**
         * 功能描述: <br>
         * 〈http post request〉
         *
         * @param: url
         * @param: params
         * @return: java.lang.Object
         */
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        HttpEntity httpEntity = dealWithBodyParams(params);
        httppost.setEntity(httpEntity);
        httppost.setHeader(CONTENT_TYPE, "application/json;charset=utf-8");
        logger.info("executing request:{}  POST", url);
        Object result = sendHttpRequest(url, httppost);
        return result;
    }

    public static Object sendPut(String url, Object params) {
        /**
         * 功能描述: <br>
         * 〈http put request〉
         *
         * @param: url
         * @param: params
         * @return: java.lang.Object
         */
        // 创建httpput
        HttpPut httpPut = new HttpPut(url);
        HttpEntity httpEntity = dealWithBodyParams(params);
        httpPut.setEntity(httpEntity);
        httpPut.setHeader(CONTENT_TYPE, "application/json;charset=utf-8");
        logger.info("executing request:{}  PUT", url);
        Object result = sendHttpRequest(url, httpPut);
        return result;
    }

    public static Object sendDelete(String url, Map<String, String> param) {
        /**
         * 功能描述: <br>
         * 〈http delete request〉
         *
         * @param: url
         * @param: param
         * @return: java.lang.Object
         */
        String paramStr = dealWithUrlParams(param);
        String requestUrl = url + "?" + paramStr;
        // 创建httpDelete
        HttpDelete httpDelete = new HttpDelete(requestUrl);
        String uri = httpDelete.getURI().toString();
        logger.info("executing request:{}  DELETE", url);
        Object result = sendHttpRequest(uri, httpDelete);
        return result;
    }

    public static Object sendMultipartPost(String url, MultipartHttpServletRequest multipartRequest) {
        /**
         * 功能描述: <br>
         * 〈http post multipart request〉
         *
         * @param: url
         * @param: multipartRequest
         * @return: java.lang.Object
         */
        Object result = null;
        JSONObject jsonObject = new JSONObject();
        try {
            List<MultipartFile> multipartFiles = multipartRequest.getFiles("exampleInputFile");
            List<JSONObject> fileList = new ArrayList<>();
            for (MultipartFile multipartFile :
                    multipartFiles) {
                String fileName = multipartFile.getOriginalFilename();
                byte[] fileBytes = multipartFile.getBytes();

                JSONObject fileInfo = new JSONObject();
                fileInfo.put("fileName", fileName);
                fileInfo.put("fileBytes", fileBytes);
                fileList.add(fileInfo);
            }
            jsonObject.put("files", fileList);
            Enumeration parameterNames = multipartRequest.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = (String)parameterNames.nextElement();
                String parameterString = multipartRequest.getParameter(parameterName);
                jsonObject.put(parameterName, parameterString);
            }

            HttpEntity httpEntity = new StringEntity(jsonObject.toString(), UTF_8);
            // 创建httppost
            HttpPost httppost = new HttpPost(url);
            httppost.setHeader(CONTENT_TYPE, "application/json;charset=utf-8");
            httppost.setEntity(httpEntity);

            logger.info("executing request:{}  Multipart POST", url);
            result = sendHttpRequest(url, httppost);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}