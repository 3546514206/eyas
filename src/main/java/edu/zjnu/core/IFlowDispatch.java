package edu.zjnu.core;

/**
 * @description: flow 的 Java表述
 * @author: 杨海波
 * @date: 2022-06-02 15:12
 **/
public interface IFlowDispatch {

    /**
     * 初始化flow
     *
     * @param transCode
     */
    void init(String transCode);

    /**
     * @param ctx
     */
    void run(IBizContext ctx);
}
