package cn.com.agree.aweb.dao;

import cn.com.agree.aweb.entity.po.AuthorityPO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityDao extends JpaRepositoryImplementation<AuthorityPO, String>,JpaSpecificationExecutor<AuthorityPO>{

}
