package edu.zjnu.core.flow.model;

/**
 * @description: flow 文件的 node 节点
 * @author: 杨海波
 * @date: 2022-06-02 19:18
 **/
public class Node {

    private String id;

    private String beanId;

    private String desc;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
