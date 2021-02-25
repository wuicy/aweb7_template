package cn.com.agree.aweb.demo.service.impl;

import cn.com.agree.aweb.common.base.service.BaseServiceImpl;
import cn.com.agree.aweb.demo.dao.DemoDao;
import cn.com.agree.aweb.demo.entity.po.DemoPO;
import cn.com.agree.aweb.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl extends BaseServiceImpl<DemoPO, String> implements DemoService {

  @Autowired
  private DemoDao demoDao;

  @Override
  public JpaRepositoryImplementation<DemoPO, String> getDao() {
    return demoDao;
  }

}
