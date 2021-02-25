package cn.com.agree.aweb.excel.entity.po;

import cn.com.agree.aweb.common.base.entity.BasePO;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Excel使用示例：PO对象
 * 持久层对象，也是Excel导入数据对象
 */
@Data
//@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "DEMO_EXCEL")
public class ExcelPO extends BasePO{

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ExcelIgnore
  private Long id;

  @Column(name = "NAME")
  @ExcelProperty(value = "名称",index = 0)
  @ColumnWidth(15)
  private String name;

  @Column(name = "NUMBER")
  @ExcelProperty(value = "数字",index = 1)
  @ColumnWidth(15)
  private Integer number;

  @Column(name = "DATE")
  @ExcelProperty(value = "日期",index = 2)
  @ColumnWidth(15)
  private Date date;
}
