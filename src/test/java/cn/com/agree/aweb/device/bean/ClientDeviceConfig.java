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
@ApiModel(value = "ClientDeviceConfig", description = "客户端外设配置")
public class ClientDeviceConfig {
    /**
     *     {"factory":"nantian",
     *     "clientId":"1232645",
     *     "isGeneral":true,
     *     "factoryCN":"南天",
     *     "category":"msf",
     *     "deviceId":"13564",
     *     "categoryCN":"刷卡器",
     *     "properties":"{\"port\":\"usb0\"}"}
     */
    @ApiModelProperty(value = "设备id")
    @JSONField(name = "deviceId")
    private String deviceId;

    @ApiModelProperty(value = "客户端id")
    @JSONField(name = "clientId")
    private String clientId;

    @ApiModelProperty(value = "是否通用")
    @JSONField(name = "isGeneral")
    private String isGeneral;

    @ApiModelProperty(value = "设备类别")
    @JSONField(name = "category")
    private String category;

    @ApiModelProperty(value = "设备类别中文名称")
    @JSONField(name = "categoryCN")
    private String categoryCN;

    @ApiModelProperty(value = "厂商")
    @JSONField(name = "factory")
    private String factory;

    @ApiModelProperty(value = "厂商中文名称")
    @JSONField(name = "factoryCN")
    private String factoryCN;

    @ApiModelProperty(value = "配置的属性")
    @JSONField(name = "properties")
    private String properties;
}
