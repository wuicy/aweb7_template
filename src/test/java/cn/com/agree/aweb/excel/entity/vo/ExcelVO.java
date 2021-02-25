package cn.com.agree.aweb.excel.entity.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import java.util.Date;
import lombok.Data;

/**
 * Excel使用示例：VO对象
 * 表现层对象，也是Excel导出数据对象
 */
@Data
public class ExcelVO {

  @ExcelIgnore
  private long id;

  /**
   * @ExcelProperty的属性
   * value是设置表头名称，默认是属性的名称
   * index是列的位置顺序，默认是属性的命名顺序
   * 即不使用注解时表头名称是属性的名称，位置顺序是属性的命名顺序
   *
   * @ExcelIgnore
   * 使用Excel时忽略的属性
   *
   * @ColumnWidth
   * 设置行宽
   */
  @ExcelProperty(value = "名称",index = 0)
  @ColumnWidth(10)
  private String name;

  @ExcelProperty(value = "数字",index = 1)
  @ColumnWidth(10)
  private int number;

  @ExcelProperty(value = "日期",index = 2)
  @ColumnWidth(10)
  private Date date;

}
