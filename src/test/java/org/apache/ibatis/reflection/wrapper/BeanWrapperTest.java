package org.apache.ibatis.reflection.wrapper;

import org.apache.ibatis.domain.misc.CustomBeanWrapperFactory;
import org.apache.ibatis.domain.misc.RichType;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author Gin
 * @description
 * @date 2023/7/12 23:19
 */
public class BeanWrapperTest {
    @Test
    public void test01() {
        RichType object = new RichType();

        if (true) {
            object.setRichType(new RichType());
            object.getRichType().setRichMap(new HashMap());
            object.getRichType().getRichMap().put("nihao", null);
        }

        MetaObject meta = MetaObject.forObject(object, SystemMetaObject.DEFAULT_OBJECT_FACTORY, new CustomBeanWrapperFactory(), new DefaultReflectorFactory());
        MetaObject meta2 = MetaObject.forObject(null, SystemMetaObject.DEFAULT_OBJECT_FACTORY, new CustomBeanWrapperFactory(), new DefaultReflectorFactory());
        Class<?> clazz = meta.getObjectWrapper().getGetterType("richType.richMap.nihao");
        Class<?> clazz2 = meta2.getObjectWrapper().getGetterType("richType.richMap.nihao");
        System.out.println(meta == SystemMetaObject.NULL_META_OBJECT);
        System.out.println(clazz);
        System.out.println(meta2 == SystemMetaObject.NULL_META_OBJECT);
        //System.out.println(clazz2);--报错
        /*
        org.apache.ibatis.reflection.ReflectionException: There is no getter for property named 'richType' in 'class java.lang.Class'
         */
    }
}
