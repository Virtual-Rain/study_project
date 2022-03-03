package com.study.common.data_struct;

import java.util.Arrays;

/**
 * author Afton
 * date 2020/5/30
 */
public class Kmp {
    public static void main(String[] args) {
        String str1 = "ABD CCB ABCDABCD ABABCDABC CCDBAC";
        String str2 = "ABCDABD";

        int index = kmp(str1, str2);
        System.out.println("Index=" + index);
    }

    /**
     * KMP匹配算法
     *
     * @param str1 长字符串
     * @param str2 匹配的字符串
     * @return
     */
    public static int kmp(String str1, String str2) {
        int index = -1;
        if (str1.length() < 1 || str2.length() < 1) {
            return index;
        }
        /*首先得到部分匹配表*/
        int next[] = kmpPartial(str2);
        for (int i = 0, j = 0; i < str1.length(); i++) {
            if (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            if (j == next.length) {
                index = i - j - 1;
                break;
            }
        }

        return index;
    }

    /**
     * KMP部分匹配值
     * 前缀与后缀的最长共有元素长度
     * 例如 ABCDABD 前缀：A AB ABC ABCD ABCDA ABCDAB 后缀 BCDABD CDABD DABD ABD BD D 共有元素没有 长度0
     * 例如 ABCDAB 前缀：A AB ABC ABCD ABCDA ABCDA 后缀 BCDAB CDAB DAB AB B 共有元素AB 长度2
     *
     * @param str
     * @return 部分匹配数组
     */
    public static int[] kmpPartial(String str) {
        int[] next = new int[str.length()];
        /*str为长度为1 next没有前后缀部分匹配值就为0*/
        next[0] = 0;
        for (int i = 1, j = 0; i < str.length(); i++) {
            /*如果不同还原j的值，核心*/
            if (j > 0 && str.charAt(i) != str.charAt(j)) {
                j = next[j - 1];
            }
            /*出现相同*/
            if (str.charAt(i) == str.charAt(j)) {
                j++;
            }
            next[i] = j;
        }

        return next;
    }
}
