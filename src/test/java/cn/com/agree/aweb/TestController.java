package cn.com.agree.aweb;

import cn.com.agree.aweb.common.validation.LowercaseName;
import cn.com.agree.aweb.common.validation.RoleName;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@Validated
public class TestController {

  @GetMapping("/test")
  public Object test() {
    return "Hello World!";
  }

  @PutMapping("/test")
  public Object test1() {
    return "Hello World!";
  }

  @GetMapping("/testError")
  public Object error() {
    return String.valueOf(10 / 0);
  }

  @GetMapping("/testParam")
  public Object testParam(@RequestParam String param) {
    return param;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/hasRole")
  public Object hasRole() {
    return "hasRole";
  }

  @PreAuthorize("hasAuthority('userManagement')")
  @GetMapping("/hasAuthority")
  public Object hasAuthority() {
    return "hasAuthorize";
  }

  @PreAuthorize(value = "hasRole('ADMIN') && hasAuthority('userManagement')")
  @GetMapping("/and")
  public Object and() {
    return "and";
  }

  @PreAuthorize(value = "hasRole('ADMIN') || hasAuthority('userManagement')")
  @GetMapping("/or")
  public Object or() {
    return "or";
  }

  @GetMapping("/testValid/{str}")
  public Object testValid(@PathVariable("str") @LowercaseName String str) {
    return str;
  }


  static {
    initSentinelRule();
  }

  private static void initSentinelRule() {
    List<FlowRule> rules =
        FlowRuleManager.getRules() != null ? FlowRuleManager.getRules() : new ArrayList<>();
    FlowRule rule = new FlowRule("test");
    rule.setGrade(RuleConstant.FLOW_GRADE_THREAD);
    rule.setCount(5);
    rules.add(rule);
    FlowRuleManager.loadRules(rules);
  }

  @SentinelResource(value = "test", blockHandler = "blockHandler")
  @GetMapping("/testLimit")
  public String testLimit() throws InterruptedException {
    Thread.sleep(2000L);
    return "limit";
  }

  public String blockHandler(BlockException ex) {
    System.out.println("block");
    return "block";
  }
}
