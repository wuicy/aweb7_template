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
@Table(name = "AWEB_AUTHORITY")
public class AuthorityPO extends BasePO {

  private static final long serialVersionUID = -6452207679156758232L;

  @Id
  @Column(name = "ID", length = 50)
  @GeneratedValue(generator = "idGenerator")
  @GenericGenerator(name = "idGenerator", strategy = "cn.com.agree.aweb.common.util.IdGenerator", parameters = {
      @Parameter(name = "prefix", value = "auth")})
  private String id;

  @Column(name = "NAME", unique = true, updatable = false, nullable = false)
  private String name;

  @Column(name = "PARENT_ID", length = 50)
  private String parentId;

  @Column(name = "DESCRIPTION", length = 255)
  private String desc;

}