package cn.com.agree.aweb.excel.controller;

import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.excel.entity.vo.ExcelVO;
import cn.com.agree.aweb.excel.mapper.ExcelMapper;
import cn.com.agree.aweb.excel.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * Excel使用示例：Controller 直接调用service的方法即可
 *
 * 使用Excel功能需要多增加/改写代码的地方在： 1.PO 2.VO 3.Service 4.ServiceImpl
 */
@Api(description = "Excel功能示例")
@RestController
@RequestMapping("/excel")
public class ExcelController {

  @Autowired
  private ExcelService excelService;

  ExcelMapper mapper = ExcelMapper.INSTANCE;

  @ApiOperation("下载导入模板")
  @GetMapping("/template")
  public void excelTemplate(HttpServletResponse response) throws IOException {
    excelService.template(response);
  }

  @ApiOperation("导入")
  @PostMapping("/import")
  public Object excelUpload(MultipartFile file) throws IOException {
    excelService.upload(file, mapper);
    return RespMessage.ok();
  }

  @ApiOperation("导出")
  @PostMapping("/export/excel")
  public void excelDownload(@RequestBody List<ExcelVO> data, HttpServletResponse response)
      throws IOException {
    //此处仅作为示例，通过前端把导出数据传过来是不安全的。
    //正确的做法应该是
    //1.前端传来id等查询条件
    //2.使用查询条件调用service查出po list
    //3.把po list转换成vo list
    //4.使用vo list调用导出方法
    excelService.download(data, response);
  }

  @ApiOperation("导出")
  @PostMapping("/export/pdf")
  public void pdfDownload(@RequestBody List<ExcelVO> data, HttpServletResponse response)
      throws IOException {
    //此处仅作为示例，通过前端把导出数据传过来是不安全的。
    //正确的做法应该是
    //1.前端传来id等查询条件
    //2.使用查询条件调用service查出po list
    //3.把po list转换成vo list
    //4.使用vo list调用导出方法
    excelService.pdf(data, response);
  }

}
