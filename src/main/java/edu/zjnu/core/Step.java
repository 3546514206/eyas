package edu.zjnu.core;

import edu.zjnu.core.op.EndOperation;
import edu.zjnu.core.op.IOperation;
import edu.zjnu.core.op.StartOperation;
import edu.zjnu.core.support.EyasConstant;
import edu.zjnu.core.utils.ElUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 步骤的java表述
 * @author: 杨海波
 * @date: 2022-06-02 15:14
 **/
@Component
@Scope("prototype")
public class Step {

    private static final Logger logger = LoggerFactory.getLogger(FlowDispatch.class);

    private FlowDispatch flowDispatch;

    private String elString;

    private String id;

    private IOperation operation;

    private String desc;

    private String type;

    private List<Step> nextSteps = new ArrayList<>();

    /**
     * 执行定义在Operation中的业务逻辑
     *
     * @param ctx
     */
    public void run(IBizContext ctx) {
        if (operation instanceof StartOperation) {
            logger.info("开始执行交易【{}】业务逻辑", this.flowDispatch.getFlowName());
        }

        operation.exec(ctx);
        if (operation instanceof EndOperation) {
            logger.info("交易【{}】业务逻辑执行完毕", this.flowDispatch.getFlowName());
            return;
        }

        // 如果是条件节点
        if (EyasConstant.CONDITION_STR.equals(this.type)) {
            Step nextStep = null;
            for (Step step : nextSteps) {
                if (ElUtil.parse(ctx.getAllParams(), step.getElString())) {
                    nextStep = step;
                    break;
                }
            }

            if (null == nextStep) {
                logger.info("交易【{}】执行失败，条件节点【{}】不存在符合条件的下一个节点",
                        this.flowDispatch.getFlowName(),
                        this.desc);
            } else {
                nextStep.run(ctx);
            }
        } else {
            // 非条件节点 nextSteps 大小必为 1
            this.nextSteps.get(EyasConstant.ZERO).run(ctx);
        }


    }

    public String getElString() {
        return elString;
    }

    public void setElString(String elString) {
        this.elString = elString;
    }

    public FlowDispatch getFlowDispatch() {
        return flowDispatch;
    }

    public void setFlowDispatch(FlowDispatch flowDispatch) {
        this.flowDispatch = flowDispatch;
    }

    public List<Step> getNextSteps() {
        return nextSteps;
    }

    public void setNextSteps(List<Step> nextSteps) {
        this.nextSteps = nextSteps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IOperation getOperation() {
        return operation;
    }

    public void setOperation(IOperation operation) {
        this.operation = operation;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
