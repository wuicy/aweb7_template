package cn.com.agree.aweb.controller;

import cn.com.agree.aweb.common.base.entity.RespMessage;
import cn.com.agree.aweb.entity.mapper.AuthorityMapper;
import cn.com.agree.aweb.entity.po.AuthorityPO;
import cn.com.agree.aweb.entity.query.QueryEntity;
import cn.com.agree.aweb.entity.vo.AuthorityVO;
import cn.com.agree.aweb.service.AuthorityService;
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

@Api(description = "权限管理")
@RestController
@RequestMapping("/authority")
@PreAuthorize("hasAuthority('authorityManagement')")
public class AuthorityController {

  @Autowired
  AuthorityService authorityService;

  AuthorityMapper mapper = AuthorityMapper.INSTANCE;

  @ApiOperation(value = "列出所有权限", notes = "查找并以list形式列出所有权限")
  @GetMapping("/list")
  public List<AuthorityVO> list() {
    return mapper.poToVO(authorityService.findAll());
  }

//  @ApiOperation(value = "列出所有权限", notes = "查找并以菜单树的形式列出所有权限")
//  @GetMapping("/tree")
//  public List<AuthorityVO> tree() {
//    return AuthorityTreeUtil.getSubTree(null, mapper.poToVO(authorityService.findAll()));
//  }

  @ApiOperation(value = "根据条件排序分页列出所有权限（模糊查询）", notes = "类型为字符串的属性进行模糊查询，排序分页列出所有权限")
  @PostMapping("/list")
  public Page<AuthorityVO> listByQuery(@RequestBody QueryEntity<AuthorityVO> queryEntity) {
    return authorityService.findByQuery(queryEntity, mapper);
  }

  @ApiOperation(value = "根据条件排序分页列出所有权限（匹配查询）", notes = "类型为字符串的属性进行匹配查询，单个字段模糊匹配即可，排序分页列出所有权限")
  @PostMapping("/list/matching")
  public Page<AuthorityVO> listByString(@RequestBody QueryEntity<String> queryEntity)
      throws IllegalAccessException, InstantiationException {
    return authorityService.findByString(AuthorityPO.class, queryEntity, mapper);
  }


  @ApiOperation(value = "获取单个权限详情", notes = "根据id获取权限信息")
  @GetMapping("/get/{id}")
  public AuthorityVO get(@PathVariable("id") String id) {
    return mapper.poToVO(authorityService.findById(id).orElse(null));
  }

//  @ApiOperation(value = "获取单个权限详情", notes = "根据id获取权限及其相关子权限")
//  @GetMapping("/get/{id}")
//  public AuthorityVO get(@PathVariable("id") String id) {
//    List<AuthorityVO> voList = mapper.poToVO(authorityService.findAll());
//    AuthorityVO parent = null;
//    for (AuthorityVO vo : voList) {
//      //查找权限是子权限的时候
//      if (vo.getId().equals(id) && vo.getParentId() != null) {
//        parent = new AuthorityVO();
//        parent.setId(vo.getParentId());
//      }
//    }
//    List<AuthorityVO> result = AuthorityTreeUtil.getSubTree(parent, voList);
//    for (AuthorityVO vo : result) {
//      if (vo.getId().equals(id)) {
//        return vo;
//      }
//    }
//    return null;
//  }

  @ApiOperation(value = "添加权限", notes = "添加单个权限")
  @PostMapping("/add")
  public AuthorityVO add(@RequestBody AuthorityVO vo) {
    AuthorityVO result = mapper.poToVO(authorityService.add(mapper.voToPO(vo)));
    return result;
  }

  @ApiOperation(value = "修改权限", notes = "修改单个权限的相关信息")
  @PostMapping("/update")
  public AuthorityVO update(@RequestBody AuthorityVO vo) {
    AuthorityVO result = mapper.poToVO(authorityService.update(mapper.voToPO(vo),vo.getId()));
    return result;
  }

  @ApiOperation(value = "删除权限", notes = "根据id删除单个或多个权限")
  @PostMapping("/delete")
  public RespMessage delete(@RequestBody List<String> ids) {
    authorityService.delete(ids);
    return RespMessage.ok();
  }

}
