package edu.zjnu.biz;

import edu.zjnu.core.IBizContext;
import edu.zjnu.core.op.IOperation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description: workingOperation
 * @author: 杨海波
 * @date: 2022-06-04 18:26
 **/
@Component("workingOperation")
@Scope("prototype")
public class WorkingOperation implements IOperation {

    @Override
    public void exec(IBizContext ctx) {
        logger.info("workingOperation");
    }
}
