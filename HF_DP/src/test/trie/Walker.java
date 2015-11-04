package test.trie;

import java.util.List;

/**
 * Created by huawei_yang on 15/10/29.
 */
public interface Walker<P, D> {

    void walk(List<P> pathElements, NodeDataConsumer<D> nodeDataConsumer);

    interface NodeDataConsumer<D> {
        void consumer(D nodeData);

        void event(WalkErrorEvent walkErrorEvent);

    }

    class WalkErrorEvent<P> {

        private P pathElements;
        private String message;

        public WalkErrorEvent(P pathElements, String message) {
            this.pathElements = pathElements;
            this.message = message;
        }

        public P getPathElements() {
            return pathElements;
        }

        public void setPathElements(P pathElements) {
            this.pathElements = pathElements;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
