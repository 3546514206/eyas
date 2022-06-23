package edu.zjnu.core;

import edu.zjnu.core.exception.EyasException;
import edu.zjnu.core.flow.FlowParser;
import edu.zjnu.core.op.StartOperation;
import edu.zjnu.core.support.EyasConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: flow 的 Java表述
 * @author: 杨海波
 * @date: 2022-06-02 15:16
 **/
@Component
@Scope("prototype")
public class FlowDispatch implements IFlowDispatch {

    private static final Logger logger = LoggerFactory.getLogger(FlowDispatch.class);

    private final List<Step> steps = new ArrayList<>();
    private String flowName;
    private String flowId;
    private String flowDesc;
    ;

    /**
     * 初始化
     *
     * @param transCode
     */
    @Override
    public void init(String transCode) {

        URL propertiesUrl = this.getClass().getClassLoader().getResource(EyasConstant.PROPERTIES_CONFIG_LOCATION);
        if (null == propertiesUrl) {
            throw new EyasException("flow根目录加载失败");
        }
        String propertiesPath = propertiesUrl.getPath();
        // 类路径
        String classPath = propertiesPath.substring(EyasConstant.ZERO, propertiesPath.length() - EyasConstant.PROPERTIES_CONFIG_LOCATION.length());
        // flow 文件
        String flowFilePath = classPath + EyasConstant.FLOW_ROOT_PATH + transCode + EyasConstant.FLOW_FILE_SUFFIX;

        FlowParser flowParser = Eyas.getApp().getApplicationContext().getBean(FlowParser.class);
        flowParser.parse(this, flowFilePath);
    }

    @Override
    public void run(IBizContext ctx) {
        logger.info("交易【{}】开始执行", this.flowId);
        // 获取开始节点
        Step start = this.steps.stream().filter(e -> e.getOperation() instanceof StartOperation).collect(Collectors.toList()).get(EyasConstant.ZERO);
        start.run(ctx);
        logger.info("交易【{}】执行完毕", this.flowId);
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowDesc() {
        return flowDesc;
    }

    public void setFlowDesc(String flowDesc) {
        this.flowDesc = flowDesc;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
