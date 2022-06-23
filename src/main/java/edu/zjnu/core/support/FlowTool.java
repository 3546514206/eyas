package edu.zjnu.core.support;

import edu.zjnu.core.Eyas;
import edu.zjnu.core.FlowFactory;
import edu.zjnu.core.IBizContext;
import edu.zjnu.core.IFlowDispatch;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description: 执行flow的工具类
 * @author: 杨海波
 * @date: 2022-06-02 14:59
 **/
@Component
@Scope("singleton")
public class FlowTool implements IFlowTool {

    @Override
    public void run(String transCode, IBizContext context) {
        IFlowDispatch flow = Eyas.getApp().getApplicationContext().getBean(FlowFactory.class).getFlow(transCode);
        flow.run(context);
    }
}
