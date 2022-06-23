package edu.zjnu.core.flow;

import edu.zjnu.core.FlowDispatch;

/**
 * @description: TODO
 * @author: 杨海波
 * @date: 2022-06-02 19:01
 **/
public interface IParser {

    /**
     * 解析flow
     *
     * @param flowDispatch
     * @param flowFilePath
     */
    void parse(FlowDispatch flowDispatch, String flowFilePath);
}
