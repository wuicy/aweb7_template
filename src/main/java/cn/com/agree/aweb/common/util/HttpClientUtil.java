package cn.com.agree.aweb.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * description: http 请求工具类
 */
public class HttpClientUtil {

    /**
     * description: 发送http get 请求
     * @param url url
     * @return java.lang.String
     */
    public static String doHttpGet(String url) {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //创建HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            //执行GET请求
            response = httpClient.execute(httpGet);
//            System.out.println(response.getStatusLine());
            //获取响应实体
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * description: 发送http post 请求
     * @param url
     * @param json josn参数
     * @return java.lang.String
     */
    public static String doHttpPost(String url, String json) {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //创建HttpPost对象
        HttpPost post = new HttpPost(url);
        //设置POST请求传递参数
        try {
            post.setHeader(HTTP.CONTENT_TYPE, "application/json");
            post.setEntity(new StringEntity(json,
                            StandardCharsets.UTF_8));

        } catch (Exception e) {
            e.printStackTrace();
        }
        //执行请求并处理响应
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,"UTF-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
