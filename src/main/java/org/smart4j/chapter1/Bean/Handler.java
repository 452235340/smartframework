package org.smart4j.chapter1.Bean;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 * Created by qingbowu on 2017/10/11.
 */
public class Handler {

    /**
     * Controller类
     */
    private Class<?> controllerClass;

    private Method actionMethod;

    public Handler(Class<?> controllerClass,Method actionMethod){
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
