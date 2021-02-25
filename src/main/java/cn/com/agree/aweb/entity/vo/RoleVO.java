package cn.com.agree.aweb.entity.vo;

import cn.com.agree.aweb.common.base.entity.BaseVO;
import cn.com.agree.aweb.common.validation.RoleName;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
public class RoleVO extends BaseVO {

  @ApiModelProperty(value = "唯一id")
  @ExcelIgnore
  private String id;

  @ApiModelProperty(value = "角色名称，由'ROLE_'开头加大写字母和下划线组成，不可修改")
  @RoleName
  @ExcelProperty(value = "角色名称", index = 0)
  @ColumnWidth(20)
  private String name;

  @ApiModelProperty(value = "角色描述")
  @ExcelProperty(value = "角色描述", index = 1)
  @ColumnWidth(20)
  private String desc;

  @ApiModelProperty(value = "角色所属权限，展示用属性", hidden = true)
  @ExcelIgnore
  private List<AuthorityVO> authorities;

  @ApiModelProperty(value = "角色所属菜单，展示用属性", hidden = true)
  @ExcelIgnore
  private List<MenuVO> menus;

  @ApiModelProperty(value = "前端权限")
  @ExcelProperty(value = "前端权限", index = 2)
  @ColumnWidth(200)
  private String permissions;

}
