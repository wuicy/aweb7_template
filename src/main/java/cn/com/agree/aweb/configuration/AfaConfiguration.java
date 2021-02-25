//package cn.com.agree.aweb.configuration;
//
//import cn.com.agree.asdk.common.Constants;
//import cn.com.agree.asdk.config.consumer.ReferenceConfig;
//import cn.com.agree.asdk.config.friendly.Builders;
//import cn.com.agree.asdk.remoting.service.GenericReference;
//import cn.com.agree.asdk.remoting.web.client.GenericRestOperations;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//
//import javax.annotation.PostConstruct;
//import java.net.Inet4Address;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//@Configuration
//public class AfaConfiguration {
//
//  @Value("${afasdk.register.type}")
//  String registerType;
//  @Value("${afasdk.register.ip}")
//  String registerIp;
//  @Value("${afasdk.register.port}")
//  int registerPort;
//  @Value("${afasdk.configuration.ip}")
//  String configurationIp;
//  @Value("${afasdk.configuration.port}")
//  int configurationPort;
//  @Value("${afasdk.timeOut:3000}")
//  int timeOut;
//
//  private String host = "";
//  @PostConstruct
//  private void getHost(){
//    InetAddress ia = null;
//    try {
//      ia=InetAddress.getLocalHost();
//      host = ia.getHostAddress();
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//  private final boolean ASYNC = false;
//  private final int PORT = 0;
//  private final String SERVICE = "aweb";
//  private final String VERSION = "7.0";
//  private final String NAME = "aweb";
//  private final String CONFIGURATION_TYPE = "zookeeper";
//  private final String PHYSICAL_GROUP = "*";
//
//  @Bean
//  GenericReference genericReference() {
//    ReferenceConfig<GenericReference> referenceConfig
//        = Builders.<GenericReference>forReference()
//        .async(ASYNC)//是否设置异步，true为异步，false为同步
//        .genericInterface()
//        .service(SERVICE, VERSION)
//        .name(NAME)
//        .protocol(Constants.Protocol.ABUS, host, PORT)
//        .registry(registerType, registerIp, registerPort)//注册中心类型,运行的ip以及端口
//        .configuration(CONFIGURATION_TYPE, configurationIp, configurationPort)//配置中心类型,运行的ip和端口
//        .useShort()
//        .physicalGroup(PHYSICAL_GROUP) //设置分组
//        .build();
//    referenceConfig.getProtocols().get(0).setBind(false);
//    referenceConfig.setTimeout(timeOut);
//    GenericReference reference = referenceConfig.get();
//    return reference;
//  }
//
//  @Bean
//  GenericRestOperations genericRestOperations() {
//    ReferenceConfig<GenericRestOperations> referenceConfig
//            = Builders.<GenericRestOperations>forReference()
//            .async(ASYNC)//是否设置异步，true为异步，false为同步
//            .normalInterface(GenericRestOperations.class)
//            .service(SERVICE, VERSION)
//            .name(NAME)
//            .protocol(Constants.Protocol.HTTP, host, PORT)
//            .registry(registerType, registerIp, registerPort)//注册中心类型,运行的ip以及端口
//            .configuration(CONFIGURATION_TYPE, configurationIp, configurationPort)//配置中心类型,运行的ip和端口
//            .useShort()
//            .physicalGroup(PHYSICAL_GROUP) //设置分组
//            .build();
//    referenceConfig.getProtocols().get(0).setBind(false);
//    referenceConfig.setTimeout(timeOut);
//    GenericRestOperations genericRestOperations = referenceConfig.get();
//    return genericRestOperations;
//  }
//
//}
