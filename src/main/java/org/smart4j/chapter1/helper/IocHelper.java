package org.smart4j.chapter1.helper;

import org.smart4j.chapter1.annotation.Inject;
import org.smart4j.chapter1.util.ArrayUtil;
import org.smart4j.chapter1.util.CollectionUtil;
import org.smart4j.chapter1.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * Created by qingbowu on 2017/10/11.
 */
public final class IocHelper {

    static {
        //获取所有Bean类与Bean实例之间的映射关系(简称Bean Map)
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)){
            //遍历 beanMap
            for(Map.Entry<Class<?>, Object> entryEntity : beanMap.entrySet()){
                Class<?> beanClass = entryEntity.getKey();
                Object beanInstance = entryEntity.getValue();

                //获取Bean类定义的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                if(ArrayUtil.isNotEmpty(beanFields)){
                    //遍历Bean Field
                    for (Field beanField : beanFields){
                        //判断当前Bean Field是否带有Inject注解
                        if(beanField.isAnnotationPresent(Inject.class)){
                            //在Bean Map中获取Bean Field的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (null != beanFieldInstance) {
                                //通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
