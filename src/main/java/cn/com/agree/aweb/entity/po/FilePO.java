package cn.com.agree.aweb.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "AWEB_FILE")
public class FilePO {

  @Id
  @Column(name = "ID", length = 50)
  @GeneratedValue(generator = "idGenerator")
  @GenericGenerator(name = "idGenerator", strategy = "cn.com.agree.aweb.common.util.IdGenerator", parameters = {
      @Parameter(name = "prefix", value = "file")})
  private String Id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "GROUP_PATH")
  private String groupName;

  @Column(name = "NODE_PATH")
  private String nodePath;

  @Column(name="SUFFIX_NAME")
  private String suffixName;

  @Column(name = "[SIZE]") //在oracle中size是关键字
  private Long size;

}
