/**
 *    Copyright 2009-2018 the original author or authors.
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
package org.apache.ibatis.plugin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PluginTest {

  @Test
  public void mapPluginShouldInterceptGet() {
    Map map = new HashMap();
    map = (Map) new AlwaysMapPlugin().plugin(map);
    assertEquals("Always", map.get("Anything"));
  }

  @Test
  public void shouldNotInterceptToString() {
    Map map = new HashMap();
    map = (Map) new AlwaysMapPlugin().plugin(map);
    assertFalse("Always".equals(map.toString()));
  }

  @Intercepts({
          @Signature(type = Map.class, method = "get", args = {Object.class})}) // <1> 定义了需要拦截的方法为 Map 类型、方法为 "get" 方法，方法参数为 Object.class
  public static class AlwaysMapPlugin implements Interceptor {
    @Override // <4> 拦截方法 此处只会返回 Always
    public Object intercept(Invocation invocation) throws Throwable {
      return "Always";
    }

    @Override // <2> 调用 Plugin#wrap(Object target, Interceptor interceptor) 方法，执行代理对象的创建。
    public Object plugin(Object target) {
      return Plugin.wrap(target, this);
    }

    @Override // <3> 暂未做任何实现。此处可以实现，若 AlwaysMapPlugin 有属性，可以从 properties 获取一些需要的属性值。
    public void setProperties(Properties properties) {
    }
  }

}
