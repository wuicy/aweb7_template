package cn.com.agree.aweb.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="文件信息")
public class FileVO {
    
    @ApiModelProperty(value = "文件ID")
    private String id;
    
    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "文件名称后缀")
    private String suffixName;
    
    @ApiModelProperty(value = "size")
    private Long size;

}
