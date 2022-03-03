package com.study.common.data_struct;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * author Afton
 * date 2020/5/29
 * 排序算法
 */
public class Sort {


    public static void main(String[] args) {

        dateTest();

//        int[] arr = {0, 23, 0, -10, 0, 0};
//        quickSort1(arr, 0, arr.length - 1);
//        System.out.println(Arrays.toString(arr));
    }

    /**
     * 时间测试
     */
    public static int MAX = 800000;

    public static void dateTest() {
        int[] arr = new int[MAX];
        for (int i = 0; i < MAX; i++) {
            /*生成一个[0,8000000]的数*/
            arr[i] = (int) (Math.random() * 8000000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str1 = simpleDateFormat.format(date1);
        System.out.println("排序前时间：" + str1);

        quickSort(arr, 0, arr.length - 1);

        Date date2 = new Date();
        String str2 = simpleDateFormat.format(date2);
        System.out.println("排序后时间：" + str2);
    }

    /**
     * 冒泡排序
     * 规则：一次循环发现比左边小就交换，循环arr.length-1次
     * 优化:如果一次循环没有任何交换（说明已经排序好了）就退出
     * 时间复杂度O（n^2）
     */
    public static void bubbleSort(int[] arr) {
        int temp = 0;
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            } else {
                flag = false;
            }
        }
    }

    /**
     * 快速排序
     *
     * @param left  左侧开始位
     * @param right 右侧开始位
     *              规则：left向右移动，right向左移动；比中间值小的移动到左边，大的移动到右边；递归左边与右边进行同样操作，一直到所有都有序
     *              递归退出规则left>=right
     */
    public static void quickSort(int arr[], int left, int right) {
        int l = left;
        int r = right;
        int pivot = arr[(left + right) / 2];
        int temp = 0;
        while (l < r) {
            /*pivot左边一直找直到找到比pivot大的值*/
            while (arr[l] < pivot) {
                l += 1;
            }
            /*pivot右边一直找直到找到比pivot小的值*/
            while (arr[r] > pivot) {
                r -= 1;
            }
            /*这一轮已移动完毕*/
            if (l >= r) {
                break;
            }
            /*找到合适的进行交换*/
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            /*arr中有与pivot值相同的很可能会出现死循环{-9,-10,0,0,23,70}},所以相等时 l右移一位，r左移一位 移位后可能退出循环*/
            if (arr[l] == pivot) {
                r -= 1;
            }
            if (arr[r] == pivot) {
                l += 1;
            }
        }
        /*arr中有与pivot值相同的很可能会出现死循环{-9,-10,0,0,23,70}},所以相等时 l右移一位，r左移一位 避免pivot相同的还需要比较*/
        if (l == r) {
            l += 1;
            r -= 1;
        }
        if (left < r) {
            /*左递归*/
            quickSort(arr, left, r);
        }
        if (right > l) {
            /*右递归*/
            quickSort(arr, l, right);
        }
    }


}
