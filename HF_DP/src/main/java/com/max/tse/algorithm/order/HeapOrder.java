package com.max.tse.algorithm.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Random;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-26
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 * Note:堆排序
 */
public class HeapOrder {
    /**
     * 构造测试数据
     * 总个数
     * @param length
     * 最小值
     * @param leftValue
     * 最大值
     * @param rightValue
     * @return
     * */
    private int[] getTestData(int length, int leftValue, int rightValue) {
        Preconditions.checkArgument(length > 0, "数组长度必须大于0");
        Preconditions.checkArgument(rightValue > leftValue, "区间左右范围值大小不匹配");
        Preconditions.checkArgument( rightValue - leftValue >= length);

        int[] result = new int[length + 1];//结果
        int offset = 0 - leftValue;//偏移量
        int randomMaxValue = offset + rightValue;//随机数最大的值
        Set<Integer> valueSet = Sets.newHashSet();

        for (int i = 1; i < length+1; i++) {
            int value = new Random().nextInt(randomMaxValue);
            if (valueSet.contains(value)) {
                i = i - 1;
                continue;
            }
            valueSet.add(value);
            result[i] = value - offset;
        }
        return result;
    }

    private void testGetTestData() {
        System.out.println("test leftValue < 0, rightValue > 0");
        int[] test1 = getTestData(10, -2, 10);
        System.out.println("test1 : " + JSON.toJSONString(test1));

        System.out.println("test leftValue = 0, rightValue > 0");
        int[] test2 = getTestData(10, 0, 10);
        System.out.println("test2 : " + JSON.toJSONString(test2));

        System.out.println("test leftValue > 0, rightValue > 0");
        int[] test3 = getTestData(10, 1, 20);
        System.out.println("test3 : " + JSON.toJSONString(test3));

        System.out.println("test leftValue < 0, rightValue = 0");
        int[] test4 = getTestData(10, -20, 0);
        System.out.println("test4 : " + JSON.toJSONString(test4));

    }

    private void heapJust(int[] array, int start, int end) {//1 2
        Preconditions.checkArgument(start <= end);
        Preconditions.checkArgument(end <= array.length-1);

        int temp = array[start];
        for (int i = start * 2; i <= end; i *= 2) {//从子节点开始判断 找到temp应该的位置
            if (i < end && array[i] < array[i+1]) {//如果子节点还有一个兄弟节点并且比第一个子节点大
                i++;//兄弟节点下标
            }
            if (temp > array[i]) {//已经是堆了 ok
                break;
            }
            array[start] = array[i];//交换
            start = i;//从替换节点的子节点继续判断
        }
        array[start] = temp;//把判断的节点放到合适的地方
    }

    public void order(int length, int leftValue, int rightValue) {
        int[] orderingData = getTestData(length, leftValue, rightValue);
        System.out.println("orderingData= " +  JSON.toJSONString(orderingData));

        //init heap
        for (int i = orderingData.length / 2; i > 0; i--) {//初始化堆
            heapJust(orderingData, i, orderingData.length - 1);
        }
        System.out.println("init heap data= " + JSON.toJSONString(orderingData));

        //order
        for (int i = orderingData.length-1; i > 1; i--) {//依次交互 排序
            int temp = orderingData[1];
            orderingData[1] = orderingData[i];
            orderingData[i] = temp;
            heapJust(orderingData, 1, i - 1);
        }
        System.out.println("sort data = " + JSON.toJSONString(orderingData));


    }
    public static void main(String[] args) {
        HeapOrder heapOrder = new HeapOrder();
        heapOrder.testGetTestData();

        int[] testData = heapOrder.getTestData(10, 1, 20);
        System.out.println("testData =" + JSON.toJSONString(testData));
        //heapOrder.heapJust(testData, 1, testData.length);
        //System.out.println(JSON.toJSONString(testData));

        for (int i = testData.length / 2; i > 0; i--) {//初始化堆
            heapOrder.heapJust(testData, i, testData.length - 1);
            System.out.println("i = " + i + JSON.toJSONString(testData));
        }

        for (int i = testData.length-1; i > 1; i--) {//依次交互 排序
            int temp = testData[1];
            testData[1] = testData[i];
            testData[i] = temp;
            heapOrder.heapJust(testData, 1, i - 1);
            System.out.println("sort i = " + i + JSON.toJSONString(testData));
        }




    }
}
