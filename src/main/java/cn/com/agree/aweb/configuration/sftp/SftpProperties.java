package cn.com.agree.aweb.configuration.sftp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @desc: sftp配置项
 */
@Component
@ConfigurationProperties(prefix="sftp-server")
@Data
public class SftpProperties {
    private String host;
    private int port;
    private String username;
    private String password;
    private String uploadPath;
}
