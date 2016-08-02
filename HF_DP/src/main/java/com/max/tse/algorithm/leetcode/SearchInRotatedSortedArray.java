package com.max.tse.algorithm.leetcode;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-7-25
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 * 循环左移数组里查找某个数字
 * Suppose a sorted array is rotated at some pivot unknown to you beforehand.

 (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 */
public class SearchInRotatedSortedArray {

    public static int search(int[] nums, int target) {

        int left = 0;
        int right = nums.length -1;
        while(left < right) {
            int mid = left + right >> 1;
            //找到了
            if (nums[mid] == target) {
                return mid;
            }
            //mid前面的是有序的
            if (nums[left] < nums[mid]) {
                if (target < nums[mid] && target >= nums[left]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }


        }
        return nums[left] == target ? left : -1;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {4,5,6,7,1,2,3};
        System.out.println(search(nums, 5));
    }
}
