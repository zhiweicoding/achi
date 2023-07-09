package com.deta.achi.service;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deta.achi.dao.mapper.AchiPlV2Mapper;
import com.deta.achi.dto.AchiTaskDTO;
import com.deta.achi.pojo.AchiProcurement;
import com.deta.achi.utils.AchiEnum;
import com.deta.common.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 品类采购service v2
 *
 * @author by diaozhiwei on 2023/01/10.
 * @email diaozhiwei2k@163.com
 */
@Service
public class AchiPlV2Service extends ServiceImpl<AchiPlV2Mapper, AchiProcurement> {

    private static final Logger logger = LoggerFactory.getLogger(AchiPlV2Service.class);

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
    private AchiPlV2Mapper achiPlMapper;

    @Transactional(rollbackFor = {Exception.class})
    public int add(AchiProcurement achiPl, String userName, String domainName, String departmentName, String userId) {
        String projectno = achiPl.getProjectno();
        String xieyiNo = achiPl.getXieyiNo();
        //判断项目是否存在
        if (StringUtils.hasText(projectno)) {
            List<AchiProcurement> selectOne = achiPlMapper.selectList(new QueryWrapper<AchiProcurement>().eq("status", AchiEnum.DELETE_FALSE.code()).eq("projectno", projectno));
            Assert.isTrue(selectOne.isEmpty(), "项目已存在");
        }
        //与协议项目无关
        if (StringUtils.isEmpty(projectno) && StringUtils.isEmpty(xieyiNo)) {
            achiPl.setId(UUID.fastUUID().toString(false));
            achiPl.setCreateTime(new Date());
            achiPl.setJbrCode(userId);
            achiPl.setJbrName(userName);
            achiPl.setCompany(domainName);
            achiPl.setStatus(AchiEnum.DELETE_FALSE.code());
            achiPl.setDepartment(departmentName);
            return achiPlMapper.insert(achiPl);
        }
        achiPl.setCreateTime(new Date());
        achiPl.setJbrCode(userId);
        achiPl.setJbrName(userName);
        achiPl.setCompany(domainName);
        achiPl.setStatus(AchiEnum.DELETE_FALSE.code());
        achiPl.setDepartment(departmentName);
        return achiPlMapper.insert(achiPl);
    }

    // 通过研究推荐集采目录
    public int calcMenuScore(String jbrCode, Date startTime, Date endTime) {
        int score = 0;
        try {
            List<AchiProcurement> businessApprovalTimeArray = achiPlMapper.selectList(Wrappers.<AchiProcurement>lambdaQuery()
                    .eq(AchiProcurement::getStatus, AchiEnum.DELETE_FALSE.code())
                    .eq(AchiProcurement::getJbrCode, jbrCode)
                    .eq(AchiProcurement::getOrderType, "集采目录推荐")).stream().filter(bean -> checkTime(bean.getBusinessApprovalTime(), startTime, endTime)).collect(Collectors.toList());

            List<AchiProcurement> centralApprovalTimeArray = achiPlMapper.selectList(Wrappers.<AchiProcurement>lambdaQuery()
                    .eq(AchiProcurement::getStatus, AchiEnum.DELETE_FALSE.code())
                    .eq(AchiProcurement::getJbrCode, jbrCode)
                    .eq(AchiProcurement::getOrderType, "集采目录推荐")).stream().filter(bean -> checkTime(bean.getCentralApprovalTime(), startTime, endTime)).collect(Collectors.toList());

            List<AchiProcurement> finalApprovalAndReleaseTimeArray = achiPlMapper.selectList(Wrappers.<AchiProcurement>lambdaQuery()
                    .eq(AchiProcurement::getStatus, AchiEnum.DELETE_FALSE.code())
                    .eq(AchiProcurement::getJbrCode, jbrCode)
                    .eq(AchiProcurement::getOrderType, "集采目录推荐")).stream().filter(bean -> checkTime(bean.getFinalApprovalAndReleaseTime(), startTime, endTime)).collect(Collectors.toList());

            Map<String, String> distinctBatDict = getDistinctDictCalcMenuScore(businessApprovalTimeArray);
            Map<String, String> distinctCatDict = getDistinctDictCalcMenuScore(centralApprovalTimeArray);
            Map<String, String> distinctFaartDict = getDistinctDictCalcMenuScore(finalApprovalAndReleaseTimeArray);
            removeScoreTiny(distinctFaartDict, distinctCatDict);
            removeScoreTiny(distinctFaartDict, distinctBatDict);
            removeScoreTiny(distinctCatDict, distinctBatDict);

            finalApprovalAndReleaseTimeArray = retainArray(distinctFaartDict, finalApprovalAndReleaseTimeArray);
            centralApprovalTimeArray = retainArray(distinctCatDict, centralApprovalTimeArray);
            businessApprovalTimeArray = retainArray(distinctBatDict, businessApprovalTimeArray);

            score = score + finalApprovalAndReleaseTimeArray.size() * 15;
            score = score + centralApprovalTimeArray.size() * 10;
            score = score + businessApprovalTimeArray.size() * 5;
            if (score > 60) {
                score = 60;
            }
        } catch (Exception e) {
            logger.error("通过研究推荐集采目录,{}", e.getMessage(), e);
        }
        return score;
    }

