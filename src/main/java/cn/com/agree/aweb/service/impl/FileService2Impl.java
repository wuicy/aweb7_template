package cn.com.agree.aweb.service.impl;

import cn.com.agree.aweb.service.FileService2;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService2Impl implements FileService2 {

  final Logger LOGGER = LoggerFactory.getLogger(FileService2Impl.class);

  @Value("${upload-path}")
  private String uploadPath;

  /**
   * @desc: 文件下载，支持断点(header加入"Range:bytes=xx-xx")
   * @param request
   * @param response
   * @param filePath 文件路径
   */
  @Override
  public void download(HttpServletRequest request, HttpServletResponse response, String filePath) {

    File file = new File(uploadPath + filePath);
    if (!file.exists()) {
      LOGGER.info("文件不存在");
      return ;
    }

    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
      long size = file.length();
      response.setContentType("application/x-download");
      response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
      response.setHeader("Accept-Ranges", "bytes");
      //pos开始读取位置;  last最后读取位置;  sum记录总共已经读取了多少字节
      long start = 0, end = size - 1;
      if (null != request.getHeader("Range")) {
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        try {
          String numRang = request.getHeader("Range").replaceAll("bytes=", "");
          String[] strRange = numRang.split("-");
          if (strRange.length == 2) {
            start = Long.parseLong(strRange[0].trim());
            end = Long.parseLong(strRange[1].trim());
          } else {
            start = Long.parseLong(numRang.replaceAll("-", "").trim());
          }
        } catch (NumberFormatException e) {
          start = 0;
        }
      }

      String contentRange = "bytes "+start+"-"+end+"/"+size;
      response.setHeader("Content-Range", contentRange);
      response.addHeader("Content-Length", String.valueOf(end - start + 1));

      outputStream = new BufferedOutputStream(response.getOutputStream());
      inputStream = new BufferedInputStream(new FileInputStream(file));
      inputStream.skip(start);
      byte[] buffer = new byte[1024];
      int length;

      long range = end - start + 1;
      while (range > 0) {
        if(range > buffer.length) {
          length = inputStream.read(buffer, 0, buffer.length);
        } else {
          length = inputStream.read(buffer, 0, (int) range);
        }
        range -= length;
        outputStream.write(buffer, 0, length);
        outputStream.flush();
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (outputStream != null) {
          outputStream.close();
        }
        if (inputStream != null) {
          inputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public String upload(MultipartFile file, HttpServletRequest request) {
    //返回结果
    String res;

    //日期
    Long timeStamp = System.currentTimeMillis();
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    //是否是分片上传
    boolean isPartUpload = false;
    StringBuilder filePath = new StringBuilder(format.format(new Date()));
    String fileName = timeStamp+"";
    String fileSuffix;
    try {
      if (file != null) {
        String originalName = file.getOriginalFilename();
        fileSuffix = originalName.substring(originalName.lastIndexOf(".") + 1);

        //获取分片信息，格式为 当前分片数/总分片数
        String filePart = request.getHeader("filePart");
        //总分片数
        int total;
        //当前分片数
        int currentPart = 0;
        if(StringUtils.isNotEmpty(filePart)) {

          String[] strPart = filePart.split("/");
          //如果有分片信息代表是分片上传,查询分片的唯一标识
          String filePartId = request.getHeader("filePartId");

          if(!StringUtils.isEmpty(filePartId)) {

            isPartUpload = true;

            //获取当前分片数
            try {
              if (strPart.length == 2) {
                //如果符合条件，文件名为当前分片数，将后缀名修改为temp
                currentPart = Integer.parseInt(strPart[0].trim());
                fileSuffix = "temp";
                filePath.append("/").append(filePartId);
                fileName = currentPart+"";
              } else {
                isPartUpload = false;
              }
            } catch (NumberFormatException e) {
              e.printStackTrace();
              isPartUpload = false;
            }
          }
        }

        //保存文件
        File saveFile = new File(uploadPath + "/" + filePath + "/" + fileName + "." + fileSuffix);
        if (!saveFile.getParentFile().exists()) {
          saveFile.getParentFile().mkdirs();
        }
        file.transferTo(saveFile);

        //如果是分片的话，检查是否达到总分片个数，是的话进行合并
        if(isPartUpload) {

          String[] strPart = filePart.split("/");
          //获取总分片数
          total = Integer.parseInt(strPart[1].trim());
          File[] files = new File(uploadPath + "/" + filePath).listFiles();
          //合并文件列表，目录下包含.temp的文件
          List<File> mergeList = Arrays.stream(files).filter(f->f.isFile() && f.getName().contains(".temp")).collect(Collectors.toList());
          //temp分片数等于总分片数，开始合并
          if(mergeList.size() == total && total > 0) {
            //分片文件排序
            partSort(mergeList);
            //文件合并
            fileName = timeStamp+"";
            fileSuffix = originalName.substring(originalName.lastIndexOf(".") + 1);
            fileMerge(mergeList, uploadPath + "/" + filePath + "/" + fileName + "." + fileSuffix);
            //删除temp文件
            for (File f : mergeList) {
              f.delete();
            }
            res = "/" + filePath + "/" + fileName + "." + fileSuffix;
          } else {
            res = "分片上传成功";
          }
        } else {
          res = "/" + filePath + "/" + fileName + "." + fileSuffix;
        }
      } else {
        res = "上传的文件为空";
      }
    } catch (Exception e) {
      e.printStackTrace();
      res = "文件上传失败";
    }
    return res;
  }

  /**
   * @desc: 分片文件排序
   * @param files 文件列表
   */
  public void partSort(List<File> files) {
    try {
      files.sort(Comparator.comparing(o -> new Integer(o.getName().replace(".temp", ""))));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }

  /**
   * @desc: 合并文件
   * @param mergeFiles 合并的文件
   * @param filepath 合并文件存放路径
   */
  public void fileMerge(List<File> mergeFiles, String filepath) throws IOException {
    //合并后文件
    File f = new File(filepath);

    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(f));
    InputStream inputStream;

    try {
      for(File file : mergeFiles)
      {
        if(file.isFile())
        {
          inputStream = new BufferedInputStream(new FileInputStream(file));
          int length = 0;
          byte[] buffer = new byte[1024];
          while((length = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, length);
            outputStream.flush();
          }
          inputStream.close();
        }
      }
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    LOGGER.info("文件合并完成");
  }
}
