package edu.zjnu.core.flow.model;

/**
 * @description: 连接线
 * @author: 杨海波
 * @date: 2022-06-02 19:18
 **/
public class Line {

    private String desc;

    private String condition;

    private String from;

    private String to;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
