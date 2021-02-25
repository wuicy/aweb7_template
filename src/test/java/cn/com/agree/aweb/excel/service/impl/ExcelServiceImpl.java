package cn.com.agree.aweb.excel.service.impl;

import cn.com.agree.aweb.common.base.service.ExcelBaseServiceImpl;
import cn.com.agree.aweb.excel.dao.ExcelDao;
import cn.com.agree.aweb.excel.entity.po.ExcelPO;
import cn.com.agree.aweb.excel.entity.vo.ExcelVO;
import cn.com.agree.aweb.excel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;

/**
 * Excel使用示例：Service实现
 *
 * 不使用Excel功能时继承改为BaseServiceImpl即可
 */
@Service
public class ExcelServiceImpl extends ExcelBaseServiceImpl<ExcelPO, Long> implements ExcelService {

  @Autowired
  private ExcelDao excelDao;

  //使用Excel需要使用这个方法获取PO的Class对象
  @Override
  public Class getPOClass() {
    return ExcelPO.class;
  }

  //使用Excel需要使用这个方法获取VO的Class对象
  @Override
  public Class getVOClass() {
    return ExcelVO.class;
  }

  @Override
  public JpaRepositoryImplementation<ExcelPO, Long> getDao() {
    return excelDao;
  }
}
