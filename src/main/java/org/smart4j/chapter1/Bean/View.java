package org.smart4j.chapter1.Bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 * Created by qingbowu on 2017/10/12.
 */
public class View {

    /**
     * 视图路径
     */
    private String path;

    /**
     * 模型数据
     */
    private Map<String,Object> model;

    public View(String path){
        this.path = path;
        this.model = new HashMap<String,Object>();
    }

    public View addModel(String key,Object value){
        model.put(key,value);
        return this;
    }

    public String getPath(){
        return path;
    }

    public Map<String,Object> getModel(){
        return model;
    }
}
