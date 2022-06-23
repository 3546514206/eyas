package edu.zjnu;

import edu.zjnu.core.BizContext;
import edu.zjnu.core.Eyas;
import edu.zjnu.core.FlowFactory;
import edu.zjnu.core.IBizContext;
import edu.zjnu.core.support.IFlowTool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 应用测试
 * @author: 杨海波
 * @date: 2022-06-04 02:17
 **/
public class EyasTest {

    private static final Logger logger = LoggerFactory.getLogger(FlowFactory.class);

    /**
     * 应用信息
     */
    @Test
    public void testEyas() {
        Eyas eyas = Eyas.getApp();
        assert eyas != null;
        logger.info(eyas.getName());
    }

    @Test
    public void testRunFlow() {
        IBizContext ctx = new BizContext();
        ctx.setParam("name", "杨海波");
        Eyas.getApp().getApplicationContext().getBean(FlowFactory.class).getFlow("demo2").run(ctx);
    }

    @Test
    public void testRunFlowV2() {
        IBizContext ctx = new BizContext();
        ctx.setParam("name", "杨海波");
        ctx.setParam("age", 18);
        Eyas.getApp().getApplicationContext().getBean(IFlowTool.class).run("demo2", ctx);
        Eyas.getApp().getApplicationContext().getBean(IFlowTool.class).run("demo2", ctx);
    }

    @Test
    public void testRunFlowV3() {
        IBizContext ctx = new BizContext();
        ctx.setParam("name", "杨海波");
        ctx.setParam("age", 18);
        Eyas.getApp().getApplicationContext().getBean(IFlowTool.class).run("demo2", ctx);
    }
}
