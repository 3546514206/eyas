package edu.zjnu.core.op;

import edu.zjnu.core.IBizContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 所有Op的顶层接口
 * <p>
 * 从设计上看，所有的Op由业务总线串联，而路由功能由 step-> nextStep 完成，
 * 是不需要 NEXT 与 END 之类路由键参与实现路由功能的。
 * <p>
 * 只需要强制用户开发flow的时候显示设置StartOperation与EndOperation即可保证业务流程能有序表达。
 * 执行引擎的逻辑根据当前的Op类型即可启动一个流程或者终结一个流程。
 * <p>
 * 在完成对flow的建模的时候，可以断言IFlowDispatch是已经包含start和end的，对完整业务逻辑的抽象表述。
 * <p>
 * 对于内置Operation，无任何定制化的业务动作，基于性能考虑应该声明为单例bean；
 * 而对于自定义Operation（biz目录下的）,考虑线程安全应该声明为原型bean。
 * @author: 杨海波
 * @date: 2022-06-02 14:44
 **/
public interface IOperation {

//    int NEXT = 1;
//
//    int END = -1;

    Logger logger = LoggerFactory.getLogger(IOperation.class);

    /**
     * 业务逻辑
     *
     * @param ctx
     * @return
     */
    void exec(IBizContext ctx);
}
