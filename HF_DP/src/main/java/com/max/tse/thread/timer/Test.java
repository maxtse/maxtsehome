package com.max.tse.thread.timer;

import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-3-3
 * Time: 下午3:51
 * To change this template use File | Settings | File Templates.
 */
public class Test {


    public static int[] testInt = new int[9];

    public static int[] testResultInt = new int[10];

    public static int size = 0;

    static {
        testInt[0] = 9;
        testInt[1] = 8;
        testInt[2] = 6;
        testInt[3] = 7;
        testInt[4] = 5;
        testInt[5] = 3;
        testInt[6] = 4;
        testInt[7] = 1;
        testInt[8] = 2;

    }

    public static void testFixUp() {

        for (int i = 0; i < 9; i++) {
            testResultInt[++size] =  testInt[i];
            fixUp(size);
        }

    }

    public static void main(String[] args) {
        testFixUp();
        for (int i = 0; i < testResultInt.length; i++) {
            System.out.println("i = " + i + " and value=" + testResultInt[i]);
        }

    }

    private static void fixUp(int k) {
        while (k > 1) {
            int j = k >> 1;
            if (testResultInt[j] <= testResultInt[k])
                break;
            int tmp = testResultInt[j];  testResultInt[j] = testResultInt[k]; testResultInt[k] = tmp;
            k = j;
        }
    }


}


