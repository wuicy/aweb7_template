package cn.com.agree.aweb.common.base.service;

import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import cn.com.agree.aweb.common.util.PDFUtil;
import cn.com.agree.aweb.listener.ExcelUploadListener;
import com.alibaba.excel.EasyExcel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public abstract class ExcelBaseServiceImpl<T, ID extends Serializable> extends
    BaseServiceImpl<T, ID> implements ExcelBaseService<T, ID> {

  public abstract Class getPOClass();

  public abstract Class getVOClass();

  @Override
  public void template(HttpServletResponse response) throws IOException {
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("UTF-8");
    String fileName = getPOClass().getSimpleName().replace("PO", "模板.xlsx");
    response.setHeader("Content-Disposition",
        "attachment; fileName=" + fileName + ";filename*=utf-8''" + URLEncoder
            .encode(fileName, "UTF-8"));

    EasyExcel.write(response.getOutputStream(), getPOClass()).sheet().doWrite(null);
  }


  @Override
  public void upload(MultipartFile file, BaseMapper mapper) throws IOException {
    EasyExcel.read(file.getInputStream(), getPOClass(),
        new ExcelUploadListener(getDao(), mapper)).sheet().doRead();
  }

  @Override
  public void download(List data, HttpServletResponse response) throws IOException {
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("UTF-8");
    String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    fileName = getVOClass().getSimpleName().replaceAll("VO", "_") + fileName;
    response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

    EasyExcel.write(response.getOutputStream(), getVOClass()).sheet().doWrite(data);
  }

  @Override
  public void pdf(List data, HttpServletResponse response) throws IOException {
    response.setContentType("application/pdf");
    response.setCharacterEncoding("UTF-8");
    String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    fileName = getVOClass().getSimpleName().replaceAll("VO", "_") + fileName;
    response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".pdf");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    EasyExcel.write(baos, getVOClass()).sheet().doWrite(data);
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    ByteArrayOutputStream pdfos = (ByteArrayOutputStream) PDFUtil.excel2PDF(bais, true);
    response.getOutputStream().write(pdfos.toByteArray());
  }
}
