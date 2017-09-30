package org.smart4j.chapter1.util;



/**
 * 转型工具类
 * Created by qingbowu on 2017/9/30.
 */
public class CastUtil {

    /**
     * 转为String类型
     * @param obj
     * @return
     */
    public static String castString(Object obj){
        return castString(obj,"");
    }

    /**
     * 转为String类型(提供默认值)
     * @param obj
     * @param defaultValue
     * @return
     */
    public static String castString(Object obj,String defaultValue){
        return null == obj ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为int型
     * @param obj
     * @return
     */
    public static int castInt(Object obj){
        return castInt(obj,0);
    }

    /**
     * 转为int型(提供默认值)
     * @param obj
     * @return
     */
    public static int castInt(Object obj,int defaultValue){
        int intValue = defaultValue;
        if(obj != null ){
            String strVale = castString(obj);
            if(StringUtil.isNotEmpty(strVale)){
                try {
                    intValue= Integer.parseInt(strVale);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }


    /**
     * 转为布尔类型
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj){
        return castBoolean(obj,false);
    }

    /**
     * 转为布尔类型(提供默认值)
     * @param obj
     * @param defaultValue
     * @return
     */
    public static boolean castBoolean(Object obj,boolean defaultValue){
        boolean booleanValue = defaultValue;
        if(null != obj){
            booleanValue = Boolean.parseBoolean(castString(obj));
        }

        return booleanValue;
    }
}
