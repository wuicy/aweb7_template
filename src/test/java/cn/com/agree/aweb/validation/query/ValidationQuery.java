package cn.com.agree.aweb.validation.query;

import cn.com.agree.aweb.common.validation.EnglishName;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class ValidationQuery {

  @EnglishName//字符串为全英文字符
  private String name;

  @Min(0)//最小为0
  @Max(100)//最大为100
  private Integer age;

  private String desc;

}