    private List<AchiProcurement> retainArray(Map<String, String> dict, List<AchiProcurement> as) {
        List<String> ds = new ArrayList<>(dict.values());
        for (AchiProcurement a : as) {
            if (a.getIsEnsureProductSupplyProblem() == null) {
                a.setIsEnsureProductSupplyProblem("");
            }
        }
        List<AchiProcurement> result = as.stream().filter(bean ->
                ds.contains(bean.getId()) && !bean.getIsEnsureProductSupplyProblem().contains("是")).collect(Collectors.toList());
        return result;
    }

    /**
     * 根据value
     * 保留分数大的，去掉分数小的
     *
     * @param bigDict  {@link Map}
     * @param tinyDict
     */
    private void removeScoreTiny(Map<String, String> bigDict, Map<String, String> tinyDict) {
        for (String bigKey : bigDict.keySet()) {
            for (String tingKey : tinyDict.keySet()) {
                String bigValue = bigDict.get(bigKey);
                String tinyValue = tinyDict.get(tingKey);
                if (bigValue.equals(tinyValue)) {
                    tinyDict.remove(tingKey);
                }
            }
        }
    }

    private Map<String, String> getDistinctDictCalcMenuScore(List<AchiProcurement> array) {
        Map<String, String> distinctBatDict = new HashMap<>();
        for (AchiProcurement batItem : array) {
            if (batItem.getIsEnsureProductSupplyProblem() != null && batItem.getIsEnsureProductSupplyProblem().contains("是")) {
                continue;
            }
            String categoryCode = batItem.getCategoryCode();
            String middleCode = batItem.getMiddleCode();
            String subcategoryCode = batItem.getSubcategoryCode();
            if (!StringUtils.isEmpty(subcategoryCode)) {
                distinctBatDict.put(subcategoryCode, batItem.getId());
            } else if (!StringUtils.isEmpty(middleCode)) {
                distinctBatDict.put(middleCode, batItem.getId());
            } else if (!StringUtils.isEmpty(categoryCode)) {
                distinctBatDict.put(categoryCode, batItem.getId());
            }
        }
        return distinctBatDict;
    }

    // 当年进行集采协议化可行性分析研究
    public int calcKxxScore(String jbrCode, Date startTime, Date endTime) {
        int kxxScore = 0;
        try {
            //如果不在一年，那就是有问题
            //品类协议化可行性分析研究时间（或采购工作方案开始编制时间）
            List<AchiProcurement> plV2List = achiPlMapper.selectList(Wrappers.<AchiProcurement>lambdaQuery()
                    //（一）品类协议化集采可行性研究 不确定分类
//                    .eq(AchiPlV2::getType, 1)
                    .eq(AchiProcurement::getStatus, 1)
                    .eq(AchiProcurement::getOrderType, "集采目录推荐")
                    .eq(AchiProcurement::getJbrCode, jbrCode)).stream().filter(bean -> checkTime(bean.getKxxDate(), startTime, endTime)).collect(Collectors.toList());
            ;

            Set<String> plSet = new HashSet<>();
            if (plV2List.size() > 0) {
                for (AchiProcurement achiPl : plV2List) {
                    if (achiPl.getIsEnsureProductSupplyProblem() != null && achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                        continue;
                    }
                    // 按照最低品类有值的为依据添加
                    if (StringUtils.hasText(achiPl.getSubcategoryCode())) {
                        plSet.add(achiPl.getSubcategoryCode());
                    } else if (StringUtils.hasText(achiPl.getMiddleCode())) {
                        plSet.add(achiPl.getMiddleCode());
                    } else if (StringUtils.hasText(achiPl.getCategoryCode())) {
                        plSet.add(achiPl.getCategoryCode());
                    }
                }
            }
            int kxxCount = plSet.size();
            if (kxxCount >= 1 && kxxCount <= 3) {
                kxxScore = 30;
            } else if (kxxCount >= 4 && kxxCount <= 10) {
                kxxScore = 36;
            } else if (kxxCount >= 11) {
                kxxScore = 45;
            }
        } catch (Exception e) {
            logger.error("当年进行品类协议化可行性分析研究报错了,{}", e.getMessage(), e);
        }
        return kxxScore;
    }

