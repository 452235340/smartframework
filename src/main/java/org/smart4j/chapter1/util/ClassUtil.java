package org.smart4j.chapter1.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 * Created by qingbowu on 2017/10/11.
 */
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }


    /**
     * 加载类(为了提高加载类的性能，可将isInitialized设置为false)
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className,boolean isInitialized){
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure",e);
            throw  new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取指定包名下的所有类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if(null != url){
                    String protocol = url.getProtocol();
                     if("file".equals(protocol)){
                         String packagePath = url.getPath().replace("%20", "");
                         addClass(classSet,packagePath,packageName);
                     }else if("jar".equals(protocol)){
                         JarURLConnection jarURLConnection =(JarURLConnection)url.openConnection();
                         if(null != jarURLConnection){
                             JarFile jarFile = jarURLConnection.getJarFile();
                             if(null != jarFile){
                                 Enumeration<JarEntry> jarEntries = jarFile.entries();
                                 while (jarEntries.hasMoreElements()) {
                                     JarEntry jarEntry = jarEntries.nextElement();
                                     String jarEntryName = jarEntry.getName();
                                     if(jarEntryName.endsWith(".class")){
                                         String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", "/");
                                         doAddClass(classSet,className);
                                     }
                                 }
                             }
                         }
                     }
                }
            }
        } catch (Exception e) {
            LOGGER.error("get class set failure",e);
            throw new RuntimeException(e);
        }
        return classSet;
    }


    /**
     * 递归获取所有类的集合
     * @param classSet
     * @param packagePath
     * @param packageName
     */
    private static void addClass(Set<Class<?>> classSet,String packagePath,String packageName){
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for (File file:files){
            String fileName = file.getName();
            if(file.isFile()){
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)){
                    className = packageName + "." + className;
                }
                doAddClass(classSet,className);
            }  else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)){
                    subPackagePath =  packagePath + "/" + subPackagePath;
                }
                addClass(classSet,packagePath,packageName);
            }

        }
    }


    private static void doAddClass(Set<Class<?>> classSet,String className){
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }





}
