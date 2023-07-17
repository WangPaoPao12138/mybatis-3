/**
 *    Copyright 2009-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
