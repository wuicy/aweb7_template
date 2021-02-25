package cn.com.agree.aweb.entity.vo;

import cn.com.agree.aweb.common.base.entity.BaseVO;
import cn.com.agree.aweb.common.validation.LegalName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AuthorityVO", description = "权限VO")
public class AuthorityVO extends BaseVO {

  @ApiModelProperty(value = "唯一id")
  private String id;
  @ApiModelProperty(value = "权限名称，不可修改；合法格式:以字母开头，只能包含字母、数字和下划线")
  @LegalName
  private String name;
  @ApiModelProperty(value = "权限描述")
  private String desc;
  @ApiModelProperty(value = "上一级权限id")
  private String parentId;

}
