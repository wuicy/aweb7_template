package cn.com.agree.aweb.afa;

import cn.com.agree.asdk.common.Constants;
import cn.com.agree.asdk.config.consumer.ReferenceConfig;
import cn.com.agree.asdk.config.friendly.Builders;
import cn.com.agree.asdk.http.HttpEntity;
import cn.com.agree.asdk.http.ResponseEntity;
import cn.com.agree.asdk.remoting.ConsumerContext;
import cn.com.agree.asdk.remoting.service.GenericReference;
import cn.com.agree.asdk.remoting.web.client.GenericRestOperations;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class afaSDKTest2 {

    public static void main(String[] args) {
//        abusTest();
        httpTest();
    }

    public static void abusTest() {
        ReferenceConfig<GenericReference> referenceConfig
                = Builders.<GenericReference>forReference()
                .async(false)
                .genericInterface()
                .service("friendly", "1.0.0")
                .name("test")
                .protocol(Constants.Protocol.ABUS, "127.0.0.1", 8080)
                .registry("eureka", " 10.8.4.210", 8761)
                .configuration("zookeeper", "10.8.4.210", 2181)
                .useShort()
                .physicalGroup("*")
                .build();
        referenceConfig.getProtocols().get(0).setBind(false);
        GenericReference reference = referenceConfig.get();
        List<Object> params = new ArrayList<>();
        params.add("Hello");

        try {
            ConsumerContext.removeContext();
            //1.服务码 2.服务版本 3.场景码 4.场景版本
            Object ret = reference.$invoker("poc", "1.0.0", "test", "1.0.0", params);
            System.out.println("abus============================================================");
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void httpTest() {
        ReferenceConfig<GenericRestOperations> referenceConfig = Builders.<GenericRestOperations>forReference()
                .async(false)//是否设置异步，true为异步，false为同步
                .normalInterface(GenericRestOperations.class)
                .service("friendly", "1.0.0")
                .name("test")
                .protocol(Constants.Protocol.HTTP, "127.0.0.1", 8080)
                .registry("eureka", " 10.8.4.210", 8761)
                .configuration("zookeeper", "10.8.4.210", 2181)
                .useShort()
                .physicalGroup("*")
                .build();
        referenceConfig.getProtocols().get(0).setBind(false);
//        GenericRestOperations genericRestOperations = referenceConfig.get();

        GenericReference reference = referenceConfig.get();
        GenericRestOperations genericRestOperations = (GenericRestOperations)reference;


        ConsumerContext.removeContext();
        String str = "https://poc!1.0.0!test!1.0.0/poc/test";
        URI uri = null;
        try {
            uri = new URI(str);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // 拼包
        // 传参
        Map pack = new HashMap(1);
        pack.put("token", "test-token");

        HttpEntity<Map> entity = new HttpEntity<>(pack);

        System.out.println(entity);
        ResponseEntity<byte[]> result = genericRestOperations.postForEntity(uri, new HashMap<>(), entity, byte[].class);
        byte[] ret = result.getBody();
        String string = new String(ret);
//        int start = string.indexOf("{");
//        string = string.substring(start);
        System.out.println("http============================================================");
        System.out.println(string);
    }
}
