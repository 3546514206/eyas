package edu.zjnu.core;

import java.util.Map;

/**
 * @description: 业务总线接口
 * @author: 杨海波
 * @date: 2022-06-02 14:37
 **/
public interface IBizContext {

    /**
     * 设置参数
     *
     * @param key
     * @param value
     */
    void setParam(String key, Object value);

    /**
     * 获取参数
     *
     * @param key
     * @return
     */
    Object getParam(String key);

    /**
     * @return
     */
    Map<String, Object> getAllParams();
}
