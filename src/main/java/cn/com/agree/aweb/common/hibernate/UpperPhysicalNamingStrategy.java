package cn.com.agree.aweb.common.hibernate;

import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * @author: Fanzhendong fanzhendong@agree.com.cn
 * @version: 1.0
 * @create: 2020/5/21 11:34
 * @description: UpperPhysicalNamingStrategy
 **/
public class UpperPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {

  @Override
  protected boolean isCaseInsensitive(JdbcEnvironment jdbcEnvironment) {
    return false;
  }
}
