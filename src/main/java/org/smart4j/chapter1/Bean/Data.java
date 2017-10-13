package org.smart4j.chapter1.Bean;

/**
 * 返回数据对象
 * Created by qingbowu on 2017/10/12.
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
