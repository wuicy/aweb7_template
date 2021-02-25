package cn.com.agree.aweb.listener;

import cn.com.agree.aweb.common.base.mapper.BaseMapper;
import cn.com.agree.aweb.common.util.ValidationUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * 文件上传的监听, 把dao传入,并在invoke和doAfterAllAnalysed调用dao的save方法即可
 */
public class ExcelUploadListener extends AnalysisEventListener {

  private static final int BATCH_COUNT = 5;
  List list = new ArrayList<>();

  private JpaRepositoryImplementation dao;

  private BaseMapper mapper;

  private Integer line;

  public ExcelUploadListener(JpaRepositoryImplementation dao, BaseMapper mapper) {
    this.dao = dao;
    this.mapper = mapper;
    this.line = 1;
  }

  @Override
  public void invoke(Object object, AnalysisContext analysisContext) {
//    ValidationUtil.allCheckValidate(t, ++line);
//    list.add(mapper.voToPO(t));
    ValidationUtil.allCheckValidate(mapper.poToVO(object), ++line);
    list.add(object);

    // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
    if (list.size() >= BATCH_COUNT) {
      saveData();
      // 存储完成清理 list
      list.clear();
    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    saveData();
  }

  private void saveData() {
    dao.saveAll(list);
  }
}
