package cn.com.agree.aweb.entity.po;

import cn.com.agree.aweb.common.base.entity.BasePO;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "AWEB_USER")
public class UserPO extends BasePO {

  private static final long serialVersionUID = 2795219580807138272L;

  @Id
  @Column(name = "ID", length = 50)
  @GeneratedValue(generator = "idGenerator")
  @GenericGenerator(name = "idGenerator", strategy = "cn.com.agree.aweb.common.util.IdGenerator", parameters = {
      @Parameter(name = "prefix", value = "usr")})
  private String id;

  @Column(name = "NAME", length = 50, unique = true, updatable = false, nullable = false)
  private String name;

  @Column(name = "PASSWORD", length = 128, nullable = false)
  private String password;


  @Column(name = "NICKNAME", length = 200)
  private String nickname;

  @Column(name = "EMAIL", length = 100)
  private String email;

  @Column(name = "PHONE", length = 18)
  private String phone;

  @Column(name = "STATUS", length = 2)
  private String status;

  @Column(name = "DESCRIPTION", length = 255)
  private String desc;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "AWEB_USER_ROLE", joinColumns = {
      @JoinColumn(name = "USER_ID")}, inverseJoinColumns = {
      @JoinColumn(name = "ROLE_ID")}, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private List<RolePO> roles;

}
