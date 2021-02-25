package cn.com.agree.aweb.common.base.entity;

import cn.com.agree.aweb.listener.EntityListener;
import com.alibaba.excel.annotation.ExcelIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@MappedSuperclass
@EntityListeners(EntityListener.class)
public class BasePO implements Serializable {

  @ExcelIgnore
  private static final long serialVersionUID = 3567846355853017142L;

  @Column(name = "CREATE_TIME", updatable = false)
  @ExcelIgnore
  private String createTime;

  @Column(name = "CREATE_USER_ID", updatable = false)
  @ExcelIgnore
  private String createUserId;

  @Column(name = "CREATE_USER_NAME", updatable = false)
  @ExcelIgnore
  private String createUserName;

  @Column(name = "UPDATE_TIME")
  @ExcelIgnore
  private String updateTime;

  @Column(name = "UPDATE_USER_ID")
  @ExcelIgnore
  private String updateUserId;

  @Column(name = "UPDATE_USER_NAME")
  @ExcelIgnore
  private String updateUserName;
}
