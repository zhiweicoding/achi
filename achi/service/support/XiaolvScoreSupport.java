package com.deta.achi.service.support;

import com.deta.achi.pojo.AchiWb;
import com.deta.achi.service.impl.AchiTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 效率系数
 *
 * @author by diaozhiwei on 2022/08/17.
 * @email diaozhiwei2k@163.com
 */
@Component
public class XiaolvScoreSupport extends ScoreSupport {
    @Autowired
    private AchiTaskServiceImpl achiTaskService;

    @Override
    public List<Map<String, Double>> work(Map<String, List<AchiWb>> groupByProjectName, Map<String, Double> allScoreDict, Date startTime, Date endTime) {
        Map<String, Double> map = new HashMap<>();
        double xl1 = 0;
        double xl2 = 0;

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

                        //新项目跟踪 外部项目备案到采购管理中

                        if (achiWb.getDaikuanPy() != null) {
                            switch (achiWb.getDaikuanPyData()) {
                                case "优":
                                    xl1 = xl1 + 7;
                                    projectStepScore = projectStepScore + 7;
                                    projectScore = projectScore + 7;
                                    break;
                                case "良":
                                    xl1 = xl1 + 5;
                                    projectStepScore = projectStepScore + 5;
                                    projectScore = projectScore + 5;
                                    break;
                                case "差":
                                    xl1 = xl1 - 5;
                                    projectStepScore = projectStepScore - 5;
                                    projectScore = projectScore - 5;
                                    break;
                            }

                        }

                        if (achiWb.getManyiPy() != null&&achiTaskService.checkTime(achiWb.getManyiPy(),startTime,endTime)) {
                            switch (achiWb.getManyiPyData()) {
                                case "优":
                                    xl2 = xl2 + 7;
                                    projectStepScore = projectStepScore + 7;
                                    projectScore = projectScore + 7;
                                    break;
                                case "良":
                                    xl2 = xl2 + 5;
                                    projectStepScore = projectStepScore + 5;
                                    projectScore = projectScore + 5;
                                    break;
                                case "差":
                                    xl2 = xl2 - 5;
                                    projectStepScore = projectStepScore - 5;
                                    projectScore = projectScore - 5;
                                    break;
                            }

                        }
                        allScoreDict.put(projectName + "-" + achiWb.getBiaoduanuid(), projectStepScore);
                    }

                }
            }
            allScoreDict.put(projectName, projectScore);
        }

        //贷款机构对项目合同检查评议
        map.put("贷款机构对项目合同检查评议", xl1);
        //年度客户满意度调查
        map.put("年度客户满意度调查", xl2);

        List<Map<String, Double>> objects = new ArrayList<>();
        objects.add(map);
        return objects;
    }
}
