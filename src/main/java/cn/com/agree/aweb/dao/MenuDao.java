package cn.com.agree.aweb.dao;

import cn.com.agree.aweb.entity.po.MenuPO;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao extends JpaRepositoryImplementation<MenuPO, String> {

}
