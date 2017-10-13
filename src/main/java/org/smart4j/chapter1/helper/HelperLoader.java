package org.smart4j.chapter1.helper;

import org.smart4j.chapter1.util.ClassUtil;

/**
 * 加载相应的Helper类
 * Created by qingbowu on 2017/10/12.
 */
public final class HelperLoader {

    /**
     * 实际上，在第一次访问类时就会下载类的static代码块，这里只是为了让加载更集中，所以写了一个HelperLoader
     */
    public static void init(){
        Class<?>[] classList = {
            ClassHelper.class,
            BeanHelper.class,
            IocHelper.class,
            ControllerHelper.class
        };
        for (Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName(),false);
        }
    }
}
