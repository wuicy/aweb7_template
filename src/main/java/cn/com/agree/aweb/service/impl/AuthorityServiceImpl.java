package cn.com.agree.aweb.service.impl;

import cn.com.agree.aweb.common.base.service.BaseServiceImpl;
import cn.com.agree.aweb.dao.AuthorityDao;
import cn.com.agree.aweb.entity.po.AuthorityPO;
import cn.com.agree.aweb.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl extends BaseServiceImpl<AuthorityPO, String> implements
    AuthorityService {

  @Autowired
  private AuthorityDao authorityDao;

  @Override
  public JpaRepositoryImplementation<AuthorityPO, String> getDao() {
    return authorityDao;
  }

}
