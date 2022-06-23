package edu.zjnu.core.op;

import edu.zjnu.core.IBizContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 杨海波
 * @date: 2022-06-03 22:56
 **/
@Component("emptyOperation")
@Scope("singleton")
public class EmptyOperation implements IOperation {

    @Override
    public void exec(IBizContext ctx) {
        logger.info("emptyOperation");
    }
}
