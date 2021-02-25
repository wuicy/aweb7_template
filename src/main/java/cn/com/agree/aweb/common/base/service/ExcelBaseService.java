package cn.com.agree.aweb.common.base.service;

import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelBaseService<T, ID extends Serializable> extends BaseService<T, ID> {

  void template(HttpServletResponse response) throws IOException;

  void upload(MultipartFile file, BaseMapper mapper) throws IOException;

  void download(List data, HttpServletResponse response) throws IOException;

  void pdf(List data, HttpServletResponse response) throws IOException;
}
