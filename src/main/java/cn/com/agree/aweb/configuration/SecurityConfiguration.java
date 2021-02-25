package cn.com.agree.aweb.configuration;

import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.common.security.PasswordDecodeRequestWrapper;
import cn.com.agree.aweb.common.util.ServletUtil;
import cn.com.agree.aweb.common.util.SpringContextUtil;
import cn.com.agree.aweb.common.security.XssHttpServletRequestWrapper;
import cn.com.agree.aweb.entity.constant.AWebConstants;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        //静态资源和swagger放行
        .antMatchers("/**/*.ico")
        .antMatchers("/swagger-ui.html")
        .antMatchers("/swagger-resources/**")
        .antMatchers("/images/**")
        .antMatchers("/webjars/**")
        .antMatchers("/v2/api-docs")
        //验证码放行
        .antMatchers("/verify/**")
        //注册放行
        .antMatchers("/register")
        .antMatchers("/templates/**");
//        .antMatchers("/test**/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        //未登录处理
        .exceptionHandling()
        .authenticationEntryPoint((request, response, e) -> {
          response.setContentType("application/json;charset=utf-8");
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter()
              .write(JSONObject.toJSONString(
                  RespMessage.build(false, HttpServletResponse.SC_UNAUTHORIZED, "未登录")));
        })
        //没有权限处理
        .accessDeniedHandler((request, response, e) -> {
          response.setContentType("application/json;charset=utf-8");
          response.setStatus(HttpServletResponse.SC_FORBIDDEN);
          response.getWriter()
              .write(JSONObject.toJSONString(
                  RespMessage.build(false, HttpServletResponse.SC_FORBIDDEN, "没有权限")));
        })
        .and()
        //登录
        .formLogin().loginProcessingUrl("/login")
        //登录成功处理
        .successHandler((request, response, authentication) -> {
          response.setContentType("application/json;charset=utf-8");
          //登录成功后获取CSRF TOKEN
          CsrfTokenRepository csrfTokenRepository = SpringContextUtil
              .getBean(CsrfTokenRepository.class);
          CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
          csrfTokenRepository.saveToken(csrfToken, request, response);
          response.getWriter().write(JSONObject.toJSONString(RespMessage.ok("登录成功!", csrfToken)));
          response.getWriter().flush();
          response.getWriter().close();
        })
        //登录失败处理
        .failureHandler((request, response, e) -> {
          response.setContentType("application/json;charset=utf-8");
          response.getWriter()
              .write(JSONObject.toJSONString(
                  RespMessage.build(false, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage())));
          response.getWriter().flush();
          response.getWriter().close();
        })
        .and()
        //注销
        .logout().logoutUrl("/logout")
        //注销成功处理
        .logoutSuccessHandler((request, response, authentication) -> {
          response.setContentType("application/json;charset=utf-8");
          response.getWriter().write(JSONObject.toJSONString(RespMessage.ok("注销成功!")));
          response.getWriter().flush();
          response.getWriter().close();
        })
        .and()
        //拦截规则
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        //登录验证码 密码解密 过滤器
        .addFilterBefore((request, response, chain) -> {
          HttpServletRequest req = (HttpServletRequest) request;
          HttpServletResponse resp = (HttpServletResponse) response;
          if (req.getServletPath().equals("/login") && HttpMethod.POST.matches(req.getMethod())) {
            req = new PasswordDecodeRequestWrapper((HttpServletRequest) request);
            String sessionCode = (String) ServletUtil
                .getSessionAttribute(AWebConstants.VERIFY_OBJ_SESSION_ATTR_NAME);
            String code = request.getParameter("code");
            ServletUtil.removeSessionAttribute(AWebConstants.VERIFY_OBJ_SESSION_ATTR_NAME);
            if (code == null) {
              response.setContentType("application/json;charset=utf-8");
              response.getWriter()
                  .write(JSONObject.toJSONString(
                      RespMessage.build(false, HttpServletResponse.SC_UNAUTHORIZED, "验证码不能为空！")));
              return;
            } else if (!code.equalsIgnoreCase(sessionCode)) {
              response.setContentType("application/json;charset=utf-8");
              response.getWriter()
                  .write(JSONObject.toJSONString(
                      RespMessage.build(false, HttpServletResponse.SC_UNAUTHORIZED, "验证码错误！")));
              return;
            }
          }
          chain.doFilter(req, resp);
        }, UsernamePasswordAuthenticationFilter.class)
        //CSRF TOKEN验证过滤器
        .addFilterAfter((request, response, chain) -> {
          HttpServletRequest req = (HttpServletRequest) request;
          HttpServletResponse resp = (HttpServletResponse) response;
          CsrfTokenRepository csrfTokenRepository = SpringContextUtil
              .getBean(CsrfTokenRepository.class);
          CsrfToken csrfToken = csrfTokenRepository.loadToken(req);
          if (csrfToken == null) {
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter()
                .write(JSONObject.toJSONString(
                    RespMessage
                        .build(false, HttpServletResponse.SC_UNAUTHORIZED, "登录超时，请重新登录")));
            return;
          } else {
            String csrf = req.getHeader(csrfToken.getHeaderName());
            if(csrf == null){
              csrf = req.getParameter(csrfToken.getParameterName());
            }
            if (csrf == null || !csrfToken.getToken().equals(csrf)) {
              resp.setContentType("application/json;charset=utf-8");
              resp.getWriter().write(JSONObject.toJSONString(RespMessage.build(false, HttpServletResponse.SC_UNAUTHORIZED, "Csrf Token错误或为空。")));
              return;
            }
          }
          chain.doFilter(req, resp);
        }, FilterSecurityInterceptor.class)
//        XSS防御 过滤参数
        .addFilterBefore((request, response, chain) -> {
          XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
              (HttpServletRequest) request);
          chain.doFilter(xssRequest, response);
        }, FilterSecurityInterceptor.class)
        //关闭Spring Security自带的csrf
        .csrf().disable()
        .headers().frameOptions().sameOrigin().and()
        //允许跨域
        .cors();

  }


}
