package cn.com.agree.aweb.device.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * Excel使用示例：VO对象
 * 表现层对象，也是Excel导出数据对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "ExcelData", description = "导出excel例子数据")
public class ExcelData {

  @ApiModelProperty(value = "id")
  private long id;

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "序号")
  private int number;

  @ApiModelProperty(value = "日期")
  private Date date;

}
