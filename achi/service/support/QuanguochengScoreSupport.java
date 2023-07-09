package com.deta.achi.service.support;

import com.deta.achi.pojo.AchiWb;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 计算难度系数-项目全过程
 * 项目全过程           管理咨询（外部招标管理系统）
 *
 * @author by diaozhiwei on 2022/08/15.
 * @email diaozhiwei2k@163.com
 */
@Component
public class QuanguochengScoreSupport extends ScoreSupport {

    @Override
    public List<Map<String, Double>> work(Map<String, List<AchiWb>> groupByProjectName, Map<String, Double> allScoreDict, Date startTime, Date endTime) {
        Map<String, Double> map = new HashMap<>();
        double guocheng1 = 0;
        double guocheng2 = 0;
        double guocheng3 = 0;
        double guocheng4 = 0;
        double guocheng5 = 0;
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

                int stepNum = 0;
                for (AchiWb achiWb : singleWbArray) {
                    double projectStepScore = 0;
                    if (allScoreDict.containsKey(projectName + "-" + achiWb.getBiaoduanuid())) {
                        projectStepScore = allScoreDict.get(projectName + "-" + achiWb.getBiaoduanuid());
                    }
                    if (stepNum == 0) {
                        String projectJd = achiWb.getProjectJd();
                        if (projectJd != null) {
                            if (projectJd.contains("投融资决策咨询") || projectJd.contains("编写项目建议书") || projectJd.contains("申报材料")) {
                                guocheng1 = guocheng1 + scoreWithFlag(hasWeiFaLuanJi, 40);
                                projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 40);
                                projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 40);
                            }
                            if (projectJd.contains("列入国家外资贷款项目备选规划")) {
                                guocheng2 = guocheng2 + scoreWithFlag(hasWeiFaLuanJi, 60);
                                projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 60);
                                projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 60);
                            }
                            if (projectJd.contains("列入贷款机构三年滚动计划清单")) {
                                guocheng3 = guocheng3 + scoreWithFlag(hasWeiFaLuanJi, 60);
                                projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 60);
                                projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 60);
                            }
                            if (projectJd.contains("贷款机构对项目评估")) {
                                guocheng4 = guocheng4 + scoreWithFlag(hasWeiFaLuanJi, 60);
                                projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 60);
                                projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 60);
                            }
                            if (projectJd.contains("贷款谈判及草签贷款协定") || projectJd.contains("项目协议")) {
                                guocheng5 = guocheng5 + scoreWithFlag(hasWeiFaLuanJi, 20);
                                projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 20);
                                projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 20);
                            }
                        }

                    }
                    stepNum++;
                    allScoreDict.put(projectName + "-" + achiWb.getBiaoduanuid(), projectStepScore);
                }
            }
            allScoreDict.put(projectName, projectScore);
        }

        map.put("投融资决策咨询、编写项目建议书和申报材料", guocheng1);
        map.put("列入国家外资贷款项目备选规划", guocheng2);
        map.put("列入贷款机构三年滚动计划清单", guocheng3);
        map.put("贷款机构对项目评估", guocheng4);
        map.put("贷款谈判及草签贷款协定、项目协议", guocheng5);

        List<Map<String, Double>> objects = new ArrayList<>();
        objects.add(map);
        return objects;
    }
}
