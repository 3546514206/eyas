package edu.zjnu.core.flow.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 表示flow
 * @author: 杨海波
 * @date: 2022-06-02 19:20
 **/
public class Diagram {

    private String id;

    private String desc;

    private List<Line> lines = new ArrayList<>();

    private List<Node> nodes = new ArrayList<>();

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
