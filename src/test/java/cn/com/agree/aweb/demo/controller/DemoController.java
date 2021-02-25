package cn.com.agree.aweb.demo.controller;

import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.demo.entity.po.DemoPO;
import cn.com.agree.aweb.demo.entity.vo.DemoVO;
import cn.com.agree.aweb.demo.mapper.DemoMapper;
import cn.com.agree.aweb.demo.service.DemoService;
import cn.com.agree.aweb.entity.query.QueryEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "Controller功能描述")
@RestController
@RequestMapping("/demo")
public class DemoController {

  @Autowired
  DemoService demoService;

  DemoMapper mapper = DemoMapper.INSTANCE;

  @ApiOperation(value = "列出所有", notes = "查找并以list形式列出所有数据")
  @GetMapping("/list")
  public List<DemoVO> list() {
    return mapper.poToVO(demoService.findAll());
  }

  @ApiOperation(value = "动态模糊查询", notes = "类型为字符串的属性进行动态模糊查询，排序分页列出所有数据")
  @PostMapping("/list")
  public Page<DemoVO> listByQuery(@RequestBody QueryEntity<DemoVO> queryEntity) {
    return demoService.findByQuery(queryEntity, mapper);
  }

  @ApiOperation(value = "匹配查询", notes = "类型为字符串的属性进行匹配查询，单个字段模糊匹配即可，排序分页列出所有数据")
  @PostMapping("/list/matching")
  public Page<DemoVO> listByString(@RequestBody QueryEntity<String> queryEntity)
      throws IllegalAccessException, InstantiationException {
    return demoService.findByString(DemoPO.class, queryEntity, mapper);
  }


  @ApiOperation(value = "根据ID查询", notes = "根据id获取单条数据")
  @GetMapping("/get/{id}")
  public DemoVO get(@PathVariable("id") String id) {
    return mapper.poToVO(demoService.findById(id).orElse(null));
  }

  @ApiOperation(value = "添加", notes = "添加单条数据")
  @PostMapping("/add")
  public DemoVO add(@RequestBody DemoVO vo) {
    DemoVO result = mapper.poToVO(demoService.add(mapper.voToPO(vo)));
    return result;
  }

  @ApiOperation(value = "修改", notes = "修改单条数据的信息")
  @PostMapping("/update")
  public DemoVO update(@RequestBody DemoVO vo) {
    DemoVO result = mapper.poToVO(demoService.update(mapper.voToPO(vo),vo.getId()));
    return result;
  }

  @ApiOperation(value = "删除", notes = "根据id删除单条或多条数据")
  @PostMapping("/delete")
  public RespMessage delete(@RequestBody List<String> ids) {
    demoService.delete(ids);
    return RespMessage.ok();
  }

}
