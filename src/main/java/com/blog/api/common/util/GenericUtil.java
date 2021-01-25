package com.blog.api.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericUtil {

    // 获取第i个泛型的类型
    public   static Class<?> getActualTypeArgument(Object classObj,int i) {
        {
            Class<?> clazz = classObj.getClass();  // 由于本类是抽象类，所以this 一定是其子类的实例化对象
            Class<?> entitiClass = null;  // 定义返回的class
            Type genericSuperclass = clazz.getGenericSuperclass(); // 利用反射机制的获取其泛化的超类，实际上也就是带有具体的S 和A的类型的超类
            if (genericSuperclass instanceof ParameterizedType) { // 如果本类实现了参数化接口
                // 获取所有的参数化的类型
                Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length > i) {
                    entitiClass = (Class<?>) actualTypeArguments[i];
                }
            }
            return entitiClass;
        }
    }
}
