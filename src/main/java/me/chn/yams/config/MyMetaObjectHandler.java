package me.chn.yams.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import me.chn.yams.common.entity.BaseEntity;
import me.chn.yams.security.kit.SecurityKit;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        if (originalObject instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) originalObject;
            baseEntity.setCreateTime(LocalDateTime.now());
            baseEntity.setCreateUser(SecurityKit.getUserId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        if (originalObject instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) originalObject;
            baseEntity.setUpdateTime(LocalDateTime.now());
            baseEntity.setUpdateUser(SecurityKit.getUserId());
        }
    }

}
