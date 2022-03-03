package com.study.common;

import com.study.common.entity.BettingRecord;
import com.study.common.entity.PlayData;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:zx on 2019/9/2511:00
 */
public class MockData {

    public static List<BettingRecord> getBettingRecord(int pageIndex, int pageSize, int limitNum) {
        List<BettingRecord> list = new ArrayList<>();
        if (pageIndex * pageSize > limitNum){
            return list;
        }
        for (int i = 0; i < pageSize; i++) {
            list.add(new BettingRecord("第" + pageIndex + "页：时时彩", "前三-对子", "201909" + pageIndex + i + "期", "500" + pageIndex * i, "10000" + i));
        }
        return list;
    }

    public static List<PlayData> getPlayData() {
        List<PlayData> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            PlayData data = new PlayData();
            String name="大小盒子";
//            if(i>5){
//                name+="小和值";
//            }
            data.setPlayName(name);
            data.setPlayCode("1中3." + i);
            list.add(data);
        }
        return list;
    }
}
