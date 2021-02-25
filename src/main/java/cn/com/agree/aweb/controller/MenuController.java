package cn.com.agree.aweb.controller;

import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.entity.mapper.MenuMapper;
import cn.com.agree.aweb.entity.po.MenuPO;
import cn.com.agree.aweb.entity.query.QueryEntity;
import cn.com.agree.aweb.entity.vo.MenuVO;
import cn.com.agree.aweb.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "菜单管理")
@RestController
@RequestMapping("/menu")
@PreAuthorize("hasAuthority('menuManagement')")
public class MenuController {

  @Autowired
  MenuService menuService;

  MenuMapper mapper = MenuMapper.INSTANCE;

  @ApiOperation(value = "列出所有菜单", notes = "查找并以list形式列出所有菜单")
  @GetMapping("/list")
  public List<MenuVO> list() {
    return mapper.poToVO(menuService.findAll());
  }

//  @ApiOperation(value = "列出所有权限", notes = "查找并以菜单树的形式列出所有权限")
//  @GetMapping("/tree")
//  public List<AuthorityVO> tree() {
//    return AuthorityTreeUtil.getSubTree(null, mapper.poToVO(authorityService.findAll()));
//  }

  @ApiOperation(value = "根据条件排序分页列出所有菜单（模糊查询）", notes = "对类型为字符串的属性进行模糊查询，排序分页列出所有菜单")
  @PostMapping("/list")
  public Page<MenuVO> listByQuery(@RequestBody QueryEntity<MenuVO> queryEntity) {
    return menuService.findByQuery(queryEntity, mapper);
  }

  @ApiOperation(value = "根据条件排序分页列出所有菜单（匹配查询）", notes = "一类型为字符串的属性进行匹配查询，单个字段模糊匹配即可，排序分页列出所有菜单")
  @PostMapping("/list/matching")
  public Page<MenuVO> listByString(@RequestBody QueryEntity<String> queryEntity)
      throws IllegalAccessException, InstantiationException {
    return menuService.findByString(MenuPO.class, queryEntity, mapper);
  }


  @ApiOperation(value = "获取单个菜单详情", notes = "根据id获取菜单信息")
  @GetMapping("/get/{id}")
  public MenuVO get(@PathVariable("id") String id) {
    return mapper.poToVO(menuService.findById(id).orElse(null));
  }

  @ApiOperation(value = "添加菜单", notes = "添加单个菜单")
  @PostMapping("/add")
  public MenuVO add(@RequestBody MenuVO vo) {
    MenuVO result = mapper.poToVO(menuService.add(mapper.voToPO(vo)));
    return result;
  }

  @ApiOperation(value = "修改菜单", notes = "修改单个菜单的相关信息")
  @PostMapping("/update")
  public MenuVO update(@RequestBody MenuVO vo) {
    MenuVO result = mapper.poToVO(menuService.update(mapper.voToPO(vo),vo.getId()));
    return result;
  }

  @ApiOperation(value = "删除菜单", notes = "根据id删除单个或多个菜单")
  @PostMapping("/delete")
  public RespMessage delete(@RequestBody List<String> ids) {
    menuService.delete(ids);
    return RespMessage.ok();
  }

}
