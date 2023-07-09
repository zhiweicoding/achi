package com.deta.achi.service.support;

import com.deta.achi.pojo.AchiWb;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 计算难度系数-项目争取
 * 项目争取（外部项目业务系统--已经关停，未来在哪里填写，魏庆福，刘鹏程-13716061680  溯源）
 *
 * @author by diaozhiwei on 2022/08/15.
 * @email diaozhiwei2k@163.com
 */
@Component
public class ZhengquScoreSupport extends ScoreSupport {

    @Override
    public List<Map<String, Double>> work(Map<String, List<AchiWb>> groupByProjectName, Map<String, Double> allScoreDict, Date startTime, Date endTime) {
        Map<String, Double> map = new HashMap<>();

        double xinxiangmugenzong = 0;
        double xinxiangmudaigongsizhaobiao = 0;
        double guojijinrongzuzhi = 0;
        double waiguozhengfudaikuan = 0;
        double guoneizijin = 0;
        double suozaidi = 0;
        double dulizhaolan = 0;
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
                    if (achiWb.getGenzong() != null && achiWb.getGenzong().compareTo(startTime) != -1 && achiWb.getGenzong().compareTo(endTime) != 1) {
                        //新项目跟踪 外部项目备案到采购管理中
                        if (stepNum == 0) {
                            xinxiangmugenzong = xinxiangmugenzong + scoreWithFlag(hasWeiFaLuanJi, 30);
                            projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 30);
                            projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 30);

                            //新项目代理公司投标
                            if (isYes(achiWb.getDailitoubiao())) {
                                xinxiangmudaigongsizhaobiao = xinxiangmudaigongsizhaobiao + scoreWithFlag(hasWeiFaLuanJi, 15);
                                projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 15);
                                projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 15);
                            }

                        }

                    }
                    if (achiWb.getDailihetong() != null && achiWb.getDailihetong().compareTo(startTime) != -1 && achiWb.getDailihetong().compareTo(endTime) != 1) {
                        if (stepNum == 0) {
                            //中标并签订代理合同
                            if (achiWb.getZijin() != null) {
                                switch (achiWb.getZijin()) {
                                    case "国际金融组织":
                                        guojijinrongzuzhi = guojijinrongzuzhi + scoreWithFlag(hasWeiFaLuanJi, 40);
                                        projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 40);
                                        projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 40);
                                        break;
                                    case "外国政府贷款":
                                        waiguozhengfudaikuan = waiguozhengfudaikuan + scoreWithFlag(hasWeiFaLuanJi, 30);
                                        projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 30);
                                        projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 30);
                                        break;
                                    case "国内资金":
                                        guoneizijin = guoneizijin + scoreWithFlag(hasWeiFaLuanJi, 20);
                                        projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 20);
                                        projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 20);
                                        break;
                                }

                            }

                            if (isYes(achiWb.getIsZhongbiao()) && (achiWb.getXiangmu().equals("新贷款类别") || achiWb.getXiangmu().equals("新的行业") || achiWb.getXiangmu().equals("新的项目所在地"))) {
                                suozaidi = suozaidi + scoreWithFlag(hasWeiFaLuanJi, 40);
                                projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, 40);
                                projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, 40);
                            }

                            //独立招揽项目
                            if (isYes(achiWb.getIsDuzizhaolan()) && isYes(achiWb.getIsZhongbiao())) {
                                BigDecimal duLiZhaoLanXiangMuBig = BigDecimal.valueOf(projectStepScore).multiply(BigDecimal.valueOf(1.5)).setScale(2, RoundingMode.HALF_UP);
                                dulizhaolan = dulizhaolan + scoreWithFlag(hasWeiFaLuanJi, duLiZhaoLanXiangMuBig.doubleValue());
                                projectStepScore = projectStepScore + scoreWithFlag(hasWeiFaLuanJi, duLiZhaoLanXiangMuBig.doubleValue());
                                projectScore = projectScore + scoreWithFlag(hasWeiFaLuanJi, duLiZhaoLanXiangMuBig.doubleValue());
                            }

                        }
                    }
                    stepNum++;

                    allScoreDict.put(projectName + "-" + achiWb.getBiaoduanuid(), projectStepScore);
                }
            }
            allScoreDict.put(projectName, projectScore);
        }

        map.put("新项目跟踪", xinxiangmugenzong);
        map.put("新项目代理公司投标", xinxiangmudaigongsizhaobiao);
        map.put("国际金融组织", guojijinrongzuzhi);
        map.put("外国政府贷款", waiguozhengfudaikuan);
        map.put("国内资金", guoneizijin);
        map.put("项目来源新贷款类别、新的行业、新的项目所在地", suozaidi);
        map.put("独立招揽项目", dulizhaolan);

        List<Map<String, Double>> objects = new ArrayList<>();
        objects.add(map);
        return objects;
    }
}
