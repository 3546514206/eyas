package edu.zjnu.core.flow;

import edu.zjnu.core.Eyas;
import edu.zjnu.core.FlowDispatch;
import edu.zjnu.core.Step;
import edu.zjnu.core.exception.EyasException;
import edu.zjnu.core.flow.model.Diagram;
import edu.zjnu.core.flow.model.Line;
import edu.zjnu.core.flow.model.Node;
import edu.zjnu.core.op.IOperation;
import edu.zjnu.core.support.EyasConstant;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 解析flow文件获取业务流程定义并组装到flowDispatch中，完成由图形语言描述到Java语言描述的转化
 * @author: 杨海波
 * @date: 2022-06-02 18:58
 **/
@Component
@Scope("singleton")
public class FlowParser implements IParser {

    private static final Logger logger = LoggerFactory.getLogger(FlowParser.class);

    @Override
    public void parse(FlowDispatch flowDispatch, String flowFilePath) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(flowFilePath));
            doParse(flowDispatch, document);
        } catch (DocumentException e) {
            throw new EyasException("flow文档解析发生异常");
        }

    }

    private void doParse(FlowDispatch flowDispatch, Document document) {
        Diagram diagram = new Diagram();
        // 解析diagram元素
        Element diagramElement = doParseDiagram(diagram, document);
        // 解析节点
        doParseNodes(diagram, diagramElement);
        // 解析线条
        doParseLines(diagram, diagramElement);
        // 组装FlowDispatch对象
        buildFlowDispatch(flowDispatch, diagram);
    }

    /**
     * 根据 flow 的 信息拼装业务流程的抽象表述，这个过程可以叫做 flow建模
     *
     * @param flowDispatch
     * @param diagram
     */
    private void buildFlowDispatch(FlowDispatch flowDispatch, Diagram diagram) {
        // 设置flow本身的属性
        flowDispatch.setFlowName(diagram.getId());
        assert diagram.getId() != null;
        assert !EyasConstant.BLANK.equals(diagram.getId());
        assert diagram.getId().contains(EyasConstant.FLOW_FILE_SUFFIX);
        flowDispatch.setFlowId(diagram.getId().substring(EyasConstant.ZERO, diagram.getId().length() - EyasConstant.FLOW_FILE_SUFFIX.length()));
        flowDispatch.setFlowDesc(diagram.getDesc());

        logger.info("开始对交易【{}】进行flow建模", flowDispatch.getFlowId());

        // 处理nodes
        for (Node node : diagram.getNodes()) {
            Step step = Eyas.getApp().getApplicationContext().getBean(Step.class);
            step.setId(node.getId());
            IOperation operation = (IOperation) Eyas.getApp().getApplicationContext().getBean(node.getBeanId());
            assert null != operation;
            step.setOperation(operation);
            step.setType(node.getType());
            step.setDesc(node.getDesc());
            step.setFlowDispatch(flowDispatch);
            flowDispatch.getSteps().add(step);
        }

        // 处理lines，拼装 step 的 nextSteps
        for (Line line : diagram.getLines()) {
            String formStepId = line.getFrom();
            String toStepId = line.getTo();
            List<Step> fromSteps = flowDispatch.getSteps().stream().filter(step -> Objects.equals(formStepId, step.getId())).collect(Collectors.toList());
            // formString 必是唯一的
            assert fromSteps.size() == EyasConstant.ONE;
            Step fromStep = fromSteps.get(EyasConstant.ZERO);
            List<Step> toSteps = flowDispatch.getSteps().stream().filter(step -> Objects.equals(toStepId, step.getId())).collect(Collectors.toList());
            // 每根line必然只连接一个toStep
            assert toSteps.size() == EyasConstant.ONE;
            toSteps.get(EyasConstant.ZERO).setElString(line.getCondition());
            fromStep.getNextSteps().addAll(toSteps);
        }
        logger.info("完成对交易【{}】的flow建模", flowDispatch.getFlowId());
    }

    private Element doParseDiagram(Diagram diagram, Document document) {
        Element diagramElement = document.getRootElement();
        diagram.setId(diagramElement.attributeValue(EyasConstant.ID_STR));
        diagram.setDesc(diagramElement.attributeValue(EyasConstant.DESC_STR));
        return diagramElement;
    }

    /**
     * 解析线条
     *
     * @param diagram
     * @param diagramElement
     */
    private void doParseLines(Diagram diagram, Element diagramElement) {
        // lines 元素
        Element linesElement = diagramElement.element(EyasConstant.LINES_STR);
        // line 元素集合
        List<Element> lineElements = linesElement.elements();
        // 遍历
        for (Element lineElement : lineElements) {
            Line line = new Line();
            // desc
            line.setDesc(lineElement.attributeValue(EyasConstant.DESC_STR));
            // condition
            line.setCondition(lineElement.attributeValue(EyasConstant.CONDITION_STR));
            // from
            line.setFrom(lineElement.attributeValue(EyasConstant.FROM_STR));
            // to
            line.setTo(lineElement.attributeValue(EyasConstant.TO_STR));
            diagram.getLines().add(line);
        }
    }

    /**
     * 解析节点
     *
     * @param diagram
     * @param diagramElement
     */
    private void doParseNodes(Diagram diagram, Element diagramElement) {
        // nodes 元素
        Element nodesElement = diagramElement.element(EyasConstant.NODES_STR);
        // node 元素集合
        List<Element> nodeElements = nodesElement.elements();
        // 遍历处理每个node元素
        for (Element nodeElement : nodeElements) {
            Node node = new Node();
            node.setId(nodeElement.attributeValue(EyasConstant.ID_STR));
            // desc
            Element descElement = nodeElement.element(EyasConstant.DESC_STR);
            if (null != descElement) {
                node.setDesc(descElement.getText());
            }
            // beanID
            Element beanIdElement = nodeElement.element(EyasConstant.BEAN_ID_STR);
            if (null != beanIdElement) {
                node.setBeanId(beanIdElement.getText());
            }
            // type
            Element typeElement = nodeElement.element(EyasConstant.TYPE_STR);
            if (null != typeElement) {
                node.setType(typeElement.getText());
            }
            // 增加一个Node
            diagram.getNodes().add(node);
        }
    }
}
