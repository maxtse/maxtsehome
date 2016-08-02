package com.max.tse.reflect.tairTree;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-12
 * Time: 下午5:47
 * To change this template use File | Settings | File Templates.
 * Note:树定义
 */
public class TrieTree<P, T> {

    private DataNode<P, T> root = new DataNode<P, T>(null, "root", null);//根节点

    public void addNode(DataNode node) {

        if (root == null) {
            root = node;
        }
    }

    /**
     * 拆分出一个类的所有成员变量
     * 一直到java基本类型或者集合类型
     *
     *
     *
     * */



}
