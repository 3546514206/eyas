package edu.zjnu.core;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 业务总线，为原型bean，为每次执行flow创建新的总线
 *
 *  线程安全考虑，总线设计为原型bean
 * @author: 杨海波
 * @date: 2022-06-02 14:37
 **/
@Component
@Scope("prototype")
public class BizContext implements IBizContext {

    /**
     * 数据存储对象
     */
    private Map<String, Object> store = new HashMap<>();

    /**
     * 设置数据
     *
     * @param key
     * @param value
     */
    @Override
    public void setParam(String key, Object value) {
        this.store.put(key, value);
    }

    @Override
    public Object getParam(String key) {
        return this.store.get(key);
    }

    @Override
    public Map<String, Object> getAllParams() {
        return store;
    }
}
