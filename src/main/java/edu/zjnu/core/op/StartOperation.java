package edu.zjnu.core.op;

import edu.zjnu.core.IBizContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description: 开始步骤
 * @author: 杨海波
 * @date: 2022-06-02 14:47
 **/
@Component("startOperation")
@Scope("singleton")
public class StartOperation implements IOperation {

    @Override
    public void exec(IBizContext ctx) {
        logger.info("startOperation");
    }
}
