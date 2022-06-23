package edu.zjnu.biz;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @description: BaseOp
 * @author: 杨海波
 * @date: 2022-06-01 20:21
 **/
@Component("demoOperation")
@Scope("singleton")
public class DemoOperation {

    @Override
    public String toString() {
        return "demoOperation{}";
    }
}
