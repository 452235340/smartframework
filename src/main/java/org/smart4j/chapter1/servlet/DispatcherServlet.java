package org.smart4j.chapter1.servlet;

import org.smart4j.chapter1.Bean.Handler;
import org.smart4j.chapter1.helper.BeanHelper;
import org.smart4j.chapter1.helper.ConfigHelper;
import org.smart4j.chapter1.helper.ControllerHelper;
import org.smart4j.chapter1.helper.HelperLoader;
import org.smart4j.chapter1.util.ClassUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 * Created by qingbowu on 2017/10/12.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关Helper类
        HelperLoader.init();
        //获取ServletContext对象(用于注册Servlet)
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理jsp的用于注册Servlet
        ServletRegistration jspServlet= servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源默认的Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }


    @Override
    public void service(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
        //获取请求方法和路径
        String method = request.getMethod().toLowerCase();
        String pathInfo = request.getPathInfo();
        //获取Action处理器
        Handler handler = ControllerHelper.getHandler(method, pathInfo);
        if (null != handler){
            //获取Controller类及Bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBeanInstance(controllerClass);
            //创建请求参数对象
            Map<String,Object> paramMap = new HashMap<String,Object>();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String paramName = parameterNames.nextElement();
                String parameValue = request.getParameter(paramName);
                paramMap.put(paramName,parameValue);
            }

        }
    }
}
