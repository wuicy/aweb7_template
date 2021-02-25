package cn.com.agree.aweb.entity.vo;

import cn.com.agree.aweb.common.base.entity.BaseVO;
import cn.com.agree.aweb.common.validation.EnglishName;
import cn.com.agree.aweb.common.validation.LegalName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import lombok.Data;


@Data
@ApiModel(value = "MenuVO", description = "菜单VO")
public class MenuVO extends BaseVO {


  @ApiModelProperty(value = "菜单唯一标识")
  private String id;

  @ApiModelProperty(value = "菜单名称，合法格式：以字母开头，只能包含字母、数字和下划线")
  @LegalName
  private String name;

//  @ApiModelProperty(value = "菜单状态")
//  private Boolean status;

  @ApiModelProperty(value = "父级菜单id")
  private String parentId;

  @ApiModelProperty(value = "菜单标题，显示的菜单名称")
  private String title;

  @ApiModelProperty(value = "菜单显示的页面的URL")
  private String path;

  @ApiModelProperty(value = "菜单图标")
  private String icon;

  @ApiModelProperty(value = "菜单顺序")
  private Integer order;

  @ApiModelProperty(value = "菜单描述")
  private String desc;

  @ApiModelProperty(value = "是否为增量部署")
  private Boolean deploy;

}