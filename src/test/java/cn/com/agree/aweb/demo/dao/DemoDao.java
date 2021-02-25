package cn.com.agree.aweb.demo.dao;

import cn.com.agree.aweb.demo.entity.po.DemoPO;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoDao extends JpaRepositoryImplementation<DemoPO, String> {

}
