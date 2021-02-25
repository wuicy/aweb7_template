package cn.com.agree.aweb.common.base.dao;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class Range implements Serializable {

  private static final long serialVersionUID = 984268228734375834L;

  private String key;
  private String from;
  private String to;

  public Range() {
  }

  public Range(String key) {
    this.key = key;
  }

  public Range(String key, String from, String to) {
    this.key = key;
    this.from = from;
    this.to = to;
  }

  @ApiModelProperty(hidden = true)
  public boolean isFromSet() {
    return this.from != null;
  }

  @ApiModelProperty(hidden = true)
  public boolean isToSet() {
    return this.to != null;
  }

  @ApiModelProperty(hidden = true)
  public boolean isBetween() {
    return isFromSet() && isToSet();
  }

  @ApiModelProperty(hidden = true)
  public boolean isSet() {
    return isFromSet() || isToSet();
  }

  @ApiModelProperty(hidden = true)
  public boolean isValid() {
    if (isBetween()) {
      return this.from.compareTo(this.to) <= 0;
    }
    return true;
  }


}
