package org.smart4j.chapter1.Bean;

import org.smart4j.chapter1.util.CastUtil;

import java.util.Map;

/**
 * 请求参数对象
 * Created by qingbowu on 2017/10/12.
 */
public class Param {

    private  Map<String,Object> paramMap;

    public Param(Map<String,Object> paramMap){
        this.paramMap = paramMap;
    }

    /**
     *根据参数名获取long型参数值
     * @param name
     * @return
     */
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     * @return
     */
    public Map<String,Object> getParamMap(){
        return  paramMap;
    }

}
