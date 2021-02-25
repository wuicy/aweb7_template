package cn.com.agree.aweb.configuration;

import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 禁用不安全的请求方法
 */
@Configuration
public class TomcatConfiguration {

  @Bean
  public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
    factory.addContextCustomizers(context -> {
      SecurityConstraint securityConstraint = new SecurityConstraint();
      securityConstraint.setUserConstraint("CONFIDENTIAL");
      SecurityCollection collection = new SecurityCollection();
      collection.addPattern("/*");
      collection.addMethod("HEAD");
      collection.addMethod("PUT");
      collection.addMethod("DELETE");
      collection.addMethod("OPTIONS");
      collection.addMethod("TRACE");
      collection.addMethod("PATCH");
      securityConstraint.addCollection(collection);
      context.addConstraint(securityConstraint);
    });
    return factory;
  }

}
