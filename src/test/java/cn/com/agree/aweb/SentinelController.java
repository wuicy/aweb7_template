package cn.com.agree.aweb;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentinelController {

  //定义和加载限流规则
  static {
    List<FlowRule> rules =
        FlowRuleManager.getRules() != null ? FlowRuleManager.getRules() : new ArrayList<>();

    //新建规则，设置限流资源的名称
    FlowRule rule1 = new FlowRule("limit1");
    //设置限流规则，FLOW_GRADE_THREAD为总并发数，FLOW_GRADE_QPS为单个地址的QPS
    rule1.setGrade(RuleConstant.FLOW_GRADE_THREAD);
    //设置限流阈值
    rule1.setCount(5);

    FlowRule rule2 = new FlowRule("limit2");
    rule2.setGrade(RuleConstant.FLOW_GRADE_THREAD);
    rule2.setCount(5);

    rules.add(rule1);
    rules.add(rule2);

    //加载限流规则
    FlowRuleManager.loadRules(rules);
  }


  /*
   * value定义资源名称
   * 只定义了value时，并发请求数达到阈值时抛出默认异常
   */
  @SentinelResource(value = "limit1")
  @GetMapping("/limit1")
  public String limit1() throws InterruptedException {
    Thread.sleep(2000L);
    return "limit1";
  }

  /*
   * 定义了blockHandler后，并发请求数达到阈值时调用blockHandler指定的方法
   */
  @SentinelResource(value = "limit2", blockHandler = "blockHandler")
  @GetMapping("/limit2")
  public String limit2() throws InterruptedException {
    Thread.sleep(2000L);
    return "limit2";
  }

  /*
   * 1.该方法必须为public
   * 2.返回值原方法一致
   * 3.方法参数跟原方法一致之外，后面还需要加个BlockException
   */
  public String blockHandler(BlockException ex) {
    return "block";
  }

}
