package com.max.tse.algorithm.leetcode;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-7-25
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates.
 * https://leetcode.com/problems/two-sum/
 *
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.

 You may assume that each input would have exactly one solution.
 */
public class TwoSum {

    /**
     * 哈希存位置，O(n)
     * */
    public static Integer[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        List<Integer> resultList = new ArrayList<Integer>();

        for (int i = 0; i < nums.length; i++) {
            if (indexMap.containsKey(target - nums[i])) {
                resultList.add(i);
                resultList.add(indexMap.get(target - nums[i]));
            }

            indexMap.put(nums[i], i);

        }
        return resultList.toArray(new Integer[]{});
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,3,4,3,5};
        System.out.println(JSON.toJSONString(twoSum(nums, 5)));
    }
}
