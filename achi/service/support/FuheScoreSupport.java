package com.deta.achi.service.support;

import com.deta.achi.pojo.AchiWb;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 计算难度系数-招标负荷
 * 招标负荷（采购管理）
 *
 * @author by diaozhiwei on 2022/08/15.
 * @email diaozhiwei2k@163.com
 */
@Component
public class FuheScoreSupport extends ScoreSupport {

    @Override
    public List<Map<String, Double>> work(Map<String, List<AchiWb>> groupByProjectName, Map<String, Double> allScoreDict, Date startTime, Date endTime) {
        Map<String, Double> map = new HashMap<>();

        double fh1 = 0;
        double fh2 = 0;
        double fh3 = 0;
        double fh4 = 0;
        double fh5 = 0;
        double fh6 = 0;
        double fh7 = 0;
        double fh8 = 0;
        double fh9 = 0;
        double fh10 = 0;
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
                        if (allScoreDict.containsKey(projectName + "-" + achiWb.getBiaoduanuid())) {
                            projectStepScore = allScoreDict.get(projectName + "-" + achiWb.getBiaoduanuid());
                        }

                        double projectStepScoreFour = 0;
                        if (allScoreDict.containsKey(projectName + "-" + achiWb.getBiaoduanuid() + "-step4")) {
                            projectStepScoreFour = allScoreDict.get(projectName + "-" + achiWb.getBiaoduanuid() + "-step4");
                        }
                        //新项目跟踪 外部项目备案到采购管理中

                        if (achiWb.getShifoublw() != null && achiWb.getShifoublw().equals("是") && checkTime(achiWb.getBiddingTime(), startTime, endTime)) {
                            fh1 = fh1 + 3;
                            projectStepScore = projectStepScore + 3;
                            projectScore = projectScore + 3;
                        }

                        if (isYes(achiWb.getZtbggfwpt()) && checkTime(achiWb.getBiddingTime(), startTime, endTime)) {
                            fh2 = fh2 + 3;
                            projectStepScore = projectStepScore + 3;
                            projectScore = projectScore + 3;
                        }

                        if (achiWb.getTbrNum() != null && achiWb.getTbrNum() >= 11 && achiWb.getTbrNum() <= 20 && checkTime(achiWb.getBidOpenDate(), startTime, endTime)) {
                            fh3 = fh3 + 5;
                            projectStepScore = projectStepScore + 5;
                            projectScore = projectScore + 5;
                        }
                        if (achiWb.getTbrNum() != null && achiWb.getTbrNum() > 20 && achiWb.getBidOpenDate().after(startTime) == true && achiWb.getBidOpenDate().after(endTime) == false) {
                            fh3 = fh3 + 10;
                            projectStepScore = projectStepScore + 10;
                            projectScore = projectScore + 10;
                        }

                        if (achiWb.getPingbiaofs() != null && achiWb.getPingbiaofs().contains("线下纸质版") && checkTime(achiWb.getBidEvaluationDate(), startTime, endTime)) {
                            fh5 = fh5 + 5;
                            projectStepScore = projectStepScore + 5;
                            projectScore = projectScore + 5;
                        }

                        if (achiWb.getZbwjgt() != null && achiWb.getPingbiaofs() != null && achiWb.getPingbiaofs().contains("线下纸质版")) {
                            Integer zbwjgt = achiWb.getZbwjgt();
                            fh6 = fh6 + zbwjgt * 2;
                            projectStepScore = projectStepScore + zbwjgt * 2;
                            projectScore = projectScore + zbwjgt * 2;
                        }

                        if (achiWb.getAssessment() != null && achiWb.getAssessment().equals("是") && checkTime(achiWb.getBidEvaluationDate(), startTime, endTime)) {
                            fh7 = fh7 + 40;
                            projectStepScore = projectStepScore + 40;
                            projectScore = projectScore + 40;
                        }
                        if (achiWb.getAssessment() != null && achiWb.getAssessment().contains("且为首次出差") && checkTime(achiWb.getBidEvaluationDate(), startTime, endTime)) {
                            fh7 = fh7 + 80;
                            projectStepScore = projectStepScore + 80;
                            projectScore = projectScore + 80;
                        }

                        if (achiWb.getPinggu() != null && achiWb.getPinggu().equals("是") && checkTime(achiWb.getAssistanceAgenciesDate(), startTime, endTime)) {
                            fh8 = fh8 + 20;
                            projectStepScore = projectStepScore + 20;
                            projectScore = projectScore + 20;
                        }

                        if (checkTime(achiWb.getAcceptanceDate(), startTime, endTime)) {
                            if (achiWb.getIsPingb() != null && achiWb.getIsPingb().equals("是")) {
                                double stepFourD = new BigDecimal(String.valueOf(projectStepScoreFour)).multiply(new BigDecimal("0.3")).setScale(2, RoundingMode.HALF_UP).doubleValue();
                                fh10 = fh10 + stepFourD;
                                projectStepScore = projectStepScore + stepFourD;
                                projectScore = projectScore + stepFourD;
                            }

                            if (achiWb.getHandle() != null && achiWb.getHandle().contains("重新招标") && achiWb.getBiaoduanuid().contains("重新招标")) {
                                double s9D = new BigDecimal(String.valueOf(projectStepScoreFour)).multiply(new BigDecimal("0.8")).setScale(2, RoundingMode.HALF_UP).doubleValue();
                                fh9 = fh9 + s9D;
                                projectStepScore = projectStepScore + s9D;
                                projectScore = projectScore + s9D;
                            }
                            if (achiWb.getHandle() != null && achiWb.getHandle().contains("重新招标") && achiWb.getBiaoduanuid().contains("第二次重新招标")) {
                                double s9D = new BigDecimal(String.valueOf(projectStepScoreFour)).multiply(new BigDecimal("1.4")).setScale(2, RoundingMode.HALF_UP).doubleValue();
                                fh9 = fh9 + s9D;
                                projectStepScore = projectStepScore + s9D;
                                projectScore = projectScore + s9D;
                            }
                            if (achiWb.getHandle() != null && achiWb.getHandle().contains("重新招标") && achiWb.getBiaoduanuid().contains("第三次重新招标")) {
                                double s9D = new BigDecimal(String.valueOf(projectStepScoreFour)).multiply(new BigDecimal("1.9")).setScale(2, RoundingMode.HALF_UP).doubleValue();
                                fh9 = fh9 + s9D;
                                projectStepScore = projectStepScore + s9D;
                                projectScore = projectScore + s9D;
                            }
                        }
                        stepNum++;

                        allScoreDict.put(projectName + "-" + achiWb.getBiaoduanuid(), projectStepScore);
                    }

                }
            }
            allScoreDict.put(projectName, projectScore);
        }

        map.put("必联网操作【必联网数据回写采购管理】", fh1);
        map.put("中国招标投标公共服务平台操作", fh2);
        map.put("投标人数量", fh3);
        map.put("采用综合评分法进行评审", fh4);
        map.put("采用线下纸质版评标", fh5);
        map.put("前往至外地项目单位进行招标文件编制沟通的次数", fh6);
        map.put("至地方交易中心完成评审工作的标", fh7);
        map.put("协助贷款机构对电子交易系统进行评估", fh8);
        map.put("开标后重新招标", fh9);
        map.put("协助开标评标", fh10);

        List<Map<String, Double>> objects = new ArrayList<>();
        objects.add(map);
        return objects;
    }

    public boolean checkTime(Date date, Date startDate, Date endDate) {
        if (date != null && date.compareTo(startDate) != -1 && date.compareTo(endDate) != 1) {
            return true;
        } else {
            return false;
        }
    }
}