    // 当年完成集采协议采购方案编制的
    public double calcJcxyScore(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "框架协议采购品类");
        List<AchiProcurement> list = this.list(wrapper);
        list = list.stream().filter(bean -> checkTime(bean.getBzDate(), startTime, endTime) && checkTime(bean.getExaminDate(), startTime, endTime)).collect(Collectors.toList());
        double jcxyScore = 0;
        Set<String> plSet = new HashSet<>();
        // 首次签订框架协议*1.5。设备类*1.5
        boolean isLianhe = false;
        int firstInt = 0;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                // 按照最低品类有值的为依据添加
                if (StringUtils.hasText(achiPl.getSubcategoryCode())) {
                    plSet.add(achiPl.getSubcategoryCode());
                } else if (StringUtils.hasText(achiPl.getMiddleCode())) {
                    plSet.add(achiPl.getMiddleCode());
                } else if (StringUtils.hasText(achiPl.getCategoryCode())) {
                    plSet.add(achiPl.getCategoryCode());
                }
                if (achiPl.getIsFirstTimeFramework() != null && "是".equals(achiPl.getIsFirstTimeFramework())) {
                    firstInt++;
                }
            }
        }
        int jcxyCount = plSet.size();
        if (jcxyCount >= 1 && jcxyCount <= 3) {
            jcxyScore = 60;
        } else if (jcxyCount >= 4 && jcxyCount <= 10) {
            jcxyScore = 72;
        } else if (jcxyCount >= 11) {
            jcxyScore = 90;
        }
        if (firstInt > 0) {
            jcxyScore = jcxyScore * 1.5;
        }
        if (isLianhe) {
            jcxyScore = jcxyScore * 1.1;
        }
        return jcxyScore;
    }

    // 采购策略系数
    public double calcStrategyScore(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "框架协议采购品类");
        List<AchiProcurement> list = this.list(wrapper);
        double caigouScore = 0;
        Set<String> plSet = new HashSet<>();
        // 首次签订框架协议*1.5。设备类*1.5
//        boolean isLianhe = false;
//        int firstInt = 0;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem() == null) {
                    achiPl.setIsEnsureProductSupplyProblem("");
                }
                if (achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                plSet.add(achiPl.getProjectno());
                int size = plSet.stream().filter(biaoduan -> biaoduan.equals(achiPl.getProjectno())).collect(Collectors.toList()).size();
                double result = (size - 1) * 0.2;
                if (result >= 1) {
                    result = 1;
                }
                if (achiPl.getPurchaseWay().contains("公开招标") && achiPl.getPingbiaofs().contains("最低评标价法")) {
                    caigouScore = caigouScore + 10 * (1.5 + result);
                }
                if (achiPl.getPurchaseWay().contains("公开招标") && achiPl.getPingbiaofs().contains("综合评估法")) {
                    caigouScore = caigouScore + 10 * (2 + result);
                }
                if (achiPl.getPurchaseWay().contains("公开资格审查")) {
                    caigouScore = caigouScore + 10 * (2 + result);
                }
                if (achiPl.getPurchaseWay().contains("竞争性谈判") && achiPl.getPingbiaofs().contains("综合评估法")) {
                    caigouScore = caigouScore + 10 * (1 + result);
                } else if (achiPl.getPurchaseWay().contains("竞争性谈判")) {
                    caigouScore = caigouScore + 10 * (1.5 + result);
                }
                if (achiPl.getPurchaseWay().contains("邀请招标")) {
                    if (achiPl.getPingbiaofs().contains("最低评标价法")) {
                        caigouScore = caigouScore + 10 * (1 + result);
                    }
                    if (achiPl.getPingbiaofs().contains("综合评估法基本分")) {
                        caigouScore = caigouScore + 10 * (1.5 + result);
                    }
                }
                if (achiPl.getPurchaseWay().contains("询价")) {
                    caigouScore = caigouScore + 10 * (0.8 + result);
                }
                if (achiPl.getPurchaseWay().contains("单一来源")) {
                    caigouScore = caigouScore + 10 * (0.5 + result);
                }
            }
        }
        return caigouScore;
    }

    // 当年完成招标文件编制并发标的
    public double calcFbScore(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "框架协议采购品类");
        List<AchiProcurement> list = this.list(wrapper)
                .stream().filter(bean -> checkTime(bean.getFbDate(), startTime, endTime)).collect(Collectors.toList());
        double fbScore = 0;
        Set<String> plSet = new HashSet<>();
        // 首次签订框架协议*1.5。设备类*1.5
        boolean first = false;
        boolean isSbl = false;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
