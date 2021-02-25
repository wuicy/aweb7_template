package cn.com.agree.aweb.controller;

import cn.com.agree.aweb.service.FileService2;
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

/**
 * @desc: 上传下载
 */
@Api("文件上传下载2")
@RestController
@RequestMapping("/file2")
public class File2Controller {

    @Autowired
    private FileService2 fileService2;

    @GetMapping("download")
    @ApiOperation(value = "文件下载")
    public void download(HttpServletRequest request, HttpServletResponse response, String filePath) {
        fileService2.download(request,response,filePath);
    }

    @PostMapping("upload")
    @ApiOperation(value = "上传")
    public String upload(MultipartFile file, HttpServletRequest request) {
        String res = fileService2.upload(file, request);
        return res;
    }
}
