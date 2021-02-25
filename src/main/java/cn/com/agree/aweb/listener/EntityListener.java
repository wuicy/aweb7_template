package cn.com.agree.aweb.listener;

import cn.com.agree.aweb.common.util.AuthUtils;
import cn.com.agree.aweb.common.base.entity.BasePO;
import java.util.Optional;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class EntityListener {

    @PrePersist
    public void prePersist(Object object) {
        Optional.ofNullable(object).filter(item -> item instanceof BasePO).map(BasePO.class::cast).ifPresent(this::prePersist);
    }

    @PreUpdate
    public void preUpdate(Object object) {
        Optional.ofNullable(object).filter(item -> item instanceof BasePO).map(BasePO.class::cast).ifPresent(this::preUpdate);
    }

    private BasePO prePersist(BasePO basePO) {
        return preUpdate(basePO).setCreateTime(basePO.getUpdateTime())
            .setCreateUserId(basePO.getUpdateUserId())
            .setCreateUserName(basePO.getUpdateUserName());
    }

    private BasePO preUpdate(BasePO basePO) {
        String userId = AuthUtils.getCurrentUserId();
        String userName = AuthUtils.getCurrentUserName();
        String nowTime = System.currentTimeMillis() + "";
        return basePO.setUpdateUserId(userId).setUpdateUserName(userName).setUpdateTime(nowTime);
    }
}
