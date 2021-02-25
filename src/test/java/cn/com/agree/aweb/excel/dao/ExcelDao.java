package cn.com.agree.aweb.excel.dao;

import cn.com.agree.aweb.excel.entity.po.ExcelPO;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

/**
 * Excel使用示例：Dao接口
 */
@Repository
public interface ExcelDao extends JpaRepositoryImplementation<ExcelPO, Long> {

}
