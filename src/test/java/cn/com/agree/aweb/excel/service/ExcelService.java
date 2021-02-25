package cn.com.agree.aweb.excel.service;

import cn.com.agree.aweb.common.base.service.ExcelBaseService;
import cn.com.agree.aweb.excel.entity.po.ExcelPO;

/**
 * Excel使用示例：Service接口
 *
 * 不使用Excel功能时继承改为BaseService即可
 */
public interface ExcelService extends ExcelBaseService<ExcelPO,Long> {

}
