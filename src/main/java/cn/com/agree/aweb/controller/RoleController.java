package cn.com.agree.aweb.controller;

import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.entity.mapper.RoleMapper;
import cn.com.agree.aweb.entity.po.AuthorityPO;
import cn.com.agree.aweb.entity.po.MenuPO;
import cn.com.agree.aweb.entity.po.RolePO;
import cn.com.agree.aweb.entity.query.QueryEntity;
import cn.com.agree.aweb.entity.vo.RoleVO;
import cn.com.agree.aweb.service.RoleService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "角色管理")
@RestController
@RequestMapping("/role")
@PreAuthorize("hasAuthority('roleManagement')")
public class RoleController {

  @Autowired
  RoleService roleService;

  RoleMapper mapper = RoleMapper.INSTANCE;

  @ApiOperation(value = "列出所有角色", notes = "查找并以list形式列出所有角色")
  @GetMapping("/list")
  public List<RoleVO> list() {
    return mapper.poToVO(roleService.findAll());
  }

  @ApiOperation(value = "根据条件排序分页列出所有角色（模糊查询）", notes = "对类型为字符串的属性进行模糊查询，排序分页列出所有角色")
  @PostMapping("/list")
  public Page<RoleVO> listByQuery(@RequestBody QueryEntity<RoleVO> queryEntity) {
    return roleService.findByQuery(queryEntity, mapper);
  }

  @ApiOperation(value = "根据条件排序分页列出所有角色（匹配查询）", notes = "类型为字符串的属性进行匹配查询，单个字段模糊匹配即可，排序分页列出所有角色")
  @PostMapping("/list/matching")
  public Page<RoleVO> listByString(@RequestBody QueryEntity<String> queryEntity)
      throws IllegalAccessException, InstantiationException {
    return roleService.findByString(RolePO.class, queryEntity, mapper);
  }

  @ApiOperation(value = "获取单个角色详情", notes = "根据id获取角色详情")
  @GetMapping("/get/{id}")
  public RoleVO get(@PathVariable("id") String id) {
    return mapper.poToVO(roleService.findById(id).orElse(null));
  }

  @ApiOperation(value = "添加角色", notes = "添加单个角色")
  @PostMapping("/add")
  public RoleVO add(@RequestBody @Valid RoleVO vo) {
    vo.setAuthorities(null);
    return mapper.poToVO(roleService.add(mapper.voToPO(vo)));
  }

  @ApiOperation(value = "修改角色", notes = "修改单个角色的相关信息")
  @PostMapping("/update")
  public RoleVO update(@RequestBody @Valid RoleVO vo) {
    vo.setAuthorities(null);
    return mapper.poToVO(roleService.update(mapper.voToPO(vo), vo.getId()));
  }

  @ApiOperation(value = "关联权限", notes = "对单个角色和任意权限进行关联，id为角色id，ids为权限id集合")
  @PostMapping("/update/authority/{id}")
  public RoleVO updateAuthority(@PathVariable("id") String id,
      @RequestBody(required = false) List<String> ids) {
    RolePO rolePO = new RolePO();
    rolePO.setId(id);
    rolePO.setAuthorities(new ArrayList<>());
    for (String authorityId : ids) {
      rolePO.getAuthorities().add(new AuthorityPO().setId(authorityId));
    }
    return mapper.poToVO(roleService.update(rolePO, id));
  }

  @ApiOperation(value = "关联菜单", notes = "对单个角色和任意菜单进行关联，id为角色id，ids为菜单id集合")
  @PostMapping("/update/menu/{id}")
  public RoleVO updateMenu(@PathVariable("id") String id,
      @RequestBody(required = false) List<String> ids) {
    RolePO rolePO = new RolePO();
    rolePO.setId(id);
    rolePO.setMenus(new ArrayList<>());
    for (String menuId : ids) {
      rolePO.getMenus().add(new MenuPO().setId(menuId));
    }
    return mapper.poToVO(roleService.update(rolePO, id));
  }

  @ApiOperation(value = "删除角色", notes = "根据id删除单个或多个角色")
  @PostMapping("/delete")
  public RespMessage delete(@RequestBody List<String> ids) {
    roleService.delete(ids);
    return RespMessage.ok();
  }

  @ApiOperation("下载导入模板")
  @GetMapping("/template")
  public void excelTemplate(HttpServletResponse response) throws Exception {
    roleService.template(response);
  }

  @ApiOperation("导入角色")
  @PostMapping("/import")
  public Object excelUpload(MultipartFile file) throws Exception {
    roleService.upload(file, mapper);
    return RespMessage.ok();
  }

  @ApiOperation("导出角色（excel）")
  @PostMapping("/export/excel")
  public void excelDownload(@RequestBody List<RoleVO> data, HttpServletResponse response)
      throws IOException {
    //此处仅作为示例，通过前端把导出数据传过来是不安全的。
    //正确的做法应该是
    //1.前端传来id等查询条件
    //2.使用查询条件调用service查出po list
    //3.把po list转换成vo list
    //4.使用vo list调用导出方法
    roleService.download(data, response);
  }

  @ApiOperation("导出角色（PDF）")
  @PostMapping("/export/pdf")
  public void pdfDownload(@RequestBody List<RoleVO> data, HttpServletResponse response)
      throws IOException {
    //此处仅作为示例，通过前端把导出数据传过来是不安全的。
    //正确的做法应该是
    //1.前端传来id等查询条件
    //2.使用查询条件调用service查出po list
    //3.把po list转换成vo list
    //4.使用vo list调用导出方法
    roleService.pdf(data, response);
  }

  @ApiOperation(value = "修改前端权限", notes = "以保存json字符串的形式修改保存前端权限详情")
  @PostMapping("/update/permissions/{id}")
  public RespMessage updatePermissions(@PathVariable("id") String id,
      @RequestBody(required = false) JSONObject permissions) {
      RolePO rolePO = new RolePO();
      rolePO.setId(id);
      rolePO.setPermissions(permissions.toJSONString());
      return RespMessage.ok(mapper.poToVO(roleService.update(rolePO, id)));
  }

  @ApiOperation(value = "修改前端权限", notes = "以保存json字符串的形式修改保存前端权限详情")
  @GetMapping("/get/permissions/{id}")
  public RespMessage getPermissions(@PathVariable("id") String id) {
    RolePO rolePO=roleService.findById(id).orElse(null);
    if (rolePO!=null){
      return RespMessage.ok(JSON.parseObject(rolePO.getPermissions()));
    }else{
      return RespMessage.error("该角色不存在");
    }
  }

}
