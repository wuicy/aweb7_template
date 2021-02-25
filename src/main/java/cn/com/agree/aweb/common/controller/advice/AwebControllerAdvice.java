package cn.com.agree.aweb.common.controller.advice;

import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.entity.constant.AWebConstants;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@RestControllerAdvice
public class AwebControllerAdvice implements ResponseBodyAdvice<Object> {


  /**
   * 判断拦截类型
   */
  @Override
  public boolean supports(MethodParameter returnValue,
      Class<? extends HttpMessageConverter<?>> converter) {
    return true;
  }

  /**
   * 拦截处理body
   */
  @Nullable
  @Override
  public Object beforeBodyWrite(@Nullable Object body, MethodParameter methodParameter,
      MediaType mediaType, Class<? extends HttpMessageConverter<?>> converter,
      ServerHttpRequest request, ServerHttpResponse response) {
    //转换 /error的结果
    if (request.getURI().getPath().equals("/error")) {
      Map errorMap = (Map) body;
      return RespMessage
          .build(false, (Integer) errorMap.get("status"), String.valueOf(errorMap.get("error")));
    }

    if (pathFilter(request) || body instanceof RespMessage) {
      //RespMessage类型的结果不变
      return body;
    } else if (body instanceof String) {
      //转换String类型的结果
      response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
      return JSONObject.toJSONString(RespMessage.build(true, 200, body));
    } else {
      //转换其他类型的的结果
      return RespMessage.build(true, 200, body);
    }
  }

  //限流异常处理
  @ResponseBody
  @ExceptionHandler(UndeclaredThrowableException.class)
  public RespMessage handleBlockException(UndeclaredThrowableException ex) {
    if (!(ex.getUndeclaredThrowable() instanceof BlockException)) {
      throw ex;
    }
    return RespMessage.error("超出服务器可处理的请求数，请稍后访问。");
  }

  //验证异常处理
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public RespMessage handleBindException(MethodArgumentNotValidException ex) {
    FieldError fieldError = ex.getBindingResult().getFieldError();
    return RespMessage.error(fieldError.getField() + ":" + fieldError.getDefaultMessage());
  }

  //excel异常处理
  @ResponseBody
  @ExceptionHandler(ExcelAnalysisException.class)
  public RespMessage handleExcelAnalysisException(ExcelAnalysisException ex) {
    ex.printStackTrace();
    return RespMessage.error(ex.getCause().getMessage());
  }

  //SQL参数重复异常处理
  @ResponseBody
  @ExceptionHandler(DataIntegrityViolationException.class)
  public RespMessage handleSQLException(DataIntegrityViolationException ex) {
    return RespMessage.error(ex.getCause().getCause().getMessage());
  }

  @ResponseBody
  @ExceptionHandler(value = Exception.class)
  public RespMessage defaultErrorHandler(HttpServletRequest request, Exception e) {
    e.printStackTrace();
    RespMessage respMessage = RespMessage.build().setSuccess(false)
        .setMsg(e.getClass() + " : " + e.getMessage());
    if (e instanceof NoHandlerFoundException) {
      respMessage.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } else if (e instanceof AccessDeniedException) {
      respMessage.setStatus(HttpServletResponse.SC_FORBIDDEN);
    } else {
      respMessage.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    return respMessage;
  }

  @Autowired
  Environment env;

  private boolean pathFilter(ServerHttpRequest request) {
    String uri = request.getURI().getPath();
    String contextPath = env.getProperty("server.servlet.context-path");
    if (contextPath == null) {
      contextPath = "";
    }

    //用Spring Oauth2单点登录鉴权需要放开这里
//    if (uri.equals(contextPath + AWebConstants.USER_CURRENT_PATH) ) {
//      return true;
//    }

    for (String regexPath : AWebConstants.SWAGGER_REGEX_PATHS) {
      if (uri.matches(contextPath + regexPath)) {
        return true;
      }
    }
    return false;
  }
}
