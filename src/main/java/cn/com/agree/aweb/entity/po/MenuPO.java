package cn.com.agree.aweb.entity.po;

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


@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "AWEB_MENU")
public class MenuPO extends BasePO {

  private static final long serialVersionUID = 6643574279435044485L;

  @Id
  @Column(name = "ID", length = 50)
  @GeneratedValue(generator = "idGenerator")
  @GenericGenerator(name = "idGenerator", strategy = "cn.com.agree.aweb.common.util.IdGenerator", parameters = {
      @Parameter(name = "prefix", value = "file")})
  private String id;

  @Column(name = "NAME", unique = true, nullable = false)
  private String name;

//  private boolean status = true;

  @Column(name = "PARENT_ID", length = 50)
  private String parentId;

  @Column(name = "TITLE", length = 50, nullable = false)
  private String title;

  @Column(name = "PATH", length = 400)
  private String path;

  @Column(name = "ICON", length = 50)
  private String icon;

  @Column(name = "ORDER_", length = 5)
  private Integer order;

  @Column(name = "DESCRIPTION", length = 255)
  private String desc;

  @Column(name="DEPLOY")
  private Boolean deploy;

}