package cn.com.agree.aweb.service.impl;

import cn.com.agree.aweb.configuration.sftp.SftpProperties;
import cn.com.agree.aweb.service.SftpFileService;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * @desc:
 */
@Service
public class SftpFileServiceImpl implements SftpFileService {

    final static Logger LOGGER  = LoggerFactory.getLogger(SftpFileServiceImpl.class);

    @Autowired
    private SftpProperties sftpProperties;

    public String upload(MultipartFile file, String fileName) throws IOException {

        String res = "";
        ChannelSftp sftp = connect();
        String directory = sftpProperties.getUploadPath();
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            try {
                sftp.mkdir(directory);
                sftp.cd(directory);
            } catch (SftpException e1) {
                e1.printStackTrace();
                res = "文件上传失败";
                disConnect(sftp);
                return res;
            }
        }

        InputStream inputStream = file.getInputStream();
        try {
            sftp.put(inputStream, fileName);
            res = fileName;
        } catch (Exception e) {
            e.printStackTrace();
            res = "文件上传失败";
        } finally {
            disConnect(sftp);
            closeStream(inputStream,null);
        }
        return res;
    }

    public void download(String fileName, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/force-download");
        resp.addHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(fileName, "UTF-8"));

        OutputStream output = resp.getOutputStream();
        try {
            ChannelSftp sftp = connect();
            sftp.cd(sftpProperties.getUploadPath());
            sftp.get(fileName, output);
            LOGGER.info("文件下载成功");
            disConnect(sftp);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("文件下载失败");
        } finally {
            output.flush();
            output.close();
            closeStream(null,output);
        }
    }

    public ChannelSftp connect() {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(sftpProperties.getUsername(), sftpProperties.getHost(), sftpProperties.getPort());
            Session sshSession = jsch.getSession(sftpProperties.getUsername(), sftpProperties.getHost(), sftpProperties.getPort());
            sshSession.setPassword(sftpProperties.getPassword());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            LOGGER.info("sftp连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sftp;
    }

    public void disConnect(ChannelSftp sftp) {
        try {
            sftp.disconnect();
            sftp.getSession().disconnect();
            LOGGER.info("sftp连接关闭成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeStream(InputStream inputStream,OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(inputStream != null){
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
