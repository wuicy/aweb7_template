package cn.com.agree.aweb.controller;

import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.entity.vo.FileVO;
import cn.com.agree.aweb.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api( description = "文件上传下载")
@RestController
@RequestMapping("/file")
public class FileController {

  @Autowired
  private FileService fileService;


  @PostMapping(value = "/upload", consumes = "multipart/*", headers = "Content-Type=multipart/form-data")
  @ApiOperation(value = "文件上传",notes = "暂时只能通过postman进行测试")
  public RespMessage upload(MultipartFile[] file) {
    List<FileVO> result = fileService.upload(file);
    return RespMessage.ok(result);

  }

  @GetMapping("/download")
  @ApiOperation(value = "文件下载",notes = "swagger接口返回的下载文件中文名称会有乱码问题，浏览器则不会")
  public void download(String id, HttpServletRequest request, HttpServletResponse response) {
    fileService.download(id, request, response);
  }

}
