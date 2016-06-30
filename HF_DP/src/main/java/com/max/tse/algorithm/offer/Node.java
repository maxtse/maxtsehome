package com.max.tse.algorithm.offer;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-28
 * Time: 下午7:22
 * To change this template use File | Settings | File Templates.
 */
public class Node {

    private int data;

    private Node next;

    public Node(int data, Node next) {
        this.data = data;
        this.next = next;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    private static Node generateTestList(int size) {
        if (size < 0) {
            throw new RuntimeException("please give me a size");
        }
        Node head = new Node(new Random().nextInt(900) + 100, null);
        if (size == 1) {
            return head;
        }
        Node before = head;
        for (int i = 1; i < size; i++) {
            before.setNext(new Node(new Random().nextInt(900) + 100, null));
            before = before.getNext();
        }
        return head;
    }

    private static void printNode(Node head) {
        System.out.println(".....");
        if (head == null) {
            return;
        }

        for (Node node = head; node != null; node = node.next) {
            System.out.println("node value=" + node.data);
        }
    }

    private static Node reverseList(Node head) {
        if (head == null) {//链表为空
            return null;
        }

        if (head.getNext() == null){//只有头节点
            return head;
        } else {
            Node currentHead = head;
            Node headNext = head.getNext();
            head = reverseList(headNext);
            headNext.setNext(currentHead);
            currentHead.setNext(null);



        }
        return head;
    }

    private static Node reverseListLoop(Node head) {
        if (head == null) {
            return null;
        }

        if (head.getNext() == null) {
            return head;
        }
        Node p = head;//left
        Node q = head.getNext();//right

        Node newHead = null;
        while(q != null) {//right !=null
            Node next = q.getNext();//right.right
            q.setNext(p);//right.right=left
            p = q;
            q = next;//
            if (q == null) {
                newHead = p;
            }


        }
        head.setNext(null);
        return newHead;


    }

    public static void main(String[] args) {
        Node head = generateTestList(10);
        printNode(head);
        Node reverseHead = reverseList(head);
        printNode(reverseHead);

        Node toReverse = reverseListLoop(reverseHead);
        printNode(toReverse);




    }
}
