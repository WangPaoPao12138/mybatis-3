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
package org.apache.ibatis.reflection.property;

import java.util.Locale;

import org.apache.ibatis.reflection.ReflectionException;

/**
 * Property属性名工具类
 *
 * @author Clinton Begin
 */
public final class PropertyNamer {

  private PropertyNamer() {
    // Prevent Instantiation of Static Class
  }

  /**
   *
   * @param name 方法名
   * @return 属性名
   */
  public static String methodToProperty(String name) {
    //根据不同类型方法截断方法名
    //is方法
    if (name.startsWith("is")) {
      name = name.substring(2);
      //get set方法
    } else if (name.startsWith("get") || name.startsWith("set")) {
      name = name.substring(3);
      //只能处理 is get set方法抛出异常
    } else {
      throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
    }

    //根据截断后的字符 首字母小写
    if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
      name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
    }

    return name;
  }

  /**
   * 判断是否是is get set方法
   * @param name
   * @return
   */
  public static boolean isProperty(String name) {
    return name.startsWith("get") || name.startsWith("set") || name.startsWith("is");
  }

  /**
   * 判断是否是is get方法
   * @param name
   * @return
   */
  public static boolean isGetter(String name) {
    return name.startsWith("get") || name.startsWith("is");
  }

  /**
   * 判断是否是set方法
   * @param name
   * @return
   */
  public static boolean isSetter(String name) {
    return name.startsWith("set");
  }

}
