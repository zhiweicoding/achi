package com.deta.achi.service.support;

import com.deta.achi.pojo.AchiWb;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 计算难度系数-合同执行
 * 合同执行（采购执行）
 *
 * @author by diaozhiwei on 2022/08/15.
 * @email diaozhiwei2k@163.com
 */
@Component
public class HetongScoreSupport extends ScoreSupport {

    @Override
    public List<Map<String, Double>> work(Map<String, List<AchiWb>> groupByProjectName, Map<String, Double> allScoreDict, Date startTime, Date endTime) {
        Map<String, Double> map = new HashMap<>();

        double ht1 = 0;
        double ht2 = 0;
        double ht3 = 0;
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
                    for (AchiWb achiWb : singleWbArray) {
                        double projectStepScore = 0;
                        if (allScoreDict.containsKey(projectName + "-" + achiWb.getBiaoduanuid())) {
                            projectStepScore = allScoreDict.get(projectName + "-" + achiWb.getBiaoduanuid());
                        }
                        //&& isTimeInGap(achiWb.getDbTime(), startTime, endTime)
                        if (isYes(achiWb.getIsTanpan()) && checkTime(achiWb.getProcurementAgencyDate(), startTime, endTime)) {
                            ht1 = ht1 + 40;
                            projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 40);
                            projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 40);
                        }
                        if (checkTime(achiWb.getHandlingImportDate(), startTime, endTime) && checkTime(achiWb.getTakeDeliveryDate(), startTime, endTime) && checkTime(achiWb.getPaymentDate(), startTime, endTime)) {
                            if (isYes(achiWb.getIsBanli())) {
                                ht2 = ht2 + 60;
                                projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 60);
                                projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 60);
                            }
                        }
                        if (checkTime(achiWb.getContractFilingDate(), startTime, endTime)) {
                            if (isYes(achiWb.getIsGuidang()) && isYes(achiWb.getIsTanpan())) {
                                ht3 = ht3 + 10;
                                projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 10);
                                projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 10);
                            }
                        }
                        allScoreDict.put(projectName + "-" + achiWb.getBiaoduanuid(), projectStepScore);
                    }
                }
            }
            allScoreDict.put(projectName, projectScore);
        }

        map.put("合同谈判及签订采购代理合同", ht1);
        map.put("办理支付、进口、报关、提货", ht2);
        map.put("合同归档", ht3);

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
