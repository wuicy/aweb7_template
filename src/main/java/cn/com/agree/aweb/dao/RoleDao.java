package cn.com.agree.aweb.dao;

import cn.com.agree.aweb.entity.po.RolePO;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepositoryImplementation<RolePO, String> {

//  @Query("select r from RolePO r left join fetch r.authorities where r.id in ?1")
//  List<RolePO> findByIdsWithAuthority(List<String> ids);


}
