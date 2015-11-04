package test.trie;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Created by huawei_yang on 15/10/29.
 */
public class TrieTree<P, D> {

    private TreeNode<P, D> root = new TreeNode(null);

    public Walker<P, D> createWalker() {

        return new Walker<P, D>() {

            @Override
            public void walk(List<P> pathElements, NodeDataConsumer<D> nodeDataConsumer) {
                Preconditions.checkNotNull(pathElements);
                Preconditions.checkNotNull(nodeDataConsumer);
                TreeNode<P, D> currentNode = root;
                for (P pathElement : pathElements) {
                    TreeNode<P, D> childNode = currentNode.getChild(pathElement);
                    if (childNode == null) {
                        nodeDataConsumer.event(new WalkErrorEvent(pathElement, "无此子节点"
                                + JSON.toJSONString(pathElements)));
                        return;
                    }
                    currentNode = childNode;
                    nodeDataConsumer.consumer(currentNode.getData());
                }

            }
        };
    }

    public TreeNode<P, D> getRoot() {
        return root;
    }

}
