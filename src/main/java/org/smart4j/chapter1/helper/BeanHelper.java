package org.smart4j.chapter1.helper;

import org.smart4j.chapter1.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *Bean 助手类
 * Created by qingbowu on 2017/10/11.
 */
public final class BeanHelper {


    /**
     * 定义Bean映射(用于存放Bean类与Bean实例的映射关系)
     */
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>,Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> cls : beanClassSet){
            Object instance = ReflectionUtil.newInstance(cls);
            BEAN_MAP.put(cls,instance);
        }
    }


    /**
     * 获取Bean映射
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     * @param cls
     * @return
     */
    public static Object getBeanInstance(Class<?> cls){
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class:"+cls);
        }
        return BEAN_MAP.get(cls);
    }

}
