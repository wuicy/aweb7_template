package cn.com.agree.aweb.entity.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MenuDTO implements Serializable{

  private static final long serialVersionUID = -5587787656993536577L;

  private String id;

  private String parentId;

  private String name;

  private String title;

  private String path;

  private String icon;

  private Integer order;

  private String desc;

  private Boolean deploy;

}