//                if (achiPl.getIsEnsureProductSupplyProblem() != null && achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
//                    continue;
//                }
//                if (achiPl.getIsFirstTimeFramework() != null && "是".equals(achiPl.getIsFirstTimeFramework())) {
//                    first = true;
//                }
//                if (achiPl.getIsSbl() != null && "是".equals(achiPl.getIsSbl())) {
//                    isSbl = true;
//                }
                if (checkTime(achiPl.getBiddingDate(), startTime, endTime)) {
                    plSet.add(achiPl.getProjectno());
                }
            }
        }
        int fbCount = plSet.size();
        if (fbCount >= 1 && fbCount <= 3) {
            fbScore = 50;
        } else if (fbCount >= 4 && fbCount <= 10) {
            fbScore = 60;
        } else if (fbCount >= 11) {
            fbScore = 75;
        }
        return fbScore;
    }

    // 当年完成评标并授标的
    public double calcPbScore(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "框架协议采购品类");
        List<AchiProcurement> list = this.list(wrapper)
                .stream().filter(bean -> checkTime(bean.getPbDate(), startTime, endTime) && checkTime(bean.getWsbDate(), startTime, endTime)).collect(Collectors.toList());
        double pbScore = 0;
        Set<String> plSet = new HashSet<>();
        // 首次签订框架协议*1.5。设备类*1.5
        boolean first = false;
        boolean isSbl = false;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem() != null && achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                // 按照最低品类有值的为依据添加
//                if (StringUtils.hasText(achiPl.getThreeFl())) {
//                    plSet.add(achiPl.getThreeFl());
//                } else if (StringUtils.hasText(achiPl.getTwoFl())) {
//                    plSet.add(achiPl.getTwoFl());
//                } else if (StringUtils.hasText(achiPl.getOneFl())) {
//                    plSet.add(achiPl.getOneFl());
//                }
//                if (achiPl.getIsFirstTimeFramework() != null && "是".equals(achiPl.getIsFirstTimeFramework())) {
//                    first = true;
//                }
//                if (achiPl.getIsSbl() != null && "是".equals(achiPl.getIsSbl())) {
//                    isSbl = true;
//                }
                plSet.add(achiPl.getProjectno());
            }
        }
        int pbCount = plSet.size();
        if (pbCount >= 1 && pbCount <= 3) {
            pbScore = 30;
        } else if (pbCount >= 4 && pbCount <= 10) {
            pbScore = 42;
        } else if (pbCount >= 11) {
            pbScore = 45;
        }

        return pbScore;
    }

    // 当年完成框架协议谈判和签订
    public double calcKjqdScore(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "框架协议采购品类");
        List<AchiProcurement> list = this.list(wrapper)
                .stream().filter(bean -> checkTime(bean.getPbDate(), startTime, endTime) && checkTime(bean.getWsbDate(), startTime, endTime)).collect(Collectors.toList());
        double kjqdScore = 0;
        Set<String> plSet = new HashSet<>();
        // 首次签订框架协议*1.5。设备类*1.5
        boolean first = false;
        boolean isSbl = false;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem() != null && achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                // 按照最低品类有值的为依据添加
