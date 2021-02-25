package cn.com.agree.aweb.service;

import cn.com.agree.aweb.common.base.service.BaseService;
import cn.com.agree.aweb.entity.po.UserPO;

public interface UserService extends BaseService<UserPO, String>   {

    UserPO findByName(String username);

}
