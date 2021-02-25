package cn.com.agree.aweb.entity.po;

import cn.com.agree.aweb.common.base.entity.BasePO;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Data
//@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "AWEB_ROLE")
public class RolePO extends BasePO {

  @ExcelIgnore
  private static final long serialVersionUID = 5377779626313968749L;

  @Id
  @Column(name = "ID", length = 50)
  @GeneratedValue(generator = "idGenerator")
  @GenericGenerator(name = "idGenerator", strategy = "cn.com.agree.aweb.common.util.IdGenerator", parameters = {
      @Parameter(name = "prefix", value = "rol")})
  @ExcelIgnore
  private String id;

  @Column(name = "NAME", unique = true, updatable = false, nullable = false)
  @ExcelProperty(value = "角色名称", index = 0)
  @ColumnWidth(20)
  private String name;

//  @Column(name = "STATUS")
//  private Boolean status;

  @Column(name = "DESCRIPTION", length = 255)
  @ExcelProperty(value = "角色描述", index = 1)
  @ColumnWidth(20)
  private String desc;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "AWEB_ROLE_AUTHORITY", joinColumns = {
      @JoinColumn(name = "ROLE_ID")}, inverseJoinColumns = {
      @JoinColumn(name = "AUTHORITY_ID")}, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @ExcelIgnore
  private List<AuthorityPO> authorities;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "AWEB_ROLE_MENU", joinColumns = {
      @JoinColumn(name = "ROLE_ID")}, inverseJoinColumns = {
      @JoinColumn(name = "MENU_ID")}, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @ExcelIgnore
  private List<MenuPO> menus;

  @Column(name = "PERMISSIONS", length = 1024)
  @ExcelProperty(value = "前端权限", index = 2)
  @ColumnWidth(200)
  private String permissions;

}