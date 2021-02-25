package cn.com.agree.aweb.service;

import cn.com.agree.aweb.entity.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileService2 {

  void download(HttpServletRequest request, HttpServletResponse response, String filePath);

  String upload(MultipartFile file, HttpServletRequest request);
}
