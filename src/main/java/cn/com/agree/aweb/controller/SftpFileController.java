package cn.com.agree.aweb.controller;

import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.service.SftpFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @desc: sftp上传下载
 */
@Api("sftp上传下载")
@RestController
@RequestMapping("/sftp")
public class SftpFileController {

  @Autowired
  private SftpFileService sftpFileService;

  @PostMapping("upload")
  @ApiOperation(value = "文件上传")
  public RespMessage upload(MultipartFile file, HttpServletRequest request) throws IOException {

    if(file == null) {
      return RespMessage.error("上传文件为空");
    }

    String res = "";

    Long timeStamp = System.currentTimeMillis();
    String originalName = file.getOriginalFilename();
    String fileSuffix = originalName.substring(originalName.lastIndexOf(".") + 1);
    String fileName = timeStamp + "." + fileSuffix;

    res = sftpFileService.upload(file, fileName);
    return RespMessage.ok(res);

  }

  @GetMapping("download")
  @ApiOperation(value = "文件下载")
  public void download(HttpServletResponse response, String fileName) throws IOException {
    sftpFileService.download(fileName, response);
  }

}
