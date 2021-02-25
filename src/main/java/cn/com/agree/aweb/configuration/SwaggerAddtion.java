package cn.com.agree.aweb.configuration;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ApiListingBuilder;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.Tag;
import springfox.documentation.service.Tags;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

/**
 * 手动添加Swagger Api
 */
@Component
public class SwaggerAddtion implements ApiListingScannerPlugin {

  @Override
  public List<ApiDescription> apply(DocumentationContext documentationContext) {
    //登录
    Parameter username = new ParameterBuilder()
        .description("用户名称")
        .type(new TypeResolver().resolve(String.class))
        .name("username")
        .defaultValue("admin")
        .parameterType("form")
        .parameterAccess("access")
        .required(true)
        .modelRef(new ModelRef("string"))
        .build();
    Parameter password = new ParameterBuilder()
        .description("密码")
        .type(new TypeResolver().resolve(String.class))
        .name("password")
        .defaultValue("R1hNqjnpnOg=")
        .parameterType("form")
        .parameterAccess("access")
        .required(true)
        .modelRef(new ModelRef("string"))
        .build();
    Parameter code = new ParameterBuilder()
        .description("验证码")
        .type(new TypeResolver().resolve(String.class))
        .name("code")
        .parameterType("form")
        .parameterAccess("access")
        .required(true)
        .modelRef(new ModelRef("string"))
        .build();
    Operation loginOperation = new OperationBuilder(new CachingOperationNameGenerator())
        .summary("登录")
        .notes("需要输入验证码")
        .tags(Sets.newHashSet("Authentication"))
        .method(HttpMethod.POST)
        .responseMessages(
            Sets.newHashSet(new ResponseMessageBuilder().code(200).message("OK").build(),
                new ResponseMessageBuilder().code(401).message("Unauthorized").build()))
        .consumes(Sets.newHashSet(MediaType.MULTIPART_FORM_DATA_VALUE))
        .produces(Sets.newHashSet(MediaType.APPLICATION_JSON_VALUE))
        .parameters(Arrays.asList(username, password, code))
        .build();
    ApiDescription loginDesc = new ApiDescription(null, "/login", "登录",
        Arrays.asList(loginOperation), false);
    //注销
    Operation logoutOperation = new OperationBuilder(new CachingOperationNameGenerator())
        .summary("注销")
        .notes("退出登录")
        .tags(Sets.newHashSet("Authentication"))
        .method(HttpMethod.GET)
        .responseMessages(
            Sets.newHashSet(new ResponseMessageBuilder().code(200).message("OK").build(),
                new ResponseMessageBuilder().code(401).message("Unauthorized").build()))
        .produces(Sets.newHashSet(MediaType.APPLICATION_JSON_VALUE))
        .build();
    ApiDescription logoutDesc = new ApiDescription(null, "/logout", "注销",
        Arrays.asList(logoutOperation), false);

    documentationContext.getTags().add(new Tag("Authentication","登录注销"));

    List<ApiDescription> apiDescriptionList = new ArrayList<>(Arrays.asList(loginDesc,logoutDesc));
    return apiDescriptionList;
  }

  @Override
  public boolean supports(DocumentationType documentationType) {
    return DocumentationType.SWAGGER_2.equals(documentationType);
  }
}
