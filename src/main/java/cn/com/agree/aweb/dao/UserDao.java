package cn.com.agree.aweb.dao;

import cn.com.agree.aweb.entity.po.UserPO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepositoryImplementation<UserPO, String> {

  UserPO findByName(String name);

  @Query("select u from UserPO u left join fetch u.roles where u.name=?1")
  UserPO findByNameWithRoles(String name);

}
