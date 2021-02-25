package cn.com.agree.aweb.device.bean;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "Bank", description = "银行网点")
public class Bank {

    @ApiModelProperty(value = "银行网点id")
    @JSONField(name = "id")
    private String id;

    @ApiModelProperty(value = "银行网点名称")
    @JSONField(name = "name")
    private String name;

}
