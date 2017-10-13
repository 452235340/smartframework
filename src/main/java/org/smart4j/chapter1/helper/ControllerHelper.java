package org.smart4j.chapter1.helper;

import org.smart4j.chapter1.Bean.Handler;
import org.smart4j.chapter1.Bean.Request;
import org.smart4j.chapter1.annotation.Action;
import org.smart4j.chapter1.util.ArrayUtil;
import org.smart4j.chapter1.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 * Created by qingbowu on 2017/10/12.
 */
public final class ControllerHelper {

    /**
     *用于存放请求与处理器的映射关系(简称 Action Map)
     */
    private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();


    static {
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if(CollectionUtil.isNotEmpty(controllerClassSet)){
            //遍历Controller类
            for (Class<?> controllerClass : controllerClassSet){
                //获取Controller类中定义的方法
                Method[] methods = controllerClass.getDeclaredMethods();
                if(ArrayUtil.isNotEmpty(methods)){
                    //遍历Controller类中的方法
                    for (Method method : methods){
                        //判断当前方法是否有Action注解
                        if(method.isAnnotationPresent(Action.class)){
                            //从注解中获取URL映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证url映射规则
                            if (mapping.matches("\\w+:/\\w* ")){
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2){
                                    //获取请求方法和路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    //初始化Request和Handler
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler
                                            = new Handler(controllerClass, method);
                                    //初始化ACTION_MAP
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }


}
