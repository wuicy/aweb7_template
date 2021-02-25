package cn.com.agree.aweb.service.impl;

import cn.com.agree.aweb.common.base.service.ExcelBaseServiceImpl;
import cn.com.agree.aweb.dao.RoleDao;
import cn.com.agree.aweb.entity.po.RolePO;
import cn.com.agree.aweb.entity.vo.RoleVO;
import cn.com.agree.aweb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ExcelBaseServiceImpl<RolePO, String> implements RoleService {

  @Autowired
  private RoleDao roleDao;

  @Override
  public JpaRepositoryImplementation<RolePO, String> getDao() {
    return roleDao;
  }

  @Override
  public Class getPOClass() {
    return RolePO.class;
  }

  @Override
  public Class getVOClass() {
    return RoleVO.class;
  }
}
