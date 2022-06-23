package edu.zjnu.core.utils;

import edu.zjnu.core.FlowDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;

/**
 * @description: ElUtil
 * @author: 杨海波
 * @date: 2022-06-04 18:39
 **/
public class ElUtil {

    private static final Logger logger = LoggerFactory.getLogger(FlowDispatch.class);

    /**
     * 执行el表达式
     *
     * @param express
     * @param map
     * @return
     */
    public static boolean parse(Map map, String express) {
        if (StringUtils.isEmpty(express) || map.isEmpty()) {
            logger.info("解析el表达式失败，参数不得为空！");
            return false;
        }
        //创建一个EL解析器
        ExpressionParser parser = new SpelExpressionParser();
        TemplateParserContext templateParserContext = new TemplateParserContext("${", "}");
        SpelExpression expression = (SpelExpression) parser.parseExpression(express, templateParserContext);
        //设置动态参数
        StandardEvaluationContext context = new StandardEvaluationContext();
        MapAccessor propertyAccessor = new MapAccessor();
        context.setVariables(map);
        context.setPropertyAccessors(Arrays.asList(propertyAccessor));
        expression.setEvaluationContext(context);
        return expression.getValue(map, boolean.class);
    }
}
