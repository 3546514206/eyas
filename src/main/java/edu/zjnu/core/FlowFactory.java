package edu.zjnu.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: Flow工厂
 * @author: 杨海波
 * @date: 2022-06-02 15:10
 **/
@Component("flowFactory")
@Scope("singleton")
public class FlowFactory {

    private static final Logger logger = LoggerFactory.getLogger(FlowFactory.class);

    private final Map<String, IFlowDispatch> flowDispatches = new ConcurrentHashMap<>();

    public IFlowDispatch getFlow(String transCode) {
        IFlowDispatch flowDispatch = this.flowDispatches.get(transCode);

        if (null != flowDispatch) {
            return flowDispatch;
        }

        // 如果是第一次运行的flow，flowFactory必然不存在对应的flowDispatch，不存在则先初始化，注意：FlowDispatch必须是个原型bean
        logger.info("第一次执行交易【{}】，该交易尚未成初始化，现开始初始化该交易", transCode);
        flowDispatch = Eyas.getApp().getApplicationContext().getBean(IFlowDispatch.class);
        flowDispatch.init(transCode);
        this.flowDispatches.put(transCode, flowDispatch);
        logger.info("交易【{}】已经完成初始化", transCode);
        return flowDispatch;
    }
}
