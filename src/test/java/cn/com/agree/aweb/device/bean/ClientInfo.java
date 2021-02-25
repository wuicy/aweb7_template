package cn.com.agree.aweb.device.bean;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ClientInfo", description = "客户端信息")
public class ClientInfo {
    /**
     *  {"bankId":"b66417e5f0614c6090ba381a28e6158e",
     *  "clientId":"AC220B07744E",
     *  "ip":"10.8.1.242",
     *  "os":"Windows",
     *  "status":1}
      */

    @ApiModelProperty(value = "客户端id")
    @JSONField(name = "clientId")
    private String clientId;

    @ApiModelProperty(value = "网点id")
    @JSONField(name = "bankId")
    private String bankId;

    @ApiModelProperty(value = "客户端ip")
    @JSONField(name = "ip")
    private String ip;

    @ApiModelProperty(value = "操作系统")
    @JSONField(name = "os")
    private String os;

    @ApiModelProperty(value = "启停状态：up-启动；down-关闭")
    @JSONField(name = "status")
    private String status;
}
