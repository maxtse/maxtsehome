package com.max.tse.reflect.tairTree;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-12
 * Time: 下午5:48
 * To change this template use File | Settings | File Templates.
 * Note:树节点
 */
public class DataNode<P, T> {

    private Map<P, DataNode<P,T>> childNodes;//后继

    private T data;//数据

    private String desc;//描述信息

    private String path;//根路径

    public DataNode(T data, String desc, String path) {
        this.data = data;
        this.desc = desc;
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<P, DataNode<P, T>> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(Map<P, DataNode<P, T>> childNodes) {
        this.childNodes = childNodes;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
