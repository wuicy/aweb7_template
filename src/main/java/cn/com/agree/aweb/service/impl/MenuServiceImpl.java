package cn.com.agree.aweb.service.impl;

import cn.com.agree.aweb.common.base.service.BaseServiceImpl;
import cn.com.agree.aweb.dao.MenuDao;
import cn.com.agree.aweb.entity.po.MenuPO;
import cn.com.agree.aweb.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuPO, String> implements
    MenuService {

  @Autowired
  private MenuDao menuDao;

  @Override
  public JpaRepositoryImplementation<MenuPO, String> getDao() {
    return menuDao;
  }

}
