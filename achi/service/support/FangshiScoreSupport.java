package com.deta.achi.service.support;

import com.deta.achi.pojo.AchiWb;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;


/**
 * 计算难度系数-招标方式
 * 招标方式（采购管理）
 *
 * @author by diaozhiwei on 2022/08/15.
 * @email diaozhiwei2k@163.com
 */
@Component
public class FangshiScoreSupport extends ScoreSupport {

    @Override
    public List<Map<String, Double>> work(Map<String, List<AchiWb>> groupByProjectName, Map<String, Double> allScoreDict, Date startTime, Date endTime) {
        Map<String, Double> map = new HashMap<>();

        BigDecimal score40x25 = new BigDecimal(0);
        BigDecimal score35x20 = new BigDecimal(0);
        BigDecimal score30x18 = new BigDecimal(0);
        BigDecimal score15 = new BigDecimal(0);
        BigDecimal score10 = new BigDecimal(0);
        BigDecimal score7 = new BigDecimal(0);
        BigDecimal score5 = new BigDecimal(0);

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

                if (hasWeiFaLuanJi) {

                    int stepNum = 0;
                    for (AchiWb achiWb : singleWbArray) {
                        double projectStepScore = 0;
                        double projectStepScoreFour = 0;
                        String zbfs = achiWb.getZbfs();
                        if (allScoreDict.containsKey(projectName + "-" + achiWb.getBiaoduanuid())) {
                            projectStepScore = allScoreDict.get(projectName + "-" + achiWb.getBiaoduanuid());
                        }
                        if (zbfs != null) {
                            //新项目跟踪 外部项目备案到采购管理中
                            if (stepNum == 0) {
                                if (zbfs.contains("贷款项目国际公开咨询服务选聘QCBS") || zbfs.contains("国际公开资格预审") || zbfs.contains("国际公开竞争性招标")) {
                                    score40x25 = score40x25.add(new BigDecimal(40));
                                    projectStepScore = projectStepScore + 40;
                                    projectScore = projectScore + 40;
                                    projectStepScoreFour = projectStepScoreFour + 40;
                                }
                                if (zbfs.contains("贷款项目国际公开咨询服务选聘CQS") || zbfs.contains("前审合同的国内公开竞争性招标") || zbfs.contains("国内公开资格预审")) {
                                    score35x20 = score35x20.add(new BigDecimal(35));
                                    projectStepScore = projectStepScore + 35;
                                    projectScore = projectScore + 35;
                                    projectStepScoreFour = projectStepScoreFour + 35;
                                }
                                if (zbfs.contains("贷款项目国内公开竞争性招标") || zbfs.contains("邀请招标")) {
                                    score30x18 = score30x18.add(new BigDecimal(30));
                                    projectStepScore = projectStepScore + 30;
                                    projectScore = projectScore + 30;
                                    projectStepScoreFour = projectStepScoreFour + 30;
                                }
                            } else {
                                if (zbfs.contains("贷款项目国际公开咨询服务选聘QCBS") || zbfs.contains("国际公开资格预审") || zbfs.contains("国际公开竞争性招标")) {
                                    score40x25 = score40x25.add(new BigDecimal(25));
                                    projectStepScore = projectStepScore + 25;
                                    projectScore = projectScore + 25;
                                    projectStepScoreFour = projectStepScoreFour + 25;
                                }
                                if (zbfs.contains("贷款项目国际公开咨询服务选聘CQS") || zbfs.contains("前审合同的国内公开竞争性招标") || zbfs.contains("国内公开资格预审")) {
                                    score40x25 = score40x25.add(new BigDecimal(20));
                                    projectStepScore = projectStepScore + 20;
                                    projectScore = projectScore + 20;
                                    projectStepScoreFour = projectStepScoreFour + 20;
                                }
                                if (zbfs.contains("贷款项目国内公开竞争性招标") || zbfs.contains("邀请招标")) {
                                    score40x25 = score40x25.add(new BigDecimal(18));
                                    projectStepScore = projectStepScore + 18;
                                    projectScore = projectScore + 18;
                                    projectStepScoreFour = projectStepScoreFour + 18;
                                    ;
                                }
                            }
                            if (zbfs.contains("国内资金项目国际公开招标") || zbfs.contains("邀请招标和竞谈") || zbfs.contains("国际公开资格预审")) {
                                score15 = score15.add(new BigDecimal(15));
                                projectStepScore = projectStepScore + 15;
                                projectScore = projectScore + 15;
                                projectStepScoreFour = projectStepScoreFour + 15;
                            }
                            if (zbfs.contains("国内资金国项目内公开招标") || zbfs.contains("国内公开资格预审")) {
                                score10 = score10.add(new BigDecimal(10));
                                projectStepScore = projectStepScore + 10;
                                projectScore = projectScore + 10;
                                projectStepScoreFour = projectStepScoreFour + 10;
                            }
                            if (zbfs.contains("国内资金项目国内邀请招标") || zbfs.contains("竞谈")) {
                                score7 = score7.add(new BigDecimal(7));
                                projectStepScore = projectStepScore + 7;
                                projectScore = projectScore + 7;
                                projectStepScoreFour = projectStepScoreFour + 7;
                            }
                            if (zbfs.contains("询价")) {
                                score5 = score5.add(new BigDecimal(5));
                                projectStepScore = projectStepScore + 5;
                                projectScore = projectScore + 5;
                                projectStepScoreFour = projectStepScoreFour + 5;
                            }
                        }
                        stepNum++;

                        allScoreDict.put(projectName + "-" + achiWb.getBiaoduanuid(), projectStepScore);
                        allScoreDict.put(projectName + "-" + achiWb.getBiaoduanuid() + "-step4", projectStepScoreFour);

                    }
                }
            }
            allScoreDict.put(projectName, projectScore);
        }

        map.put("贷款项目国际公开咨询服务选聘QCBS、国际公开资格预审、国际公开竞争性招标", score40x25.doubleValue());
        map.put("贷款项目国际公开咨询服务选聘CQS、前审合同的国内公开竞争性招标、国内公开资格预审", score35x20.doubleValue());
        map.put("贷款项目国内公开竞争性招标、邀请招标", score30x18.doubleValue());
        map.put("国内资金项目国际公开招标、国际公开资格预审、邀请招标和竞谈", score15.doubleValue());
        map.put("国内资金国项目内公开招标、国内公开资格预审", score10.doubleValue());
        map.put("国内资金项目国内邀请招标、竞谈", score7.doubleValue());
        map.put("询价", score5.doubleValue());

        List<Map<String, Double>> objects = new ArrayList<>();
        objects.add(map);
        return objects;
    }
}
