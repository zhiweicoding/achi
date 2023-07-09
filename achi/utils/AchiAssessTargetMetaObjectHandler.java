package com.deta.achi.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author zzs
 * @version 1.0.0
 * @description 填充字段
 * @date 2023-01-06 19:45
 */
//@Component
public class AchiAssessTargetMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("is_deleted", "0", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
