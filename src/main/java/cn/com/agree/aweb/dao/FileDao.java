package cn.com.agree.aweb.dao;

import cn.com.agree.aweb.entity.po.FilePO;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface FileDao extends JpaRepositoryImplementation<FilePO, String> {

}
