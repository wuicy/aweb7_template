package cn.com.agree.aweb.common.util;

import cn.com.agree.aweb.AwebApplication;
import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * @author: Fanzhendong fanzhendong@agree.com.cn
 * @version: 1.0
 * @create: 2020/4/8 11:34
 * @description: TemplateUtil
 **/
public class TemplateUtil {

  private TemplateUtil() {
  }

  public static void templateToHtml(String templateName, Object dataModel, Writer out)
      throws IOException, TemplateException {
    //初始化配置
    Configuration conf = new Configuration(Configuration.getVersion());
    //指定模板目录为resources下的templates
    conf.setTemplateLoader(new ClassTemplateLoader(AwebApplication.class, "/templates/"));
    //初始化模板，通过文件名称指定模板
    Template template = conf.getTemplate(templateName);

    //把数据填充进模板
    template.process(dataModel, out);
  }

  private static String DEFAULT_FONT_NAME = "SIMSUN.TTC";

  public static void templateToPdf(String templateName, Object dataModel, OutputStream out,
      String fontName)
      throws IOException, DocumentException, TemplateException {
    if (fontName == null) {
      fontName = DEFAULT_FONT_NAME;
    }

    Writer writer = new StringWriter();

    templateToHtml(templateName, dataModel, writer);

    String fontPath=new ClassTemplateLoader(AwebApplication.class, "/font/").getBasePackagePath()+fontName;
    ITextRenderer render = new ITextRenderer();
    ITextFontResolver fontResolver = render.getFontResolver();
    fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
    render.setDocumentFromString(writer.toString());
    render.layout();
    render.createPDF(out);
  }

  public static void templateToPdf(String templateName, Object dataModel, OutputStream out)
      throws IOException, DocumentException, TemplateException {
    templateToPdf(templateName, dataModel, out, null);
  }

}