//                if (StringUtils.hasText(achiPl.getThreeFl())) {
//                    plSet.add(achiPl.getThreeFl());
//                } else if (StringUtils.hasText(achiPl.getTwoFl())) {
//                    plSet.add(achiPl.getTwoFl());
//                } else if (StringUtils.hasText(achiPl.getOneFl())) {
//                    plSet.add(achiPl.getOneFl());
//                }
//                if (achiPl.getIsFirstTimeFramework() != null && "是".equals(achiPl.getIsFirstTimeFramework())) {
//                    first = true;
//                }
//                if (achiPl.getIsSbl() != null && "是".equals(achiPl.getIsSbl())) {
//                    isSbl = true;
//                }
                plSet.add(achiPl.getProjectno());
            }
        }
        int kjqdCount = plSet.size();
        if (kjqdCount >= 1 && kjqdCount <= 3) {
            kjqdScore = 40;
        } else if (kjqdCount >= 4 && kjqdCount <= 10) {
            kjqdScore = 48;
        } else if (kjqdCount >= 11) {
            kjqdScore = 60;
        }
        return kjqdScore;
    }

    // 单项次采购
    public int calcSinglePurchase(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "单项次采购品类");
        List<AchiProcurement> list = this.list(wrapper)
                .stream().filter(bean -> checkTime(bean.getSuoshuDate(), startTime, endTime)).collect(Collectors.toList());
        int noAcrossScore = 0;
        int noAcrossCount = 0;
        int acrossScore = 0;
        int acrossCount = 0;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                if (achiPl.getOrderType().contains("单项次采购品类")) {
                    if (achiPl.getIsKdw() != null && "否".equals(achiPl.getIsKdw())) {
                        noAcrossCount++;
                    } else if (achiPl.getIsKdw() != null && "是".equals(achiPl.getIsKdw())) {
                        acrossCount++;
                    }
                }
            }
        }
        noAcrossScore = noAcrossCount * 10;
        acrossScore = acrossCount * 15;
        return noAcrossScore + acrossScore;
    }

    //“+1”的框架协议，按采购方案完成协议执行回顾续签的
    public int calcPlus1Score(List<AchiProcurement> list) {
        int plus1Score = 0;
        int plus1Count = 0;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                if (achiPl.getXuqian() != null && "是".equals(achiPl.getXuqian())) {
                    plus1Count++;
                }
            }
        }
        if (plus1Count >= 1 && plus1Count <= 3) {
            plus1Score = 50;
        } else if (plus1Count >= 4 && plus1Count <= 10) {
            plus1Score = 60;
        } else if (plus1Count >= 11) {
            plus1Score = 65;
        }
        return plus1Score;
    }

    // 节资框架协议
    public int calcFrameworkScore(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "框架协议采购品类");
        List<AchiProcurement> list = this.list(wrapper)
                .stream().filter(bean -> checkTime(bean.getKjqdDate(), startTime, endTime)).collect(Collectors.toList());
        int frameworkScore = 0;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem() != null && achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                double zbMoney = achiPl.getZbMoney();
                double ysMoney = achiPl.getYsMoney();
                BigDecimal jiezilv = new BigDecimal(0);
                if (zbMoney != 0) {
                    jiezilv = BigDecimal.valueOf(ysMoney).subtract(BigDecimal.valueOf(zbMoney)).divide(BigDecimal.valueOf(ysMoney), 2, RoundingMode.HALF_UP);
                }
                double value = jiezilv.doubleValue();
                if (value > 0 && value <= 0.3) {
                    frameworkScore += 50;
                } else if (value >= 0.5) {
                    frameworkScore += 80;
                } else {
                    double v = (value - 0.3) / (0.5 - 0.3);
                    int score = (int) (v * (80 - 50));
                    frameworkScore += score;
                }
            }
        }
        return frameworkScore;
    }

    // 节资单项次
    public double calcJieziSingleScore(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "单项次采购品类");
        List<AchiProcurement> list = this.list(wrapper)
                .stream().filter(bean -> checkTime(bean.getQdDate(), startTime, endTime)).collect(Collectors.toList());
        double jieziSingleScore = 0;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem() != null && achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                String jzl = achiPl.getJzl();
                if (StringUtils.hasText(jzl)) {
                    double v = Double.parseDouble(jzl.replaceAll("%", ""));
                    if (v > 20) {
                        int c = (int) ((v - 20) / 10);
                        jieziSingleScore += c * 0.5;
                    }
                }
            }
        }
        return jieziSingleScore;
    }

    // 执行-框架协议订单执行
    public int calcExecFrameworkScore(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "框架协议采购品类");
        List<AchiProcurement> list = this.list(wrapper)
                .stream().filter(bean -> checkTime(bean.getKjqdDate(), startTime, endTime)).collect(Collectors.toList());
        int execFrameworkScore = 0;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem() != null && achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                Integer xccjhNum = achiPl.getCoordinateNum();
                if (xccjhNum != null && xccjhNum > 0) {
                    execFrameworkScore += xccjhNum * 15;
                }
            }
        }
        return execFrameworkScore;
    }

    // 执行-单项次
    public int calcExecSingleScore(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "单项次采购品类");
        List<AchiProcurement> list = this.list(wrapper)
                .stream().filter(bean -> checkTime(bean.getSuoshuDate(), startTime, endTime)).collect(Collectors.toList());
        int execSingleScore = 0;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem() != null && achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                Integer bgScore = achiPl.getCsgNum();
                if (bgScore != null && bgScore > 0) {
                    execSingleScore += bgScore;
                }
            }
        }
        return execSingleScore;
    }

    // 风险系数-签订的框架协议存在瑕疵，导致与供应商纠纷的
    public int calcRiskJfScore(String jbrCode, Date startTime, Date endTime) {
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1);
        List<AchiProcurement> list = this.list(wrapper)
                .stream().filter(bean -> checkTime(bean.getKjqdDate(), startTime, endTime)).collect(Collectors.toList());
        int riskJfScore = 0;
        int riskJfCount = 0;
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem() != null && achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                Integer jfNum = achiPl.getJfNum();
                if (jfNum != null && jfNum > 0) {
                    riskJfCount += jfNum;
                }
            }
            riskJfScore = riskJfCount * -5;
        }
        return riskJfScore;
    }

    // 风险系数-产生质量问题的
    public int calcRiskZlScore(String jbrCode, Date startTime, Date endTime) {
        int riskZlScore = 0;
        int riskZlCount = 0;
        // 计算框架协议订单产生质量问题的
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "框架协议采购品类");
        List<AchiProcurement> list = this.list(wrapper).stream().filter(bean -> checkTime(bean.getKjqdDate(), startTime, endTime)).collect(Collectors.toList());
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                if (achiPl.getIsEnsureProductSupplyProblem() != null && achiPl.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                Integer zlNum = achiPl.getZlNum();
                if (zlNum != null && zlNum > 0) {
                    riskZlCount += zlNum;
                }
            }
            riskZlScore += riskZlCount * -30;
        }
        riskZlCount = 0;
        // 计算单次采办合同产生质量问题的
        LambdaQueryWrapper<AchiProcurement> queryWrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .eq(AchiProcurement::getStatus, 1)
                .like(AchiProcurement::getOrderType, "单项次采购品类");
        list = this.list(queryWrapper).stream().filter(bean -> checkTime(bean.getSuoshuDate(), startTime, endTime)).collect(Collectors.toList());
        if (list != null) {
            for (AchiProcurement achiPl : list) {
                Integer zlNum = achiPl.getZlNum();
                if (zlNum != null && zlNum > 0) {
                    riskZlCount += zlNum;
                }
            }
            riskZlScore += riskZlCount * -5;
        }
        return riskZlScore;
    }

    public Page<AchiProcurement> queryList(AchiProcurement achiPl, String userId) {
        String xmNameOrId = achiPl.getXmNameOrId();
        String nameOrId = achiPl.getNameOrId();
        Long startTime = achiPl.getStartTime();
        Long stopTime = achiPl.getStopTime();
        LambdaQueryWrapper<AchiProcurement> queryWrapper = new LambdaQueryWrapper<>();
        //开始时间
        if (Objects.nonNull(startTime)) {
            queryWrapper.ge(AchiProcurement::getCreateTime, simpleDateFormat.format(new Date(startTime)));
        }
        //结束时间
        if (Objects.nonNull(stopTime)) {
            queryWrapper.le(AchiProcurement::getCreateTime, simpleDateFormat.format(new Date(stopTime)));
        }
        //正序
        if (Objects.nonNull(achiPl.getSort()) && "asc".equals(achiPl.getSort())) {
            queryWrapper.orderByAsc(AchiProcurement::getCreateTime);
        }
        //倒序
        if (Objects.nonNull(achiPl.getSort()) && "desc".equals(achiPl.getSort())) {
            queryWrapper.orderByDesc(AchiProcurement::getCreateTime);
        }
        //项目名称或编号
        if (StringUtils.hasText(nameOrId)) {
            queryWrapper.and((wrapper) -> wrapper.like(AchiProcurement::getProjectname, nameOrId).or().like(AchiProcurement::getProjectno, nameOrId));
        }
        //协议名称或编号
        if (StringUtils.hasText(xmNameOrId)) {
            queryWrapper.and((wrapper) -> wrapper.like(AchiProcurement::getXieyiName, xmNameOrId).or().like(AchiProcurement::getXieyiNo, xmNameOrId));
        }
        //分页
        Page<AchiProcurement> page = new Page<>(achiPl.getPageNum(), achiPl.getPageSize());
        //类型
        queryWrapper.like(AchiProcurement::getOrderType, achiPl.getOrderType())
                .eq(AchiProcurement::getStatus, 1);
        //返回结果
        return achiPlMapper.selectPage(page, queryWrapper.eq(AchiProcurement::getJbrCode, userId));
    }

    public Integer delete(List<String> ids) {
        //删除逻辑判断
        Assert.isTrue(!ids.isEmpty(), "未选中数据，删除失败");
        //删除数据状态
        int update = achiPlMapper.update(null, new UpdateWrapper<AchiProcurement>()
                .set("status", 0)
                .in("id", ids));
        return update;
    }

    public List<AchiProcurement> queryLeft(AchiTaskDTO achiPl) {
        Assert.isTrue(StringUtils.hasText(achiPl.getOrderType()), "品类类型不能为空");
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        //一级菜单查询
        List<AchiProcurement> achiPlList = achiPlMapper.selectList(wrapper
                .eq(AchiProcurement::getStatus, AchiEnum.DELETE_FALSE.code())
                .eq(AchiProcurement::getOrderType, achiPl.getOrderType())
                .eq(AchiProcurement::getJbrCode, achiPl.getWorkId())
                .ge(AchiProcurement::getCreateTime, simpleDateFormat.format(new Date(achiPl.getAchiStart())))
                .le(AchiProcurement::getCreateTime, simpleDateFormat.format(new Date(achiPl.getAchiStop()))));
        //二级菜单查询如果是框架协议展示
        if (!achiPlList.isEmpty()&&achiPl.getOrderType().contains("框架协议")) {
            List<AchiProcurement> deepCopyList = JsonUtil.parseObject(JsonUtil.toJSONString(achiPlList), new TypeReference<>() {
            });
            Map<String, List<AchiProcurement>> collect = deepCopyList.stream().collect(Collectors.groupingBy(AchiProcurement::getXieyiNo));
            for (AchiProcurement info : achiPlList) {
                String xieyiNo = info.getXieyiNo();
                List<AchiProcurement> sons = collect.get(xieyiNo);
                info.setAchiPlList(sons);
            }
        }
        return achiPlList;
    }

    public AchiProcurement queryDetail(AchiProcurement achiPl) {
        return achiPlMapper.selectById(achiPl);
    }

    private boolean isEmpty(String value) {
        return value == null || "".equals(value.trim()) || "null".equals(value.trim());
    }

    public int youhuaScore(List<AchiProcurement> list) {
        int youhua = 0;
        int youhuaNum = 0;
        if (!list.isEmpty()) {
            for (AchiProcurement achiPlV2 : list) {
                if (achiPlV2.getIsEnsureProductSupplyProblem().contains("是")) {
                    continue;
                }
                if (achiPlV2.getProOpti().contains("是")) {
                    youhuaNum++;
                }
            }
        }
        youhua = youhua + youhuaNum * 50;

        return youhua;
    }

    public boolean checkTime(Date date, Date startDate, Date endDate) {
        if (date != null && date.compareTo(startDate) != -1 && date.compareTo(endDate) != 1) {
            return true;
        } else {
            return false;
        }
    }

}
