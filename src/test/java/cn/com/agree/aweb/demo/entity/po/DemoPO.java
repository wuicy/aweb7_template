package cn.com.agree.aweb.demo.entity.po;

import cn.com.agree.aweb.common.base.entity.BasePO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


//lombok注解
@Data//自动生成Getter、Setter、空构造器、equals、hashCode、toString等方法，可手写覆盖部分方法
@Accessors(chain = true)//Setter链式编程
@EqualsAndHashCode(callSuper = true)
//持久层注解
@Entity//标注是持久层实体
@Table(name = "AWEB_DEMO")//对应数据库表名称
public class DemoPO extends BasePO{

  private static final long serialVersionUID = -5582756296645356675L;

  @Id//标注为主键
  @Column(name = "ID", unique = true,
      updatable = false, nullable = false)//对应数据库中的列名，下同
  //Id生成策略
  @GeneratedValue(generator = "idGenerator")//指定生成器
  //该生成器可生成 前缀+短UUID 字符串ID
  @GenericGenerator(name = "idGenerator",
      strategy = "cn.com.agree.aweb.common.util.IdGenerator",
      parameters = {@Parameter(name = "prefix", value = "demo")})
  private String id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "AGE")
  private Integer age;

  @Column(name = "DESCRIPTION")
  private String desc;
}
