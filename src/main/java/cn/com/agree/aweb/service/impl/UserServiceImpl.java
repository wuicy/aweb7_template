package cn.com.agree.aweb.service.impl;

import cn.com.agree.aweb.common.base.service.BaseServiceImpl;
import cn.com.agree.aweb.dao.UserDao;
import cn.com.agree.aweb.entity.DefaultUserDetails;
import cn.com.agree.aweb.entity.po.UserPO;
import cn.com.agree.aweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserPO, String> implements UserService,
    UserDetailsService {

  @Autowired
  private UserDao userDao;

  @Override
  public JpaRepositoryImplementation<UserPO, String> getDao() {
    return userDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new DefaultUserDetails(userWithDetail(username));
  }

  private UserPO userWithDetail(String username) {
//    UserPO userPO = Optional.ofNullable(userDao.findByNameWithRoles(username))
//        .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    UserPO userPO = Optional.ofNullable(userDao.findByName(username))
        .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    return userPO;
  }

  @Override
  public UserPO findByName(String username){
    return userDao.findByName(username);
  }

}
