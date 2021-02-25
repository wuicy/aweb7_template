package cn.com.agree.aweb.afa;

public class afaSDKTest {

    public static void main(String[] args) {
//        abusTest();
        httpTest();
    }

    public static void abusTest() {
//        ReferenceConfig<GenericReference> referenceConfig
//                = Builders.<GenericReference>forReference()
//                .async(false)
//                .genericInterface()
//                .service("friendly", "1.0.0")
//                .name("Test")
//                .protocol(Constants.Protocol.ABUS, "127.0.0.1", 8080)
//                .registry("eureka", " 10.8.4.167", 8762)
//                .configuration("zookeeper", "10.8.4.167", 2181)
//                .useShort()
//                .build();
//        referenceConfig.getProtocols().get(0).setBind(false);
//        GenericReference reference = referenceConfig.get();
//        List<Object> params = new ArrayList<>();
//        params.add(null);
//
//        for (int i = 0; i < 10; i++) {
//            try {
//                ConsumerContext.removeContext();
//                Object ret = reference.$invoker("GXF0305_T2", "1.0.0", "T1", "1.0.0", params);
//                System.out.println(ret);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void httpTest() {
//        ReferenceConfig<GenericRestOperations> referenceConfig = Builders.<GenericRestOperations>forReference()
//                .async(false)//是否设置异步，true为异步，false为同步
//                .normalInterface(GenericRestOperations.class)
//                .service("friendly", "1.0.0")
//                .name("Test")
//                .protocol(Constants.Protocol.HTTP, "127.0.0.1", 8080)
//                .registry("eureka", " 10.8.4.167", 8762)
//                .configuration("zookeeper", "10.8.4.167", 2181)
//                .useShort()
//                .build();
//        referenceConfig.getProtocols().get(0).setBind(false);
//        GenericRestOperations genericRestOperations = referenceConfig.get();
//
//        ConsumerContext.removeContext();
//        String str = "https://friendly!1.0.0!T0001!1.0.0/friendly/T0001";
//        URI uri = null;
//        try {
//            uri = new URI(str);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        // 拼包
//        // 传参
//        Map pack = new HashMap(1);
//        pack.put("token", "test-token");
//
//        HttpEntity<Map> entity = new HttpEntity<>(pack);
//
//        System.out.println(entity);
//        ResponseEntity<byte[]> result = genericRestOperations.postForEntity(uri, new HashMap<>(), entity, byte[].class);
//        byte[] ret = result.getBody();
//        String string = new String(ret);
//        int start = string.indexOf("{");
//        string = string.substring(start);
//        System.out.println(string);
    }
}
