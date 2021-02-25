package cn.com.agree.aweb.entity.query;

import cn.com.agree.aweb.common.base.dao.Range;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Data
public class QueryEntity<T> {

  @ApiModelProperty(value = "页码")
  private int pageNum;
  @ApiModelProperty(value = "每页结果条数")
  private int pageSize;
  @ApiModelProperty(value = "请求参数，VO对象或者String")
  private T query;
  @ApiModelProperty(value = "范围查询")
  private List<Range> ranges;
  @ApiModelProperty(value = "排序")
  private List<Order> orders;

  @Data
  static class Order {
    @ApiModelProperty(value = "排序字段")
    private String key;
    @ApiModelProperty(value = "升序（ASC）或降序（DESC）")
    private String order;
  }

  @ApiModelProperty(hidden = true)
  public List<Sort.Order> getOrders() {
    List<Sort.Order> orders = new ArrayList<>();
    if (this.orders != null) {
      for (Order order : this.orders) {
        if (StringUtils.isNotBlank(order.getKey())) {
          if (StringUtils.isNotBlank(order.getOrder())) {
            orders.add(new Sort.Order(Direction.fromString(order.getOrder()), order.getKey()));
          } else {
            orders.add(Sort.Order.by(order.getKey()));
          }
        }
      }
    }
    return orders;
  }

  @ApiModelProperty(hidden = true)
  public Pageable getPage() {
    Pageable page = PageRequest
        .of(getPageNum() - 1, getPageSize(),
            Sort.by(getOrders()));
    return page;
  }

}
