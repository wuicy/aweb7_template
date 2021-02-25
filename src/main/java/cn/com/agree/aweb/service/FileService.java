package cn.com.agree.aweb.service;

import cn.com.agree.aweb.common.base.service.BaseService;
import cn.com.agree.aweb.entity.po.FilePO;
import cn.com.agree.aweb.entity.vo.FileVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService extends BaseService<FilePO, String> {

  List<FileVO> upload(MultipartFile[] files);

  void download(String id, HttpServletRequest request, HttpServletResponse response);
}
