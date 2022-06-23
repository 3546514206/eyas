package edu.zjnu.core.support;

import edu.zjnu.core.IBizContext;

/**
 * @description: 执行类接口类
 * @author: 杨海波
 * @date: 2022-06-02 15:00
 **/
public interface IFlowTool {

    /**
     * @param transCode
     * @param context
     */
    void run(String transCode, IBizContext context);
}
