package com.study.commonlibrary.uitls;

import java.util.List;

/**
 * Author:zx on 2019/9/2714:11
 */
public class CommonUtils {
    public static <D> boolean isEmpty(List<D> list) {
        return list == null || list.isEmpty();
    }
}
