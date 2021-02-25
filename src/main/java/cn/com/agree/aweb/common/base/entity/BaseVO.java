package cn.com.agree.aweb.common.base.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseVO {

  @ApiModelProperty(value = "创建时间")
  @ExcelProperty(value = "创建时间")
  @ColumnWidth(20)
  private String createTime;

  @ApiModelProperty(value = "创建用户名称")
  @ExcelProperty(value = "创建用户名称")
  @ColumnWidth(30)
  private String createUserName;

  @ApiModelProperty(value = "更新时间")
  @ExcelProperty(value = "更新时间")
  @ColumnWidth(20)
  private String updateTime;

  @ApiModelProperty(value = "更新用户名称")
  @ExcelProperty(value = "更新用户名称")
  @ColumnWidth(30)
  private String updateUserName;
}
