package cn.com.agree.aweb.service.impl;

import cn.com.agree.aweb.common.base.service.BaseServiceImpl;
import cn.com.agree.aweb.common.util.fastdfs.FastDFSClient;
import cn.com.agree.aweb.common.util.fastdfs.FastDFSFile;
import cn.com.agree.aweb.dao.FileDao;
import cn.com.agree.aweb.entity.po.FilePO;
import cn.com.agree.aweb.entity.vo.FileVO;
import cn.com.agree.aweb.service.FileService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl extends BaseServiceImpl<FilePO, String> implements FileService {

  @Autowired
  private FileDao fileDao;

  @Override
  public JpaRepositoryImplementation<FilePO, String> getDao() {
    return fileDao;
  }

  @Override
  @Transactional
  public List<FileVO> upload(MultipartFile[] files) {
    List<FilePO> uploadList = new ArrayList<>();
    List<FileVO> fileList = new ArrayList<>();
    if (ArrayUtils.isNotEmpty(files)) {
      for (MultipartFile file : files) {
        FilePO upload;
        try {
          upload = saveFile(file);
          uploadList.add(upload);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    uploadList = fileDao.saveAll(uploadList);
    if (uploadList != null) {
      for (FilePO filePO : uploadList) {
        FileVO fileVO = new FileVO();
        fileVO.setId(filePO.getId());
        fileVO.setSize(filePO.getSize());
        fileVO.setName(filePO.getName());
        fileVO.setSuffixName(filePO.getSuffixName());
        fileList.add(fileVO);
      }
    }
    return fileList;
  }

  public FilePO saveFile(MultipartFile multipartFile) throws IOException {
    FilePO upload;
    String[] fileAbsolutePath;
    String fileName = multipartFile.getOriginalFilename();
    String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
    byte[] fileBuff = null;
    InputStream inputStream = multipartFile.getInputStream();
    if (inputStream != null) {
      int len1 = inputStream.available();
      fileBuff = new byte[len1];
      inputStream.read(fileBuff);
    }
    inputStream.close();
    FastDFSFile file = new FastDFSFile(fileName, fileBuff, null);
    fileAbsolutePath = FastDFSClient.upload(file);
    upload = new FilePO();
    upload.setName(fileName);
    upload.setGroupName(fileAbsolutePath[0]);
    upload.setNodePath(fileAbsolutePath[1]);
//    upload.setSaveName();
    upload.setSize(multipartFile.getSize());
    upload.setSuffixName(suffixName);
    return upload;
  }

  @Override
  public void download(String id, HttpServletRequest request, HttpServletResponse response) {
    FilePO filePO = fileDao.findById(id).orElse(null);
    String fileName = filePO.getName();
    if (filePO != null) {
      try (InputStream in = FastDFSClient.downFile(filePO.getGroupName(), filePO.getNodePath());
          OutputStream out = response.getOutputStream()) {
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+URLEncoder.encode(fileName,"UTF-8"));
        byte[] b = new byte[2048];
        int length;
        while ((length = in.read(b)) > 0) {
          out.write(b, 0, length);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
