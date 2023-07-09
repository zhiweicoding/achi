package com.deta.achi.service.support;

import com.deta.achi.pojo.AchiWb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author by diaozhiwei on 2022/08/17.
 * @email diaozhiwei2k@163.com
 */
public abstract class ScoreSupport {

    public abstract List<Map<String, Double>> work(Map<String, List<AchiWb>> groupByProjectName, Map<String, Double> allScoreDict, Date startTime, Date endTime);

    //把值放到临时map中，计算
    public static Object tempInsertMap(Map<String, List<Object>> map, String key, double value, boolean flag) {
        if (map.containsKey(key)) {
            List<Object> objects = map.get(key);
            if (objects == null) {
                objects = new ArrayList<>();
            }
            objects.add(scoreWithFlag(flag, value));
        } else {
            List<Object> objects = new ArrayList<>();
            objects.add(scoreWithFlag(flag, value));
            map.put(key, objects);
        }
        return scoreWithFlag(flag, value);
    }

    public static Object tempInsertMap(Map<String, List<Object>> map, String key, int value, boolean flag) {
        if (map.containsKey(key)) {
            List<Object> objects = map.get(key);
            if (objects == null) {
                objects = new ArrayList<>();
            }
            objects.add(scoreWithFlag(flag, value));
        } else {
            List<Object> objects = new ArrayList<>();
            objects.add(scoreWithFlag(flag, value));
            map.put(key, objects);
        }
        return scoreWithFlag(flag, value);
    }

    //有违法乱纪就不得分
    public static double scoreWithFlag(boolean flag, double obj) {
        return flag ? obj : 0;
    }

    public static int scoreWithFlag(boolean flag, int obj) {
        return flag ? obj : 0;
    }

    public static boolean isTimeInGap(Long timestamp, Date start, Date end) {
        if (timestamp != null) {
            long startL = start.getTime();
            long endL = end.getTime();
            return timestamp >= startL && timestamp <= endL;
        } else {
            return false;
        }
    }

    public static boolean isYes(String obj) {
        return obj != null && obj.equals("是");
    }
}
