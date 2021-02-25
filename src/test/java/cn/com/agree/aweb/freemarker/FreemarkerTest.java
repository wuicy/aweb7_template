package cn.com.agree.aweb.freemarker;

import cn.com.agree.aweb.AwebApplication;
import cn.com.agree.aweb.common.util.TemplateUtil;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * @author: Fanzhendong fanzhendong@agree.com.cn
 * @version: 1.0
 * @create: 2020/4/7 16:31
 * @description: FreemarkerTest
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FreemarkerTest {

  private Map map = new HashMap();

  @Before
  public void before() {
    //设置数据
    map.put("createTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    map.put("updateTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    JSONObject user = new JSONObject();
    user.put("name", "赞同科技");
    user.put("desc", "http://www.agree.com.cn");
    map.put("user", user);
  }


  //完整代码生成pdf
  @Test
  public void test()
      throws IOException, DocumentException, TemplateException, com.lowagie.text.DocumentException {

    //初始化配置
    Configuration conf = new Configuration(Configuration.getVersion());
    //指定模板目录为resources下的templates
    conf.setTemplateLoader(new ClassTemplateLoader(AwebApplication.class, "/templates/"));
//    String templatePath = ResourceUtils.getURL("classpath:templates").getPath();
//    conf.setDirectoryForTemplateLoading(new File(templatePath));
    //初始化模板，通过文件名称指定模板
    Template template = conf.getTemplate("template.ftl");
    //定义模板输出
    Writer writer = new StringWriter();
    //把数据渲染进模板
    template.process(map, writer);
    String content = writer.toString();

    //设置字体（在resources的font文件夹下）
//    String fontPath = ResourceUtils.getURL("classpath:font").getPath() + "/SIMSUN.TTC";
    String fontPath=new ClassTemplateLoader(AwebApplication.class, "/font/").getBasePackagePath()+"SIMSUN.TTC";
    ITextRenderer render = new ITextRenderer();
    ITextFontResolver fontResolver = render.getFontResolver();
    fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
    // 解析html生成pdf
    render.setDocumentFromString(content);
    render.layout();
    // 输出PDF的输出流
    render.createPDF(new FileOutputStream("C:/Users/lenovo/Desktop/template.pdf"));
  }

  //使用封装工具类生成pdf
//  @Test
  public void test1()
      throws IOException, DocumentException, TemplateException, com.lowagie.text.DocumentException {
    TemplateUtil.templateToPdf("template.ftl", map,
        new FileOutputStream("C:/Users/lenovo/Desktop/template.pdf"));
  }

  //使用模板生成html
//  @Test
  public void test2()
      throws IOException, DocumentException, TemplateException, com.lowagie.text.DocumentException {
    Writer writer = new StringWriter();
    TemplateUtil.templateToHtml("template.ftl", map, writer);
    System.out.println(writer.toString());
  }


}