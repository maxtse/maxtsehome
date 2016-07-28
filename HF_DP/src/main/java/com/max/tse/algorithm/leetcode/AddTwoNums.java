package com.max.tse.algorithm.leetcode;

import com.max.tse.algorithm.leetcode.po.ListNode;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-7-25
 * Time: 下午7:09
 * To change this template use File | Settings | File Templates.
 */
public class AddTwoNums {

    public static ListNode addTwoNums(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        int cur = 0;
        while (l1 != null || l2 != null || cur > 0) {
            int val = l1.val + l2.val + cur;
            val = val %10;
            cur = val / 10;
            ListNode currentListNode = new ListNode(val);


        }
        return null;
    }




      }
