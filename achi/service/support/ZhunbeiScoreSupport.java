package com.deta.achi.service.support;

import com.deta.achi.pojo.AchiWb;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 计算难度系数-招标前期准备
 * 招标前期准备（需求管理）
 *
 * @author by diaozhiwei on 2022/08/15.
 * @email diaozhiwei2k@163.com
 */
@Component
public class ZhunbeiScoreSupport extends ScoreSupport {

    @Override
    public List<Map<String, Double>> work(Map<String, List<AchiWb>> groupByProjectName, Map<String, Double> allScoreDict, Date startTime, Date endTime) {
        Map<String, Double> map = new HashMap<>();

        double zhunbei1 = 0;
        double zhunbei2 = 0;
        double zhunbei3 = 0;
        for (String projectName : groupByProjectName.keySet()) {
            double projectScore = 0;
            if (allScoreDict.containsKey(projectName)) {
                projectScore = allScoreDict.get(projectName);
            }

            List<AchiWb> singleWbArray = groupByProjectName.get(projectName);
            //如果有标段，开始操作
            if (singleWbArray.size() > 0) {
                //有违法乱纪整个项目不得分
                boolean hasWeiFaLuanJi = singleWbArray.stream().filter(bean -> bean.getWeifaweiji() != null).mapToInt(AchiWb::getWeifaweiji).sum() <= 0;
                int jihua = 0;
                int peixun = 0;
                int beian = 0;
                for (AchiWb achiWb : singleWbArray) {
                    double projectStepScore = 0;
                    if (allScoreDict.containsKey(projectName + "-" + achiWb.getBiaoduanuid())) {
                        projectStepScore = allScoreDict.get(projectName + "-" + achiWb.getBiaoduanuid());
                    }
                    if (isYes(achiWb.getCaigoujihua()) && jihua == 0) {
                        zhunbei1 = zhunbei1 + scoreWithFlag(hasWeiFaLuanJi, 10);
                        projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 10);
                        projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 10);
                        jihua++;
                    }
                    if (achiWb.getCaigoupeixunNum() != null) {
                        zhunbei2 = zhunbei2 + scoreWithFlag(hasWeiFaLuanJi, 20*achiWb.getCaigoupeixunNum());
                        projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 20*achiWb.getCaigoupeixunNum());
                        projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 20*achiWb.getCaigoupeixunNum());
                    }
                    if (isYes(achiWb.getCaigoubeian()) && beian == 0) {
                        zhunbei3 = zhunbei3 + scoreWithFlag(hasWeiFaLuanJi, 60);
                        projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 60);
                        projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 60);
                        beian++;
                    }

                    allScoreDict.put(projectName + "-" + achiWb.getBiaoduanuid(), projectStepScore);
                }
            }
            allScoreDict.put(projectName, projectScore);
        }


        map.put("确定采购计划", zhunbei1);
        map.put("采购培训", zhunbei2*groupByProjectName.size());
        map.put("采购备案", zhunbei3);

        List<Map<String, Double>> objects = new ArrayList<>();
        objects.add(map);
        return objects;
    }
}
