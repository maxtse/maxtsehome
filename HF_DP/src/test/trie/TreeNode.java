package test.trie;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by huawei_yang on 15/10/29.
 */
public class TreeNode<P, D> {

    private Map<P, TreeNode<P, D>> childrenNodes = Maps.newConcurrentMap();

    private D data;

    public D getData() {
        return data;
    }

    public TreeNode(D data) {
        this.data = data;
    }

    public void addChild(P pathElement, TreeNode<P, D> childNode) {
        this.childrenNodes.put(pathElement, childNode);
    }

    public TreeNode<P, D> getChild(P pathElement) {
        return this.childrenNodes.get(pathElement);
    }

}
