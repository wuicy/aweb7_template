package cn.com.agree.aweb.controller;

import cn.com.agree.aweb.common.util.TemplateUtil;
import com.alibaba.fastjson.JSONObject;
import com.lowagie.text.DocumentException;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Fanzhendong fanzhendong@agree.com.cn
 * @version: 1.0
 * @create: 2020/4/2 14:07
 * @description: FtlToPdfController
 **/
@Api(description = "模板转换为pdf")
@RestController
@RequestMapping("/templates")//该路径下资源已经放行，详情请看SecurityConfiguration
public class FtlToPdfController {

  @GetMapping("/preview")
  @ApiOperation(value = "预览生成的pdf", notes = "swagger接口返回的下载文件中文名称会有乱码问题，浏览器则不会")
  public void preview(@RequestParam(required = false) String name, @RequestParam(required = false) String desc,
      HttpServletResponse response)
      throws IOException, TemplateException, DocumentException {
    if (name == null) {
      name = "赞同科技";
    }
    if (desc == null) {
      desc = "http://www.agree.com.cn";
    }
    //设置数据
    Map map = new HashMap<>();
    map.put("createTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    map.put("updateTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    JSONObject user = new JSONObject();
    user.put("name", name);
    user.put("desc", desc);
    map.put("user", user);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    TemplateUtil.templateToPdf("template.ftl", map, baos);

    response.setContentType("application/pdf");
    response.setCharacterEncoding("UTF-8");
    //设置名称
    String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    fileName = "报表_" + fileName;

    //下载放开此注释
//    response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");


    response.getOutputStream().write(baos.toByteArray());
  }

  @GetMapping("/download")
  @ApiOperation(value = "下载生成的pdf", notes = "swagger接口返回的下载文件中文名称会有乱码问题，浏览器则不会")
  public void download(@RequestParam(required = false) String name, @RequestParam(required = false) String desc,
      HttpServletResponse response)
      throws IOException, TemplateException, DocumentException {
    if (name == null) {
      name = "赞同科技";
    }
    if (desc == null) {
      desc = "http://www.agree.com.cn";
    }
    //设置数据
    Map map = new HashMap<>();
    map.put("createTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    map.put("updateTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    JSONObject user = new JSONObject();
    user.put("name", name);
    user.put("desc", desc);
    map.put("user", user);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    TemplateUtil.templateToPdf("template.ftl", map, baos);

    response.setContentType("application/pdf");
    response.setCharacterEncoding("UTF-8");
    //设置名称
    String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    fileName = "报表_" + fileName;

    //下载放开此注释
    response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");


    response.getOutputStream().write(baos.toByteArray());
  }
}
