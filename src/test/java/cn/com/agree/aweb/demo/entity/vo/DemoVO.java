package cn.com.agree.aweb.demo.entity.vo;

import cn.com.agree.aweb.common.base.entity.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "DemoVO", description = "案例VO")//swagger注解，详情参见swagger注解的使用方法
public class DemoVO extends BaseVO{

  @ApiModelProperty(value = "唯一id", hidden = false)//swagger注解，详情参见swagger注解的使用方法
  private String id;

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "年龄")
  private Integer age;

  @ApiModelProperty(value = "详细描述")
  private String desc;
}
