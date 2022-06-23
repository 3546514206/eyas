package edu.zjnu.core.op;

import edu.zjnu.core.IBizContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description: 结束步骤
 * @author: 杨海波
 * @date: 2022-06-02 14:51
 **/
@Component("endOperation")
@Scope("singleton")
public class EndOperation implements IOperation {

    @Override
    public void exec(IBizContext ctx) {
        logger.info("endOperation");
    }
}
