package cn.com.agree.aweb.service;


import com.jcraft.jsch.ChannelSftp;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SftpFileService {

    /**
     * @desc: 文件上传
     * @param file 文件
     * @param fileName 保存文件名
     */
    String upload(MultipartFile file, String fileName) throws IOException;

    /**
     * @desc: 文件下载
     * @param fileName 文件名
     * @param resp
     */
    void download(String fileName, HttpServletResponse resp) throws IOException;

    /**
     * @desc: sftp连接
     * @param
     */
    ChannelSftp connect();

    /**
     * @desc: sftp连接关闭
     * @param sftp sftp连接
     */
    void disConnect(ChannelSftp sftp);

    /**
     * @desc: 文件流关闭
     * @param inputStream 输入流
     * @param outputStream 输出流
     */
    void closeStream(InputStream inputStream, OutputStream outputStream);

}
