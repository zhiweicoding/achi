package com.deta.achi.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deta.achi.dao.mapper.AchiContributionMapper;
import com.deta.achi.dao.mapper.AchiTaskMapper;
import com.deta.achi.dao.mapper.AchiWbMapper;
import com.deta.achi.dao.mapper.JixiaoInfoMapper;
import com.deta.achi.dto.AchiTaskDTO;
import com.deta.achi.pojo.*;
import com.deta.achi.pojo.excel.ExcelUtils;
import com.deta.achi.service.AchiPlV2Service;
import com.deta.achi.service.AchiTaskService;
import com.deta.achi.service.JixiaoInfoService;
import com.deta.achi.service.support.*;
import com.deta.achi.utils.AchiEnum;
import com.deta.achi.utils.AchiSocreCalcUtil;
import com.deta.common.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static com.deta.achi.utils.AchiEnum.*;

/**
 * @author DJWang
 * @date 2022/08/09 21:04
 **/
@Service
public class AchiTaskServiceImpl extends ServiceImpl<AchiTaskMapper, AchiTask> implements AchiTaskService {

    private static final Logger logger = LoggerFactory.getLogger(AchiTaskServiceImpl.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

    @Autowired
    private AchiTaskMapper achiTaskMapper;


    @Autowired
    private AchiContributionMapper achiContributionMapper;

    @Autowired
    private AchiWbMapper achiWbMapper;

    @Autowired
    private AchiPlV2Service achiPlService;

    @Autowired
    private ZhengquScoreSupport zhengquScoreSupport;
    @Autowired
    private QuanguochengScoreSupport quanguochengScoreSupport;

    @Autowired
    private ZhunbeiScoreSupport zhunbeiScoreSupport;

    @Autowired
    private FangshiScoreSupport fangshiScoreSupport;
    @Autowired
    private FuheScoreSupport fuheScoreSupport;

    @Autowired
    private JixiaoInfoMapper jixiaoInfoMapper;
    @Autowired
    private HetongScoreSupport hetongScoreSupport;

    @Autowired
    private XiaolvScoreSupport xiaolvScoreSupport;
    List<String> scape = Arrays.asList("考核任务名称,考核人姓名,考核人账号,考核人部门,考核类型,考核开始时间,考核结束时间,rowNum".split(","));

    /**
     * 个人考核信息查询
     *
     * @param achiTask
     * @return
     */
    @Override
    public Page<AchiTask> select(AchiTaskDTO achiTask, String userId) {
        LambdaQueryWrapper<AchiTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.//开始时间
                ge(Objects.nonNull(achiTask.getAchiStart()), AchiTask::getAchiStart, achiTask.getAchiStart())
                //结束时间
                .le(Objects.nonNull(achiTask.getAchiStop()), AchiTask::getAchiStop, achiTask.getAchiStop())
                //考核任务名称
                .like(Objects.nonNull(achiTask.getAchiName()), AchiTask::getAchiName, achiTask.getAchiName())
                //考核任务类型
                .like(Objects.nonNull(achiTask.getTypeName()), AchiTask::getTypeName, achiTask.getTypeName())
                //考核人名称
                .eq(StringUtils.hasText(userId), AchiTask::getWorkId, userId)
                //查询是否删除
                .eq(AchiTask::getStatus, AchiEnum.DELETE_FALSE.code())
                //根据考核开始时间倒序
                .orderByDesc(AchiTask::getAchiStart);
        if (achiTask.getPageNum() == null) {
            achiTask.setPageNum(1);
        }
        if (achiTask.getPageSize() == null) {
            achiTask.setPageSize(20);
        }
        Page<AchiTask> page = new Page<>(achiTask.getPageNum(), achiTask.getPageSize());
        return achiTaskMapper.selectPage(page, queryWrapper);
    }

    /**
     * 考核汇总查询
     *
     * @param achiTask
     * @return
     */
    @Override
    public Page<AchiTask> selectTask(AchiTaskDTO achiTask) {
        LambdaQueryWrapper<AchiTask> queryWrapper = new LambdaQueryWrapper<>();
        //判断考核任务状态
        queryWrapper.eq(Objects.nonNull(achiTask.getAchiStatus()), AchiTask::getAchiStatus, achiTask.getAchiStatus())
                //部门经理查询
                .ne(Objects.nonNull(achiTask.getManagerStatus()), AchiTask::getManagerStatus, AchiEnum.ZERO.code())
                //处室主任查询
                .ne(Objects.nonNull(achiTask.getDirectorStatus()), AchiTask::getDirectorStatus, AchiEnum.ZERO.code())
                //考核任务名称
                .eq(Objects.nonNull(achiTask.getAchiName()), AchiTask::getAchiName, achiTask.getAchiName())
                //大于考核开始时间
                .ge(Objects.nonNull(achiTask.getAchiStart()), AchiTask::getAchiStart, achiTask.getAchiStart())
                //小于考核结束时间
                .le(Objects.nonNull(achiTask.getAchiStop()), AchiTask::getAchiStart, achiTask.getAchiStop())
                //所属部门判断
                .like(StringUtils.hasText(achiTask.getDepartment()), AchiTask::getDepartment, achiTask.getDepartment())
                //考核类型判断
                .like(StringUtils.hasText(achiTask.getTypeName()), AchiTask::getTypeName, achiTask.getTypeName())
                //查询在用考核任务
                .eq(AchiTask::getStatus, AchiEnum.DELETE_FALSE.code())
                //所属单位判断
                .like(StringUtils.hasText(achiTask.getCompany()), AchiTask::getCompany, achiTask.getCompany())
                //账号或者名称查询
                .and(StringUtils.hasText(achiTask.getNameOrId()), (wrapper) -> {
                    wrapper.like(AchiTask::getWorkId, achiTask.getNameOrId()).or().like(AchiTask::getWorkName, achiTask.getNameOrId());
                })
                //根据考核开始时间倒序
                .orderByDesc(AchiTask::getAchiStart);
        //分页
        Page<AchiTask> page = new Page<>(achiTask.getPageNum(), achiTask.getPageSize());
        logger.info("start search ======{}", achiTask);
        return achiTaskMapper.selectPage(page, queryWrapper);
    }

    @Transactional
    @Override
    public Integer updateTask(AchiTask achiTask) {
        //考核人已提交修改处室主任可查看状态
        if (achiTask.getAchiStatus() != null && achiTask.getAchiStatus().intValue() == TWO.code()) {
            achiTask.setDirectorStatus(TWO.code());
        }
        //处室主任已提交修改部门经理可查看状态
        if (achiTask.getDirectorStatus() != null && achiTask.getDirectorStatus().intValue() == FOUR.code()) {
            achiTask.setManagerStatus(TWO.code());
        }
        logger.info("开始修改考核任务状态======{}", achiTask);
        return achiTaskMapper.updateById(achiTask);
    }

    @Override
    public AchiTask selectDtail(AchiTask achiTask) {
        return achiTaskMapper.selectById(achiTask);
    }

    @Override
    public List<AchiTask> selectByAchiId(String achiId) {
        List<AchiTask> list = achiTaskMapper.selectByAchiId(achiId);
        return list;
    }

    @Override
    public boolean uploadFile(MultipartFile file, String domainName) {

        List<AchiTask> list = new ArrayList<>();
        List<ExcelPojo> excelPojos = new ArrayList<>();
        try {
            JSONArray jsonArray = ExcelUtils.readMultipartFile(file);
            excelPojos = ExcelUtils.readMultipartFile(file, ExcelPojo.class);
            for (int i = 0; i < excelPojos.size(); i++) {
                AchiTask achiTask = new AchiTask();
                String achi_start = excelPojos.get(i).getAchi_start();
                String achi_stop = excelPojos.get(i).getAchi_stop();
                if (achi_start != null) {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(achi_start);
                    long l = date.getTime();
                    achiTask.setAchiStart(l);
                }
                if (achi_stop != null) {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(achi_stop);
                    long l = date.getTime();
                    achiTask.setAchiStop(l);
                }
                achiTask.setCompany(domainName);
                achiTask.setDepartment(excelPojos.get(i).getDepartment());
                achiTask.setWorkId(excelPojos.get(i).getWork_id());
                achiTask.setWorkName(excelPojos.get(i).getWork_name());
                achiTask.setAchiName(excelPojos.get(i).getAchiName());
                achiTask.setTypeName(excelPojos.get(i).getTypeName());
                achiTask.setAchiStatus(3);
                achiTask.setManagerStatus(5);
                achiTask.setDirectorStatus(5);
                achiTask.setStatus(1);


                JSONObject o = (JSONObject) jsonArray.get(i);
                JSONObject n = new JSONObject();
                o.forEach((k, v) -> {
                    if (StringUtils.hasText(v + "")) {
                        if (!scape.contains(k)) {
                            n.put(k, v);
                        }
                    }
                });

                achiTask.setDetailScore(n.toString());
                list.add(achiTask);
            }
            return saveBatch(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 招标业务考核分计算
     *
     * @param jbrCode   投标人Code（经办人唯一标识）
     * @param startTime
     * @param endTime
     * @return
     */
    public String calcZhaobiaoyewuScore(String jbrCode, Date startTime, Date endTime) {
        ArrayNode objectNodeAll = JsonUtil.createArrayNode();
        DateTime start = DateUtil.parse(com.coredata.utils.date.DateUtil.df.format(startTime));
        DateTime stop = DateUtil.parse(com.coredata.utils.date.DateUtil.df.format(endTime));
        try {
            List<JixiaoInfo> jixiaoInfos = jixiaoInfoMapper.queryCale(jbrCode);

            List<JixiaoInfo> allList = jixiaoInfos.stream().filter(bean -> checkTime(bean.getProjectSlDate(), start, stop)).collect(Collectors.toList());

            //查询经办人本年度参与投标项目列表

            ArrayNode objectNodeBase = JsonUtil.createArrayNode();
            ArrayNode objectNodeNanDu = JsonUtil.createArrayNode();
            ArrayNode objectNodeXiaoLv = JsonUtil.createArrayNode();
            ArrayNode objectNodeXiaoYi = JsonUtil.createArrayNode();
            ArrayNode objectNodeFengXian = JsonUtil.createArrayNode();
            ArrayNode objectNodeXuanze = JsonUtil.createArrayNode();
            //难度系数
            double zhOpenBid = 0;
            double zhOpenzhaobiao = 0;
            double zhOpenJingtan = 0;
            double zhOpenYaoqing = 0;
            double zhOpenXunJia = 0;
            double zhDanyi = 0;
            double zhXunjia = 0;
            double cnDid = 0;
            double cnyaoqing = 0;
            double cnjingtan = 0;
            double cnzhaobiao = 0;
            double yuShenHouNextStep = 0;
            double duoBiaoDuan = 0;
            double tbRenShuLiang = 0;
            double zongHePingfen = 0;
            double zhiZhiPingCe = 0;
            double chongXinZhaoBiao = 0;
            double buZouSanJia = 0;
            double twoStep = 0;
            double isOnline = 0;
            double chuChaiZhiJyzx = 0;//出差至地方交易中心完成评审工作的标(jiaoxi_info里无这个字段)
            double daYuYiNian = 0;//大于一年的框架协议项目
            double qianwangWaidi = 0;//前往至外地项目单位完成评审工作的标
            double zhongDianXianmu = 0;//集团公司重点项目（列入集团公司重点项目清单）或1亿元（含）以上的标(jiaoxi_info里无这个字段)
            double guoJiaGuanWang = 0;//国家管网项目采办申请
            double tuijian = 0;//项目单位推荐招标经理并由招标经理承接此项目
            double nanDuXishuTotal = 0;//所有项目难度系数总值
            double caibanxiangmu = 0;//完成的招标采办项目
            double jiduan = 0;
            //效率系数
            double suoDuan = 0;
            double chaoShi = 0;
            double jieZiLv = 0;
            double bidSocre = 0;
            if (!allList.isEmpty()) {
                int compreBidNum = 0;
                int isOnlineNum = 0;
                for (JixiaoInfo jixiaoInfo : allList) {
                    //遍历项目名称查询该项目所有标段列表
                    List<JixiaoInfo> bidList = jixiaoInfos.stream().filter(bean -> bean.getProjectno().equals(jixiaoInfo.getProjectno())).collect(Collectors.toList());
                    List<String> bidNameList = new ArrayList<>();
                    //项目中有标风险类型为违法、违纪，整个项目不得分
                    if (bidList != null && !bidList.isEmpty()) {
                        long weiFaCount = bidList.parallelStream().filter(item -> Objects.nonNull(item.getWeijiNum()) && item.getWeijiNum() > 0).count();
                        if (weiFaCount > 0) {
                            continue;
                        }
                    }
                    //遍历项目所有标段，计算标段绩效分数
                    for (JixiaoInfo item : bidList) {
                        double itemBidScore = 0;//单一标段总分

                        if (item.getDefinemethod() != null && item.getDefinemethod().contains("综合评分法")) {
                            compreBidNum++;
                        }
                        if (item.getIsOnline() != null && item.getIsOnline().contains("是") && item.getZbDate().compareTo(start) >= 0 && item.getZbDate().compareTo(stop) <= 0) {
                            isOnlineNum++;
                        }
                        if (item.getBiaoduanname() != null && item.getBiaoduanname().contains("第一次重新招标")) {
                            bidNameList.add("第一次重新招标");
                        } else if (item.getBiaoduanname() != null && item.getBiaoduanname().contains("第二次重新招标")) {
                            bidNameList.add("第二次重新招标");
                        } else if (item.getBiaoduanname() != null && item.getBiaoduanname().contains("第三次重新招标")) {
                            bidNameList.add("第三次重新招标");
                        }
                        //招标方式分数计算
                        if (item.getZhaobiaofangshi() != null) {
                            bidSocre = bidSocre + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                            itemBidScore = itemBidScore + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                            switch (item.getZhaobiaofangshi()) {
                                case "国内公开资格预审":
                                    zhOpenBid = zhOpenBid + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                case "国内公开招标":
                                    zhOpenzhaobiao = zhOpenzhaobiao + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                case "国内邀请招标":
                                    zhOpenYaoqing = zhOpenYaoqing + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                case "国内竞谈":
                                    zhOpenJingtan = zhOpenJingtan + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                case "国内公开询价":
                                    zhOpenXunJia = zhOpenXunJia + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                case "国内单一来源":
                                    zhDanyi = zhDanyi + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                case "国内邀请询价":
                                    zhXunjia = zhXunjia + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                case "国际公开资格预审":
                                    cnDid = cnDid + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                case "国际公开招标":
                                    cnzhaobiao = cnzhaobiao + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                case "国际邀请招标":
                                    cnyaoqing = cnyaoqing + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                case "国际竞谈":
                                    cnjingtan = cnjingtan + AchiSocreCalcUtil.biddingMethodSocre(item.getZhaobiaofangshi());
                                    break;
                                default:
                                    break;
                            }
                        }
                        //公开资格预审后直接进行下一步采办方式完成招标分数计算
                        if (item.getNextPurchase() == null) {
                            item.setNextPurchase("");
                        }
                        if (item.getZbStage() != null && item.getZbSendDate() != null && item.getNextPurchase().contains("是") && checkTime(item.getZbDate(), start, stop) == true) {
                            bidSocre = bidSocre + AchiSocreCalcUtil.nextStepCompleteMethodType(item.getZhaobiaofangshi());
                            itemBidScore = itemBidScore + AchiSocreCalcUtil.nextStepCompleteMethodType(item.getZhaobiaofangshi());
                            yuShenHouNextStep = yuShenHouNextStep + AchiSocreCalcUtil.nextStepCompleteMethodType(item.getZhaobiaofangshi());
                        }
                        //投标人数量分数计算
                        if (item.getTbgysNum() != null) {
                            bidSocre = bidSocre + AchiSocreCalcUtil.bidderSocre(item.getTbgysNum());
                            itemBidScore = itemBidScore + AchiSocreCalcUtil.bidderSocre(item.getTbgysNum());
                            tbRenShuLiang = tbRenShuLiang + AchiSocreCalcUtil.bidderSocre(item.getTbgysNum());
                        }
                        //采用线下纸质版评标分数计算
                        if (item.getIsusewebztb() != null) {
                            bidSocre = bidSocre + AchiSocreCalcUtil.paperReviewSocre(item.getIsusewebztb(), item.getZhaobiaofangshi());
                            itemBidScore = itemBidScore + AchiSocreCalcUtil.paperReviewSocre(item.getIsusewebztb(), item.getZhaobiaofangshi());
                            zhiZhiPingCe = zhiZhiPingCe + AchiSocreCalcUtil.paperReviewSocre(item.getIsusewebztb(), item.getZhaobiaofangshi());
                        }
                        //前往至外地项目单位完成评审工作的标分数计算
                        if (item.getZhiwaidi() != null && item.getZhiwaidi().contains("是")) {
                            if (checkBidMethod(item.getZhaobiaofangshi())) {
                                bidSocre = bidSocre + 5;
                                itemBidScore = itemBidScore + 5;
                                qianwangWaidi = qianwangWaidi + 5;
                            }
                        }
                        //待定
                        //出差至地方交易中心完成评审工作的标分数计算
                        if (item.getIsTravel() != null && item.getIsTravel().contains("是") && item.getIsTravel().contains("首次")) {
                            if (checkBidMethod(item.getZhaobiaofangshi())) {
                                bidSocre = bidSocre + 60;
                                itemBidScore = itemBidScore + 60;
                                chuChaiZhiJyzx = chuChaiZhiJyzx + 60;
                            }
                        } else if (item.getIsTravel() != null && item.getIsTravel().equals("是")) {
                            if (checkBidMethod(item.getZhaobiaofangshi())) {
                                bidSocre = bidSocre + 30;
                                itemBidScore = itemBidScore + 30;
                                chuChaiZhiJyzx = chuChaiZhiJyzx + 30;
                            }
                        }

                        //大于一年的框架协议项目分数计算
                        if (item.getGreaterthan() != null && item.getIsframe().contains("是") && item.getGreaterthan().contains("框架协议已收取第2次或以上的费用")) {
                            long result = item.getContractClosedate().getTime() - item.getContractStartdate().getTime();
                            if (result > 31536000000L) {
                                bidSocre = bidSocre + 2;
                                itemBidScore = itemBidScore + 2;
                                daYuYiNian = daYuYiNian + 2;
                            }
                        }
                        //经批准的两步法招标的标段分数计算
                        if (item.getIsneedsecondenvelope() != null) {
                            if (checkBidMethod(item.getZhaobiaofangshi())) {
                                bidSocre = bidSocre + AchiSocreCalcUtil.twoStepSocre(item.getIsneedsecondenvelope());
                                itemBidScore = itemBidScore + AchiSocreCalcUtil.twoStepSocre(item.getIsneedsecondenvelope());
                                twoStep = twoStep + AchiSocreCalcUtil.twoStepSocre(item.getIsneedsecondenvelope());
                            }
                        }
                        //是否集团公司重点项目（列入集团公司重点项目清单）或1亿元（含）以上的标分数计算
                        if (item.getZhongdianxm() != null && item.getZhongdianxm().contains("是")) {
                            if (checkBidMethod(item.getZhaobiaofangshi())) {
                                bidSocre = bidSocre + 1;
                                itemBidScore = itemBidScore + 1;
                                zhongDianXianmu = zhongDianXianmu + 1;
                            }
                        }
                        //不足3家重新招标（按标段）分数计算
                        if (item.getYichangreason() != null && item.getTodoResult() != null) {
                            bidSocre = bidSocre + AchiSocreCalcUtil.noEnoughThreeBidderSocre(item.getYichangreason(), item.getTodoResult());
                            itemBidScore = itemBidScore + AchiSocreCalcUtil.noEnoughThreeBidderSocre(item.getYichangreason(), item.getTodoResult());
                            buZouSanJia = buZouSanJia + AchiSocreCalcUtil.noEnoughThreeBidderSocre(item.getYichangreason(), item.getTodoResult());
                        }

                        //缩短天数分数计算
                        if (item.getZhaobiaofangshi() != null && item.getProjectSlDate() != null && item.getZbSendDate() != null) {
                            bidSocre = bidSocre + AchiSocreCalcUtil.shortDaySocre(item.getZhaobiaofangshi(), item.getProjectSlDate(), item.getZbSendDate());
                            itemBidScore = itemBidScore + AchiSocreCalcUtil.shortDaySocre(item.getZhaobiaofangshi(), item.getProjectSlDate(), item.getZbSendDate());
                            if (AchiSocreCalcUtil.shortDaySocre(item.getZhaobiaofangshi(), item.getProjectSlDate(), item.getZbSendDate()) < 0) {
                                chaoShi = chaoShi + AchiSocreCalcUtil.shortDaySocre(item.getZhaobiaofangshi(), item.getProjectSlDate(), item.getZbSendDate());
                            } else if (AchiSocreCalcUtil.shortDaySocre(item.getZhaobiaofangshi(), item.getProjectSlDate(), item.getZbSendDate()) > 0) {
                                suoDuan = suoDuan + AchiSocreCalcUtil.shortDaySocre(item.getZhaobiaofangshi(), item.getProjectSlDate(), item.getZbSendDate());
                            }
                            bidSocre = (double) Math.round(bidSocre * 100) / 100;
                            itemBidScore = (double) Math.round(itemBidScore * 100) / 100;
                            chaoShi = (double) Math.round(chaoShi * 100) / 100;
                            suoDuan = (double) Math.round(suoDuan * 100) / 100;
                        }
                        //节资率超过20%分数计算
                        if (item.getTouzigusuanzh() != null && item.getSpZhongbiaomoneyzh() != null) {
                            bidSocre = bidSocre + AchiSocreCalcUtil.saveResourcePercentSocre(item.getTouzigusuanzh().longValue(), item.getSpZhongbiaomoneyzh().longValue());
                            itemBidScore = itemBidScore + AchiSocreCalcUtil.saveResourcePercentSocre(item.getTouzigusuanzh().longValue(), item.getSpZhongbiaomoneyzh().longValue());
                            jieZiLv = jieZiLv + AchiSocreCalcUtil.saveResourcePercentSocre(item.getTouzigusuanzh().longValue(), item.getSpZhongbiaomoneyzh().longValue());
                        }
                        //项目单位推荐招标经理并由招标经理承接此项目
                        if (item.getRecoCompany() != null && item.getRecoCompany().contains("是")) {
                            bidSocre = bidSocre + 1;
                            itemBidScore = itemBidScore + 1;
                            tuijian = tuijian + 1;
                        }
                        //风险系数分数计算
                        if (item.getTousuNum() != null && item.getTousuNum() > 0) {
                            bidSocre = bidSocre + -1 * itemBidScore * 0.5;
                            itemBidScore = itemBidScore + -1 * itemBidScore * 0.5;
                            objectNodeFengXian.add(JsonUtil.createObjectNode().put("自身原因导致的异议或投诉", -1 * itemBidScore * 0.5));
                        } else {
                            objectNodeFengXian.add(JsonUtil.createObjectNode().put("自身原因导致的异议或投诉", 0));
                        }
                        if (item.getZhiliangNum() != null && item.getZhiliangNum() > 0) {
                            bidSocre = bidSocre + item.getZhiliangNum();
                            itemBidScore = itemBidScore + item.getZhiliangNum();
                            objectNodeFengXian.add(JsonUtil.createObjectNode().put("招标质量", -1 * item.getZhiliangNum()));
                        } else {
                            objectNodeFengXian.add(JsonUtil.createObjectNode().put("招标质量", 0));
                        }
                        if (item.getTuihuanNum() != null && item.getTuihuanNum() > 0) {
                            bidSocre = bidSocre - 1;
                            itemBidScore = itemBidScore - 1;
                            objectNodeFengXian.add(JsonUtil.createObjectNode().put("未按时退还保证金（按标段）", -1));
                        } else {
                            objectNodeFengXian.add(JsonUtil.createObjectNode().put("未按时退还保证金（按标段）", 0));
                        }
                        if (item.getFwfNum() != null && item.getFwfNum() > 0) {
                            bidSocre = bidSocre - 2;
                            itemBidScore = itemBidScore - 2;
                            objectNodeFengXian.add(JsonUtil.createObjectNode().put("未按时收取中标服务费（按标段）", -2));
                        } else {
                            objectNodeFengXian.add(JsonUtil.createObjectNode().put("未按时收取中标服务费（按标段）", 0));
                        }
                        if (item.getZbSendDate() != null && checkTime(item.getZbSendDate(), start, stop) && item.getTenderProject() != null && item.getTenderProject().contains("是")) {
                            bidSocre = bidSocre + 1;
                            itemBidScore = itemBidScore + 1;
                            jiduan = jiduan + 1;
                        }
                    }
                    //多标段分数计算
                    if (bidList.size() > 1) {
                        bidSocre = bidSocre + AchiSocreCalcUtil.manyBiddingSocre(bidList.size());
                        duoBiaoDuan = duoBiaoDuan + AchiSocreCalcUtil.manyBiddingSocre(bidList.size());
                    }
                    //是否必联网分数计算
                    if (isOnlineNum > 0) {
                        bidSocre = bidSocre + AchiSocreCalcUtil.isOnlineSocre(isOnlineNum);
                        isOnline = isOnline + AchiSocreCalcUtil.isOnlineSocre(isOnlineNum);
                    }
                    //采用综合评分法进行评审分数计算
                    if (compreBidNum > 0) {
                        bidSocre = bidSocre + AchiSocreCalcUtil.comprehensiveSocre(compreBidNum);
                        zongHePingfen = zongHePingfen + AchiSocreCalcUtil.comprehensiveSocre(compreBidNum);
                    }
                    //开标后重新招标（按标段）分数计算
                    if (!bidNameList.isEmpty()) {
                        if (bidNameList.contains("第三次重新招标")) {
                            bidSocre = AchiSocreCalcUtil.reBiddingSocre(bidSocre, "第三次重新招标");
                            chongXinZhaoBiao = chongXinZhaoBiao + AchiSocreCalcUtil.reBiddingSocre(bidSocre, "第三次重新招标");
                        } else if (bidNameList.contains("第二次重新招标")) {
                            bidSocre = AchiSocreCalcUtil.reBiddingSocre(bidSocre, "第二次重新招标");
                            chongXinZhaoBiao = chongXinZhaoBiao + AchiSocreCalcUtil.reBiddingSocre(bidSocre, "第二次重新招标");
                        } else if (bidNameList.contains("第一次重新招标")) {
                            bidSocre = AchiSocreCalcUtil.reBiddingSocre(bidSocre, "第一次重新招标");
                            chongXinZhaoBiao = chongXinZhaoBiao + AchiSocreCalcUtil.reBiddingSocre(bidSocre, "第一次重新招标");
                        }
                    }
                }

            } else {
                objectNodeFengXian.add(JsonUtil.createObjectNode().put("自身原因导致的异议或投诉", 0));
                objectNodeFengXian.add(JsonUtil.createObjectNode().put("招标质量", 0));
                objectNodeFengXian.add(JsonUtil.createObjectNode().put("未按时退还保证金（按标段）", 0));
                objectNodeFengXian.add(JsonUtil.createObjectNode().put("未按时收取中标服务费（按标段）", 0));
            }

            objectNodeNanDu.add(JsonUtil.createObjectNode().put("国内公开资格预审、公开招标", zhOpenBid));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("国内公开招标", zhOpenzhaobiao));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("国内邀请招标", zhOpenYaoqing));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("国内竞谈", zhOpenJingtan));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("国内单一来源", zhDanyi));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("国内询价", zhXunjia));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("国际公开资格预审", cnDid));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("国际公开招标", cnzhaobiao));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("邀请招标", cnyaoqing));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("国际竞谈", cnjingtan));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("公开资格预审后直接进行下一步采办方式完成招标", yuShenHouNextStep));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("投标人数量", tbRenShuLiang));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("采用线下纸质版评标", zhiZhiPingCe));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("是否出差至地方交易中心完成评审工作的标", chuChaiZhiJyzx));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("前往至外地项目单位完成评审工作的标", qianwangWaidi));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("大于一年的框架协议项目是否已收取第2次或以上费用", daYuYiNian));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("经批准的两步法招标的标段", twoStep));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("集团公司重点项目（列入集团公司重点项目清单）或1亿元（含）以上的标", zhongDianXianmu));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("不足3家重新招标(按标段)", buZouSanJia));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("多标段", duoBiaoDuan));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("采用综合评分法进行评审", zongHePingfen));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("开标后重新招标(按标段)", chongXinZhaoBiao));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("是否必联网操作", isOnline));
            objectNodeNanDu.add(JsonUtil.createObjectNode().put("国家管网项目采办申请", guoJiaGuanWang));

            nanDuXishuTotal = zhOpenBid + zhOpenYaoqing + zhOpenzhaobiao + zhOpenJingtan + zhDanyi + zhXunjia + cnDid + cnzhaobiao + cnyaoqing + cnjingtan
                    + yuShenHouNextStep + duoBiaoDuan + tbRenShuLiang
                    + zongHePingfen + zhiZhiPingCe + twoStep + chongXinZhaoBiao + buZouSanJia + isOnline
                    + chuChaiZhiJyzx + daYuYiNian + qianwangWaidi + zhongDianXianmu + guoJiaGuanWang;

            objectNodeXiaoLv.add(JsonUtil.createObjectNode().put("每缩短1天", suoDuan));
            objectNodeXiaoLv.add(JsonUtil.createObjectNode().put("每超时1天", chaoShi));

            objectNodeXiaoYi.add(JsonUtil.createObjectNode().put("节资率超过20%", jieZiLv));
            objectNodeXuanze.add(JsonUtil.createObjectNode().put("项目单位推荐招标经理并由招标经理承接此项目", tuijian));

            ObjectNode nanDU = JsonUtil.createObjectNode();
            nanDU.replace("难度系数", objectNodeNanDu);
            objectNodeBase.add(nanDU);
            ObjectNode Xuanze = JsonUtil.createObjectNode();
            Xuanze.replace("选择系数", objectNodeXuanze);
            objectNodeBase.add(Xuanze);
            ObjectNode xiaoLv = JsonUtil.createObjectNode();
            xiaoLv.replace("效率系数（以标为单位）", objectNodeXiaoLv);
            objectNodeBase.add(xiaoLv);
            ObjectNode xiaoYi = JsonUtil.createObjectNode();
            xiaoYi.replace("效益系数（以标为单位）", objectNodeXiaoYi);
            objectNodeBase.add(xiaoYi);
            ObjectNode fengXian = JsonUtil.createObjectNode();
            fengXian.replace("风险系数", objectNodeFengXian);
            objectNodeBase.add(fengXian);

            ObjectNode jiChu = JsonUtil.createObjectNode();
            jiChu.replace("基础考核信息", objectNodeBase);
            objectNodeAll.add(jiChu);

            //个人绩效考核分数计算

            LambdaQueryWrapper<AchiContribution> queryWrapperContribution = new LambdaQueryWrapper<>();
            queryWrapperContribution.eq(AchiContribution::getWorkId, jbrCode).like(AchiContribution::getTypeName, "招标业务").like(AchiContribution::getConYear, sdf.format(startTime)).eq(AchiContribution::getStatus, 1);
            List<AchiContribution> achiContributionList = achiContributionMapper.selectList(queryWrapperContribution);
            ArrayNode objectNodeGeren = personContributionScore(achiContributionList, "招标业务", nanDuXishuTotal, bidSocre);
            ObjectNode geRenGongxiao = JsonUtil.createObjectNode();
            geRenGongxiao.replace("个人绩效贡献", objectNodeGeren);
            objectNodeAll.add(geRenGongxiao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonUtil.writeValueAsString(objectNodeAll);
    }

    /**
     * 公平系数、双选系数、额外贡献系数计算
     *
     * @param list            个人贡献列表
     * @param type            计算分数分类（招标业务、品类采购、外部市场）
     * @param nanDuXishuTotal 难度系数总分
     * @param bidSocre        总分累计（不需要的传空即可）
     * @return
     */
    public ArrayNode personContributionScore(List<AchiContribution> list, String type, double nanDuXishuTotal, Double bidSocre) {
        ArrayNode objectNodeGeren = JsonUtil.createArrayNode();
        if (!list.isEmpty()) {
            AchiContribution achiContributionOne = list.get(0);

            // 公平系数
            ArrayNode fairFactor = JsonUtil.createArrayNode();
            if (achiContributionOne.getwStartTime() != null) {
                LocalDateTime wStartTime = Instant.ofEpochMilli(achiContributionOne.getwStartTime()).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
                int workYear = LocalDateTime.now().getYear() - wStartTime.getYear();
                if (workYear >= 30) {
                    if (bidSocre != null) {
                        bidSocre = bidSocre + (double) Math.round(0.2 * nanDuXishuTotal / 100);
                    }
                    fairFactor.add(JsonUtil.createObjectNode().put("超过30年工龄（含30年）", (double) Math.round(0.2 * nanDuXishuTotal * 100) / 100));
                } else if (workYear <= 30 && workYear >= 20) {
                    if (bidSocre != null) {
                        bidSocre = bidSocre + (double) Math.round(0.1 * nanDuXishuTotal / 100);
                    }
                    fairFactor.add(JsonUtil.createObjectNode().put("超过20年工龄（含20年），不足30年工龄", (double) Math.round(0.1 * nanDuXishuTotal / 100)));
                } else if (workYear < 20) {
                    fairFactor.add(JsonUtil.createObjectNode().put("工龄20年以内", 0));
                }
            } else {
                fairFactor.add(JsonUtil.createObjectNode().put("超过30年工龄（含30年）", 0));
                fairFactor.add(JsonUtil.createObjectNode().put("超过20年工龄（含20年），不足30年工龄", 0));
                fairFactor.add(JsonUtil.createObjectNode().put("工龄20年以内", 0));
            }
            ObjectNode objectNode1 = JsonUtil.createObjectNode();
            objectNode1.replace("公平系数", fairFactor);
            objectNodeGeren.add(objectNode1);
            // 额外贡献系数
            ArrayNode contributionFactor = JsonUtil.createArrayNode();
            double classHour = 0;
            double wWork = 0;
            double write = 0;
            double system = 0;
            double works = 0;
            double special = 0;
            double tration = 0;
            double apprentice = 0;
            double twozero = 0;
            double shumian = 0;
            double honor = 0;
            double piping = 0;
            double shumiantousu = 0;

            for (AchiContribution achiContribution : list) {
                if (achiContribution.getClassHour() != null) {
                    classHour = classHour + achiContribution.getClassHour() * 10;
                }

                if (achiContribution.getwWork() != null) {
                    wWork = wWork + 1.5 * achiContribution.getwWork();
                }

                if (achiContribution.getwWrite() != null) {
                    write = write + 20 * achiContribution.getwWrite();
                }

                if (achiContribution.getwSystem() != null) {
                    system = system + 50 * achiContribution.getwSystem();
                }

                if (achiContribution.getWorkers() != null) {
                    works = works + 5 * achiContribution.getWorkers();
                }

                if (achiContribution.getSpecial() != null) {
                    special = special + achiContribution.getSpecial();
                }

                if (achiContribution.getTration() != null) {
                    if (achiContribution.getTration() > 10) {
                        tration = tration + 10;
                    } else {
                        tration = tration + achiContribution.getTration();
                    }
                }

                if (achiContribution.getApprentice() != null) {
                    apprentice = apprentice + 5 * achiContribution.getApprentice();
                }
                if ("招标业务".equals(type)) {
                    twozero = twozero + achiContribution.getIssueTime();
                }
            }
            contributionFactor.add(JsonUtil.createObjectNode().put("培训：每课时（≤1.5小时）", classHour));
            contributionFactor.add(JsonUtil.createObjectNode().put("抽调、巡视、审计（按日加分）", wWork));
            contributionFactor.add(JsonUtil.createObjectNode().put("建章立制", system));
            contributionFactor.add(JsonUtil.createObjectNode().put("党工群", works));
            contributionFactor.add(JsonUtil.createObjectNode().put("专项工作", special));
            contributionFactor.add(JsonUtil.createObjectNode().put("处室内其它管理工作", tration));
            contributionFactor.add(JsonUtil.createObjectNode().put("师带徒", apprentice));
            if ("招标业务".equals(type)) {
                contributionFactor.add(JsonUtil.createObjectNode().put("通过2.0系统完成的招标采办项目每完成一个标段", twozero));
            }
            if ("招标业务".equals(type) || "外部市场".equals(type)) {
                contributionFactor.add(JsonUtil.createObjectNode().put("业务标准编写", write));
            }
            if ("品类采购".equals(type)) {
                //书面表扬
                String praiseTimes = achiContributionOne.getWrittenPraiseTimes();
                if (Objects.nonNull(praiseTimes)) {
                    Map<String, Integer> result = JsonUtil.parseObject(praiseTimes, new TypeReference<>() {
                    });
                    if (Objects.nonNull(result.get("集团公司书面表扬次数"))) {
                        shumian = shumian + 30 * result.get("集团公司书面表扬次数");
                    }
                    if (Objects.nonNull(result.get("二级单位书面表扬次数"))) {
                        shumian = shumian + 20 * result.get("二级单位书面表扬次数");
                    }
                    if (Objects.nonNull(result.get("三级单位书面表扬次数"))) {
                        shumian = shumian + 10 * result.get("三级单位书面表扬次数");
                    }
                    if (Objects.nonNull(result.get("四级单位书面表扬次数"))) {
                        shumian = shumian + 5 * result.get("四级单位书面表扬次数");
                    }
                    if (shumian >= 60) {
                        shumian = 60;
                    }
                }
                //获得荣誉
                String honorTypeQty = achiContributionOne.getHonorTypeQty();
                if (Objects.nonNull(honorTypeQty)) {
                    Map<String, Integer> result = JsonUtil.parseObject(honorTypeQty, new TypeReference<>() {
                    });
                    if (Objects.nonNull(result.get("国家级荣誉次数"))) {
                        honor = honor + 60;
                    }
                    if (Objects.nonNull(result.get("集团公司荣誉次数"))) {
                        honor = honor + 30;
                    }
                    if (Objects.nonNull(result.get("中心级荣誉次数"))) {
                        honor = honor + 20;
                    }
                    if (Objects.nonNull(result.get("中业务部级荣誉次数"))) {
                        honor = honor + 10;
                    }

                }
                //需求单位书面
                if (achiContributionOne.getWrittenComplaintsTimes() != null) {
                    shumiantousu = shumiantousu - 5 * achiContributionOne.getWrittenComplaintsTimes();
                }
                //受到批评
                String criticismTimes = achiContributionOne.getCriticismTimes();
                if (Objects.nonNull(criticismTimes)) {
                    Map<String, Integer> result = JsonUtil.parseObject(criticismTimes, new TypeReference<>() {
                    });
                    if (Objects.nonNull(result.get("集团公司层面批评次数"))) {
                        piping = piping - 30 * result.get("集团公司层面批评次数");
                    }
                    if (Objects.nonNull(result.get("二级单位层面批评次数"))) {
                        piping = piping - 20 * result.get("二级单位层面批评次数");
                    }
                    if (Objects.nonNull(result.get("三级单位层面批评次数"))) {
                        piping = piping - 10 * result.get("三级单位层面批评次数");
                    }
                    if (Objects.nonNull(result.get("四级单位层面批评次数"))) {
                        piping = piping - 5 * result.get("四级单位层面批评次数");
                    }
                }

            }
            //加分因素
            ArrayNode jiafenyinsu = JsonUtil.createArrayNode();
            jiafenyinsu.add(JsonUtil.createObjectNode().put("书面表扬", shumian));
            jiafenyinsu.add(JsonUtil.createObjectNode().put("获得荣誉", honor));
            ObjectNode jiafen = JsonUtil.createObjectNode();
            jiafen.set("加分因素", jiafenyinsu);
            //扣分因素
            ArrayNode koufenyinsu = JsonUtil.createArrayNode();
            koufenyinsu.add(JsonUtil.createObjectNode().put("受到批评", piping));
            koufenyinsu.add(JsonUtil.createObjectNode().put("需求单位书面投诉", shumiantousu));
            ObjectNode koufen = JsonUtil.createObjectNode();
            koufen.set("扣分因素", koufenyinsu);
            ObjectNode objectNode3 = JsonUtil.createObjectNode();
            if ("品类采购".equals(type)) {
                objectNode3.replace("额外贡献系数和惩罚", contributionFactor);
            } else {
                objectNode3.replace("额外贡献系数", contributionFactor);
            }
            objectNodeGeren.add(objectNode3);
        } else {

            // 公平系数
            ArrayNode fairFactor = JsonUtil.createArrayNode();
            fairFactor.add(JsonUtil.createObjectNode().put("超过30年工龄（含30年）", 0));
            fairFactor.add(JsonUtil.createObjectNode().put("超过20年工龄（含20年），不足30年工龄", 0));
            fairFactor.add(JsonUtil.createObjectNode().put("工龄20年以内", 0));

            ObjectNode objectNode1 = JsonUtil.createObjectNode();
            objectNode1.replace("公平系数", fairFactor);
            objectNodeGeren.add(objectNode1);
            // 额外贡献系数
            ArrayNode contributionFactor = JsonUtil.createArrayNode();
            double classHour = 0;
            double wWork = 0;
            double write = 0;
            double system = 0;
            double works = 0;
            double special = 0;
            double tration = 0;
            double apprentice = 0;
            double twozero = 0;
            double shumian = 0;
            double honor = 0;
            double piping = 0;
            double shumiantousu = 0;
            contributionFactor.add(JsonUtil.createObjectNode().put("培训：每课时（≤1.5小时）", classHour));
            contributionFactor.add(JsonUtil.createObjectNode().put("抽调、巡视、审计（按日加分）", wWork));
            contributionFactor.add(JsonUtil.createObjectNode().put("建章立制", system));
            contributionFactor.add(JsonUtil.createObjectNode().put("党工群", works));
            contributionFactor.add(JsonUtil.createObjectNode().put("专项工作", special));
            contributionFactor.add(JsonUtil.createObjectNode().put("处室内其它管理工作", tration));
            contributionFactor.add(JsonUtil.createObjectNode().put("师带徒", apprentice));
            if ("招标业务".equals(type) || "外部市场".equals(type)) {
                contributionFactor.add(JsonUtil.createObjectNode().put("业务标准编写", write));
            }
            if ("招标业务".equals(type)) {
                contributionFactor.add(JsonUtil.createObjectNode().put("通过2.0系统完成的招标采办项目每完成一个标段", twozero));
            }
            if ("品类采购".equals(type)) {
                //加分因素
                ArrayNode jiafenyinsu = JsonUtil.createArrayNode();
                jiafenyinsu.add(JsonUtil.createObjectNode().put("书面表扬", shumian));
                jiafenyinsu.add(JsonUtil.createObjectNode().put("获得荣誉", honor));
                ObjectNode jiafen = JsonUtil.createObjectNode();
                jiafen.set("加分因素", jiafenyinsu);
                //扣分因素
                ArrayNode koufenyinsu = JsonUtil.createArrayNode();
                koufenyinsu.add(JsonUtil.createObjectNode().put("受到批评", piping));
                koufenyinsu.add(JsonUtil.createObjectNode().put("需求单位书面投诉", shumiantousu));
                ObjectNode koufen = JsonUtil.createObjectNode();
                koufen.set("扣分因素", koufenyinsu);
                contributionFactor.add(jiafen);
                contributionFactor.add(koufen);
            }
            ObjectNode objectNode3 = JsonUtil.createObjectNode();
            if ("品类采购".equals(type)) {
                objectNode3.replace("额外贡献系数和惩罚", contributionFactor);
            } else {
                objectNode3.replace("额外贡献系数", contributionFactor);
            }
            objectNodeGeren.add(objectNode3);
        }
        if ("品类采购".equals(type)) {
            int jihuawanchengdu = 0;
            int yinjinguochan = 0;
            int zhicheng = 0;
            int zhucezige = 0;
            int lvse = 0;
            int caigoubiaozhun = 0;
            int youxuan = 0;//优选供应商名录工作
            int lianhe = 0;

            if (list != null) {
                for (AchiContribution achiContribution : list) {
                    if (achiContribution.getWorkPlanCompletion().contains("完成")) {
                        jihuawanchengdu = jihuawanchengdu + 10;
                    }
                    yinjinguochan = achiContribution.getIntroduceDomesticSum() * 50;
                    //当年时间
                    Date thisTime = new Date(achiContribution.getProfessionalTitleTime());
                    if (achiContribution.getConYear().contains(sdf.format(thisTime))) {
                        switch (achiContribution.getProfessionalCategory()) {
                            case "中级":
                                zhicheng = zhicheng + 20;
                                break;
                            case "副高":
                                zhicheng = zhicheng + 50;
                                break;
                            case "正高":
                                zhicheng = zhicheng + 100;
                                break;
                            default:
                                break;
                        }
                    } else {
                        switch (achiContribution.getProfessionalCategory()) {
                            case "中级":
                                zhicheng = zhicheng + 20 / 2;
                                break;
                            case "副高":
                                zhicheng = zhicheng + 50 / 2;
                                break;
                            case "正高":
                                zhicheng = zhicheng + 100 / 2;
                                break;
                            default:
                                break;
                        }
                    }
                    //计算注册类资格
                    //当年时间
                    Date Time = new Date(achiContribution.getObtainRegistrationQualificationTime());
                    if (achiContribution.getConYear().contains(sdf.format(Time))) {
                        zhucezige = zhucezige + 60;
                    } else {
                        zhucezige = zhucezige + 30;
                    }
                    //绿色低碳
                    if (achiContribution.getGreenLowCarbonTimes() != null) {
                        lvse = achiContribution.getGreenLowCarbonTimes() * 50;
                    }
                    //采购标准分数
                    if (achiContribution.getPreparationStandardsFirst() != 0) {
                        caigoubiaozhun += 60 * 2 * achiContribution.getPreparationStandardsFirst();
                    }
                    if (achiContribution.getPreparationStandardsZs() != 0) {
                        caigoubiaozhun += 60 * 1.5 * achiContribution.getPreparationStandardsZs();
                    }
                    if (achiContribution.getPreparationStandardsOther() != 0) {
                        caigoubiaozhun += 60 * 0.5 * achiContribution.getPreparationStandardsOther();
                    }
                    //优选供应商名录
                    if (achiContribution.getPreferredSupplierBx() != 0) {
                        youxuan += 60 * achiContribution.getPreferredSupplierBx();
                    }
                    if (achiContribution.getPreferredSupplierCy() != 0) {
                        youxuan += 60 * achiContribution.getPreferredSupplierCy();
                    }
                    //联合采购
                    if (achiContribution.getJointProcurement() != 0) {
                        lianhe += 5 * achiContribution.getJointProcurement();
                    }
                }
            }
            //工作质量系数
            objectNodeGeren.add((JsonUtil.createObjectNode().put("工作计划的完成度", jihuawanchengdu)));
            //其他
            ArrayNode zhiliangqita = JsonUtil.createArrayNode();
            zhiliangqita.add(JsonUtil.createObjectNode().put("引进国产替代的", yinjinguochan));
            zhiliangqita.add(JsonUtil.createObjectNode().put("绿色低碳、三新三化等方面取得进展的", lvse));
            ObjectNode jsonNodes = JsonUtil.createObjectNode();
            jsonNodes.set("工作质量系数其他", zhiliangqita);
            objectNodeGeren.add(jsonNodes);
            //工作量系数（其他）
            ArrayNode gongzuoliang = JsonUtil.createArrayNode();
            gongzuoliang.add(JsonUtil.createObjectNode().put("采购标准", caigoubiaozhun));
            gongzuoliang.add(JsonUtil.createObjectNode().put("优选供应商名录工作", youxuan));
            gongzuoliang.add(JsonUtil.createObjectNode().put("联合采购", lianhe));
            ObjectNode gongzuo = JsonUtil.createObjectNode();
            gongzuo.set("工作量系数其他", gongzuoliang);
            objectNodeGeren.add(gongzuo);

            //能力提升（职业资格）
            ObjectNode zhiye = JsonUtil.createObjectNode();
            ArrayNode zhiyezige = JsonUtil.createArrayNode();
            zhiyezige.add(JsonUtil.createObjectNode().put("取得职称加分", zhicheng));
            zhiyezige.add(JsonUtil.createObjectNode().put("取得注册类资格加分", zhucezige));
            ObjectNode objectNode = JsonUtil.createObjectNode();
            objectNode.set("职业资格", zhiyezige);
            zhiye.replace("职业", objectNode);
            objectNodeGeren.add(zhiye);
        }
        return objectNodeGeren;
    }

    /**
     * 品类采购考核分计算
     *
     * @param jbrCode   投标人Code（经办人唯一标识）
     * @param startTime 考核开始时间
     * @param endTime   考核结束时间
     * @return
     */
    public String calcPinleicaigouSocre(String jbrCode, Date startTime, Date endTime) {
        startTime = DateUtil.parse(com.coredata.utils.date.DateUtil.df.format(startTime));
        endTime = DateUtil.parse(com.coredata.utils.date.DateUtil.df.format(endTime));
        ArrayNode arrayNodeAll = JsonUtil.createArrayNode();
        // ********************基础考核信息********************
        ArrayNode baseArrayAll = JsonUtil.createArrayNode();
        // 难度系数
        double difficultyFactor = 0;
        // 工作量系数
        ArrayNode workloadFactorArray = JsonUtil.createArrayNode();
        ObjectNode obj0 = JsonUtil.createObjectNode();
        int menuScore = achiPlService.calcMenuScore(jbrCode, startTime, endTime);
        obj0.put("通过研究推荐集采目录", menuScore);
        workloadFactorArray.add(obj0);
        // （一）品类协议化集采可行性研究
        ArrayNode array1 = JsonUtil.createArrayNode();
        ObjectNode node1 = JsonUtil.createObjectNode();
        // 当年进行集采协议化可行性分析研究
        int kxxScore = achiPlService.calcKxxScore(jbrCode, startTime, endTime);
        difficultyFactor += kxxScore;
        node1.put("当年进行品类协议化可行性分析研究", kxxScore);
        array1.add(node1);
        ObjectNode obj1 = JsonUtil.createObjectNode();
        obj1.replace("（一）品类协议化集采可行性研究", array1);
        workloadFactorArray.add(obj1);

        // （二）可协议化且当年形成框架协议的品类
        ArrayNode array2 = JsonUtil.createArrayNode();
        // 当年完成品类采购工作方案编制和审查
        ObjectNode node21 = JsonUtil.createObjectNode();
        double jcxyScore = achiPlService.calcJcxyScore(jbrCode, startTime, endTime);
        difficultyFactor += jcxyScore;
        node21.put("当年完成品类采购工作方案编制和审查", jcxyScore);
        array2.add(node21);
        // 采购策略系数
        ObjectNode node211 = JsonUtil.createObjectNode();
        double jcxyScore1 = achiPlService.calcStrategyScore(jbrCode, startTime, endTime);
        difficultyFactor += jcxyScore1;
        node211.put("采购策略系数", jcxyScore);
        array2.add(node211);
        // 当年完成招标文件编制并发标的
        ObjectNode node22 = JsonUtil.createObjectNode();
        double fbScore = achiPlService.calcFbScore(jbrCode, startTime, endTime);
        difficultyFactor += fbScore;
        node22.put("当年完成招标文件编制并发标的", fbScore);
        array2.add(node22);
        // 当年完成评标并授标的
        ObjectNode node23 = JsonUtil.createObjectNode();
        double pbScore = achiPlService.calcPbScore(jbrCode, startTime, endTime);
        difficultyFactor += pbScore;
        node23.put("当年完成评标并授标的", pbScore);
        array2.add(node23);
        // 当年完成框架协议谈判和签订
        ObjectNode node24 = JsonUtil.createObjectNode();
        double kjqdScore = achiPlService.calcKjqdScore(jbrCode, startTime, endTime);
        difficultyFactor += kjqdScore;
        node24.put("当年完成框架协议谈判和签订", kjqdScore);
        array2.add(node24);
        ObjectNode obj2 = JsonUtil.createObjectNode();
        obj2.replace("（二）可协议化且当年形成框架协议的品类", array2);
        workloadFactorArray.add(obj2);

        // (三)不适宜协议化的品类
        ArrayNode array3 = JsonUtil.createArrayNode();
        ObjectNode node3 = JsonUtil.createObjectNode();
        // 单项次采购
        int singlePurchaseScore = achiPlService.calcSinglePurchase(jbrCode, startTime, endTime);
        difficultyFactor += singlePurchaseScore;
        node3.put("单项次采购", singlePurchaseScore);
        array3.add(node3);
        ObjectNode obj3 = JsonUtil.createObjectNode();
        obj3.replace("(三)不适宜协议化的品类", array3);
        workloadFactorArray.add(obj3);

        // (四）可协议化且已经形成框架协议的
        ArrayNode array4 = JsonUtil.createArrayNode();
        // 查询可协议化且已经形成框架协议的数据
        LambdaQueryWrapper<AchiProcurement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AchiProcurement::getJbrCode, jbrCode)
                .like(AchiProcurement::getOrderType, "框架协议采购品类");
        Date finalStartTime = startTime;
        Date finalEndTime = endTime;
        List<AchiProcurement> list = achiPlService.list(wrapper);
        list = list.stream().filter(bean -> checkTime(bean.getQdDate(), finalStartTime, finalEndTime)).collect(Collectors.toList());
        //“+1”的框架协议，按采购方案完成协议执行回顾续签的
        ObjectNode node41 = JsonUtil.createObjectNode();
        int plus1Score = achiPlService.calcPlus1Score(list);
        difficultyFactor += plus1Score;
        node41.put("“+1”的框架协议，按采购方案完成协议执行回顾续签的", plus1Score);
        array4.add(node41);
        ObjectNode node45 = JsonUtil.createObjectNode();
        int youhua = achiPlService.youhuaScore(list);
        difficultyFactor += youhua;
        node41.put("协议优化", youhua);
        array4.add(node45);
        ObjectNode obj4 = JsonUtil.createObjectNode();
        obj4.replace("(四）可协议化且已经形成框架协议的", array4);
        workloadFactorArray.add(obj4);


        // 工作质量系数
        ArrayNode workingQualityFactorArray = JsonUtil.createArrayNode();
        // (一）节资
        ArrayNode array6 = JsonUtil.createArrayNode();
        // 节资框架协议
        ObjectNode node61 = JsonUtil.createObjectNode();
        int frameworkScore = achiPlService.calcFrameworkScore(jbrCode, startTime, endTime);
        node61.put("框架协议", frameworkScore);
        array6.add(node61);
        // 节资单项次
        ObjectNode node62 = JsonUtil.createObjectNode();
        double jieziSingleScore = achiPlService.calcJieziSingleScore(jbrCode, startTime, endTime);
        node62.put("单项次", jieziSingleScore);
        array6.add(node62);
        ObjectNode obj6 = JsonUtil.createObjectNode();
        obj6.replace("(一）节资", array6);
        workingQualityFactorArray.add(obj6);

        // (二）执行
        ArrayNode array7 = JsonUtil.createArrayNode();
        // 框架协议订单执行
        ObjectNode node71 = JsonUtil.createObjectNode();
        int execFrameworkScore = achiPlService.calcExecFrameworkScore(jbrCode, startTime, endTime);
        node71.put("框架协议订单执行", execFrameworkScore);
        array7.add(node71);
        // 单项次
        ObjectNode node72 = JsonUtil.createObjectNode();
        int execSingleScore = achiPlService.calcExecSingleScore(jbrCode, startTime, endTime);
        node72.put("单项次", execSingleScore);
        array7.add(node72);
        ObjectNode obj7 = JsonUtil.createObjectNode();
        obj7.replace("(二）执行", array7);
        workingQualityFactorArray.add(obj7);
        // 风险系数
        ArrayNode riskFactor = JsonUtil.createArrayNode();
        // 签订的框架协议存在瑕疵，导致与供应商纠纷的
        ObjectNode node91 = JsonUtil.createObjectNode();
        int riskJfScore = achiPlService.calcRiskJfScore(jbrCode, startTime, endTime);
        node91.put("签订的框架协议存在瑕疵，导致与供应商纠纷的", riskJfScore);
        riskFactor.add(node91);
        // 产生质量问题的
        ObjectNode node92 = JsonUtil.createObjectNode();
        int riskZlScore = achiPlService.calcRiskZlScore(jbrCode, startTime, endTime);
        node92.put("产生质量问题的", riskZlScore);
        riskFactor.add(node92);

        // 风险系数
        ObjectNode riskFactorObj = JsonUtil.createObjectNode();
        riskFactorObj.replace("风险系数", riskFactor);
        baseArrayAll.add(riskFactorObj);

        // ********************个人绩效贡献********************
        LambdaQueryWrapper<AchiContribution> queryWrapperContribution = new LambdaQueryWrapper<>();
        queryWrapperContribution.eq(AchiContribution::getWorkId, jbrCode)
                .like(AchiContribution::getTypeName, "品类采购").like(AchiContribution::getConYear, sdf.format(startTime)
                ).eq(AchiContribution::getStatus, 1);
        List<AchiContribution> achiContributionList = achiContributionMapper.selectList(queryWrapperContribution);
        ArrayNode gerenArrayAll = personContributionScore(achiContributionList, "品类采购", difficultyFactor, null);
        // 工作质量系数
        workingQualityFactorArray.add(gerenArrayAll.get(3));
        workingQualityFactorArray.add(gerenArrayAll.get(2));
        ObjectNode objectNode = JsonUtil.createObjectNode();
        objectNode.replace("工作质量系数", workingQualityFactorArray);
        baseArrayAll.add(objectNode);
        JsonNode tisehng1 = gerenArrayAll.get(5);
        JsonNode zhiye = tisehng1.get("职业");
        //能力提升
        ArrayNode tisheng = JsonUtil.createArrayNode();
        tisheng.add(zhiye);
        ObjectNode nenglitis = JsonUtil.createObjectNode();
        nenglitis.replace("能力提升", tisheng);
        baseArrayAll.add(nenglitis);
        // 工作量系数
        ObjectNode workloadFactorObj = JsonUtil.createObjectNode();
        workloadFactorArray.add(gerenArrayAll.get(4));
        workloadFactorObj.replace("工作量系数", workloadFactorArray);
        baseArrayAll.add(workloadFactorObj);
        gerenArrayAll.remove(2);
        gerenArrayAll.remove(2);
        gerenArrayAll.remove(2);
        gerenArrayAll.remove(2);
        ObjectNode baseObj = JsonUtil.createObjectNode();
        baseObj.set("基础考核信息", baseArrayAll);
        arrayNodeAll.add(baseObj);
        ObjectNode gerenObj = JsonUtil.createObjectNode();
        gerenObj.set("个人绩效贡献", gerenArrayAll);
        arrayNodeAll.add(gerenObj);
        return JsonUtil.writeValueAsString(arrayNodeAll);
    }

    /**
     * 外部市场考核分计算
     *
     * @param jbrCode
     * @param startTime
     * @param endTime
     * @return
     */
    public String calcWaibushichangSocre(String jbrCode, Date startTime, Date endTime) {
        List<Map<String, Object>> jsonArray = new ArrayList<>();
        startTime = DateUtil.parse(com.coredata.utils.date.DateUtil.df.format(startTime));
        endTime = DateUtil.parse(com.coredata.utils.date.DateUtil.df.format(endTime));
        try {
            Date finalStartTime = startTime;
            Date finalEndTime = endTime;
            List<AchiWb> achiWbXiangMus = achiWbMapper.selectList(Wrappers.<AchiWb>lambdaQuery()
                    .eq(AchiWb::getStatus, 1)
                    .eq(AchiWb::getJbrCode, jbrCode));
            //根据项目跟踪时间
            //根据招标时间
            List<AchiWb> zhaobiaoshijian = achiWbXiangMus.stream().filter
                            (bean -> bean.getBiddingTime() != null && checkTime(bean.getBiddingTime(), finalStartTime, finalEndTime))
                    .collect(Collectors.toList());
            //  根据受理时间
            List<AchiWb> achiWbs = achiWbXiangMus.stream().filter
                            (bean -> bean.getAcceptanceTime() != null && checkTime(bean.getAcceptanceTime(), finalStartTime, finalEndTime))
                    .collect(Collectors.toList());
            //代理合同
            List<AchiWb> dailihetong = achiWbMapper.selectList(Wrappers.<AchiWb>lambdaQuery()
                    .eq(AchiWb::getStatus, 1)
                    .eq(AchiWb::getLevel, 1)
                    .eq(AchiWb::getJbrCode, jbrCode));
            dailihetong = dailihetong.stream().filter(bean -> bean.getDailihetong() != null && checkTime(bean.getDailihetong(), finalStartTime, finalEndTime)).collect(Collectors.toList());
            //根据项目创建时间
            List<AchiContribution> achiContributionList = achiContributionMapper.selectList(Wrappers.<AchiContribution>lambdaQuery()
                    .eq(AchiContribution::getWorkId, jbrCode)
                    .like(AchiContribution::getConYear, sdf.format(startTime))
                    .like(AchiContribution::getTypeName, "外部市场")
                    .eq(AchiContribution::getStatus, 1));

            //为了后面计算部分标段，项目的系数，增加一个缓存，标段编号-项目编号，score   项目编号，score
            Map<String, Double> allScoreDict = new HashMap<>();
            List<Object> baseMapArray = new ArrayList<>();
            Map<String, Object> jsonMap1 = new HashMap<>();
            jsonMap1.put("基础考核信息", baseMapArray);
            jsonArray.add(jsonMap1);
            Map<String, Object> baseMap1 = new HashMap<>();
            List<Map<String, List<Map<String, Double>>>> nanDuBaseArray = new ArrayList<>();
            baseMap1.put("难度系数", nanDuBaseArray);
            baseMapArray.add(baseMap1);
            List<Object> xiaoYiBaseArray = new ArrayList<>();
            Map<String, Object> baseMap2 = new HashMap<>();
            baseMap2.put("效益系数", xiaoYiBaseArray);
            baseMapArray.add(baseMap2);
            List<Object> fengxianArray = new ArrayList<>();
            Map<String, Object> baseMap3 = new HashMap<>();
            baseMap3.put("风险系数", fengxianArray);
            baseMapArray.add(baseMap3);
            List<Object> xiaolvArray = new ArrayList<>();
            Map<String, Object> baseMap4 = new HashMap<>();
            baseMap4.put("效率系数", xiaolvArray);
            baseMapArray.add(baseMap4);
            List<Map<String, Object>> personMapArray = new ArrayList<>();
            Map<String, Object> personMap = new HashMap<>();
            personMapArray.add(personMap);
            Map<String, Object> jsonMap2 = new HashMap<>();
            jsonMap2.put("个人绩效贡献", personMapArray);
            jsonArray.add(jsonMap2);
            List<Object> gongpingMapArray = new ArrayList<>();
            Map<String, Object> gongpingMap = new HashMap<>();
            gongpingMapArray.add(gongpingMap);
            List<Object> ewaiMapArray = new ArrayList<>();
            Map<String, Object> ewaiMap = new HashMap<>();
            ewaiMapArray.add(ewaiMap);
            personMap.put("公平系数", gongpingMapArray);
            Map<String, Object> personMap2 = new HashMap<>();
            personMapArray.add(personMap2);
            personMap2.put("额外贡献系数", ewaiMapArray);

            Map<String, List<AchiWb>> dailihetong1 = dailihetong.stream().collect(Collectors.groupingBy(AchiWb::getProgectno));
            Map<String, List<AchiWb>> groupByProjectName = achiWbs.stream().collect(Collectors.groupingBy(AchiWb::getProgectno));
            Map<String, List<AchiWb>> achiWbXiangMu = achiWbXiangMus.stream().collect(Collectors.groupingBy(AchiWb::getProgectno));
            Map<String, List<AchiWb>> zhaobiao = zhaobiaoshijian.stream().collect(Collectors.groupingBy(AchiWb::getProgectno));
            Map<String, List<Map<String, Double>>> nanDuBaseMap1 = new HashMap<>();
            nanDuBaseMap1.put("项目争取", zhengquScoreSupport.work(dailihetong1, allScoreDict, startTime, endTime));
            nanDuBaseArray.add(nanDuBaseMap1);
            Map<String, List<Map<String, Double>>> nanDuBaseMap2 = new HashMap<>();
            nanDuBaseMap2.put("项目全过程", quanguochengScoreSupport.work(groupByProjectName, allScoreDict, startTime, endTime));
            nanDuBaseArray.add(nanDuBaseMap2);
            Map<String, List<Map<String, Double>>> nanDuBaseMap3 = new HashMap<>();
            nanDuBaseMap3.put("招标前期准备", zhunbeiScoreSupport.work(achiWbXiangMu, allScoreDict, startTime, endTime));
            nanDuBaseArray.add(nanDuBaseMap3);
            Map<String, List<Map<String, Double>>> nanDuBaseMap4 = new HashMap<>();
            nanDuBaseMap4.put("招标方式", fangshiScoreSupport.work(zhaobiao, allScoreDict, startTime, endTime));
            nanDuBaseArray.add(nanDuBaseMap4);
            Map<String, List<Map<String, Double>>> nanDuBaseMap5 = new HashMap<>();
            nanDuBaseMap5.put("招标负荷", fuheScoreSupport.work(achiWbXiangMu, allScoreDict, startTime, endTime));
            nanDuBaseArray.add(nanDuBaseMap5);
            Map<String, List<Map<String, Double>>> nanDuBaseMap6 = new HashMap<>();
            nanDuBaseMap6.put("合同执行", hetongScoreSupport.work(achiWbXiangMu, allScoreDict, startTime, endTime));
            nanDuBaseArray.add(nanDuBaseMap6);

            double nanduScore = 0;
            for (Map<String, List<Map<String, Double>>> nanDuBaseMap : nanDuBaseArray) {
                for (String nanKey : nanDuBaseMap.keySet()) {
                    List<Map<String, Double>> sonNanArray = nanDuBaseMap.get(nanKey);
                    for (Map<String, Double> sonNanMap : sonNanArray) {
                        for (String sonNanKey : sonNanMap.keySet()) {
                            Double aDouble = sonNanMap.get(sonNanKey);
                            nanduScore = nanduScore + aDouble;
                        }
                    }
                }
            }

            double nianduMoney = 0;
            double waibuMoney = 0;
            if (achiContributionList.size() > 0) {
                nianduMoney = achiContributionList.stream().mapToDouble(bean -> bean.getNianduMoney().doubleValue()).sum();
                waibuMoney = achiContributionList.stream().mapToDouble(bean -> bean.getWaibuMoney().doubleValue()).sum();
            }
            double d1 = 0, d2 = 0, d3 = 0, d4 = 0, d5 = 0, d6 = 0;
            if (nianduMoney < 0) {
                d1 = new BigDecimal(String.valueOf(nanduScore)).multiply(BigDecimal.valueOf(-0.5)).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            if (nianduMoney >= 0 && nianduMoney < 50) {
                d2 = new BigDecimal(String.valueOf(nanduScore)).multiply(BigDecimal.valueOf(-0.1)).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            if (nianduMoney >= 50 && nianduMoney < 100) {
                d3 = new BigDecimal(String.valueOf(nanduScore)).multiply(BigDecimal.valueOf(0)).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            if (nianduMoney > 100 && nianduMoney <= 200) {
                d4 = new BigDecimal(String.valueOf(nanduScore)).multiply(new BigDecimal("0.5")).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            if (nianduMoney > 200 && nianduMoney <= 300) {
                d5 = new BigDecimal(String.valueOf(nanduScore)).multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            if (nianduMoney > 300) {
                d6 = new BigDecimal(String.valueOf(nanduScore)).multiply(new BigDecimal(2)).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }


            Map<String, Object> xiaoYiBaseMap1 = new HashMap<>();

            List<Map<String, Double>> lirunArray = new ArrayList<>();
            Map<String, Double> lirunMap = new HashMap<>();
            lirunMap.put("年度人均净利润亏损", d1);
            lirunMap.put("年度人均净利润不足50万元", d2);
            lirunMap.put("年度人均净利润在50万到100万元之间", d3);
            lirunMap.put("年度人均净利润超过100万元", d4);
            lirunMap.put("年度人均净利润超过200万元", d5);
            lirunMap.put("年度人均净利润超过300万元", d6);
            lirunArray.add(lirunMap);
            xiaoYiBaseMap1.put("利润完成指标", lirunArray);
            xiaoYiBaseArray.add(xiaoYiBaseMap1);

            List<Map<String, Double>> waibuArray = new ArrayList<>();
            Map<String, Double> waibuMap = new HashMap<>();

            double w2 = 0, w3 = 0;
            if (waibuMoney >= 500 && waibuMoney <= 1000) {
                w2 = new BigDecimal(String.valueOf(nanduScore)).multiply(new BigDecimal("0.5")).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            if (nianduMoney > 1000) {
                w3 = new BigDecimal(String.valueOf(nanduScore)).multiply(new BigDecimal(1)).setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            Map<String, Object> xiaoYiBaseMap2 = new HashMap<>();
            waibuMap.put("外部市场总收入不足500万元", 0D);
            waibuMap.put("外部市场总收入500-1000万元", w2);
            waibuMap.put("外部市场总收入1000万元以上", w3);
            waibuArray.add(waibuMap);
            xiaoYiBaseMap2.put("经营规模指标", waibuArray);
            xiaoYiBaseArray.add(xiaoYiBaseMap2);

            //02效率系数
            //服务满意度
            Map<String, Object> xiaolvMap = new HashMap<>();
            xiaolvArray.add(xiaolvMap);
            Map<String, Double> allScoreXiaolvDict = new HashMap<>();
            xiaolvMap.put("服务满意度", xiaolvScoreSupport.work(achiWbXiangMu, allScoreXiaolvDict, startTime, endTime));

            ArrayNode twoObj = personContributionScore(achiContributionList, "外部市场", nanduScore, 0D);
            List<Map<String, Object>> twoArray = JsonUtil.parseObject(JsonUtil.writeValueAsString(twoObj), new TypeReference<>() {
            });
            //04公平系数
            //员工经验
            for (Map<String, Object> twoItem : twoArray) {
                if (twoItem.containsKey("公平系数")) {
                    Object gongpingObj = twoItem.get("公平系数");
                    gongpingMap.put("员工经验", gongpingObj);
                }
            }

            int fengxianNum1 = 0;
            int fengxianNum2 = 0;
            int fengxianNum3 = 0;
            BigDecimal jilvBig = new BigDecimal(0);
            BigDecimal zishenBig = new BigDecimal(0);
            int isWeifa = 0;
            for (String projectName : groupByProjectName.keySet()) {
                List<AchiWb> singleWbArray = groupByProjectName.get(projectName);
                //如果有标段，开始操作
                if (singleWbArray.size() > 0) {
                    for (AchiWb achiWb : singleWbArray) {
                        fengxianNum1 = fengxianNum1 + achiWb.getZhiliang();
                        fengxianNum2 = fengxianNum2 + achiWb.getAnshituihuan();
                        fengxianNum3 = fengxianNum3 + achiWb.getJishiluru();
                        isWeifa = isWeifa + achiWb.getWeifaweiji();
                        if (achiWb.getJilv() > 0) {
                            double nanduTemp = 0, xiaolvTemp = 0;
                            if (allScoreDict.containsKey(projectName + "-" + achiWb.getBiaoduanuid())) {
                                nanduTemp = allScoreDict.get(projectName + "-" + achiWb.getBiaoduanuid());
                            }
                            if (allScoreXiaolvDict.containsKey(projectName + "-" + achiWb.getBiaoduanuid())) {
                                xiaolvTemp = allScoreXiaolvDict.get(projectName + "-" + achiWb.getBiaoduanuid());
                            }
                            jilvBig = jilvBig.add(new BigDecimal(String.valueOf(nanduTemp)).multiply(BigDecimal.valueOf(-0.5)).setScale(2, RoundingMode.HALF_UP)).add(BigDecimal.valueOf(xiaolvTemp).multiply(BigDecimal.valueOf(-0.5)).setScale(2, RoundingMode.HALF_UP));
                        }

                        if (achiWb.getZishen() > 0) {
                            double nanduTemp = 0, xiaolvTemp = 0;
                            if (allScoreDict.containsKey(projectName + "-" + achiWb.getBiaoduanuid())) {
                                nanduTemp = allScoreDict.get(projectName + "-" + achiWb.getBiaoduanuid());
                            }
                            if (allScoreXiaolvDict.containsKey(projectName + "-" + achiWb.getBiaoduanuid())) {
                                xiaolvTemp = allScoreXiaolvDict.get(projectName + "-" + achiWb.getBiaoduanuid());
                            }
                            zishenBig = zishenBig.add(new BigDecimal(String.valueOf(nanduTemp)).multiply(BigDecimal.valueOf(-0.5)).setScale(2, RoundingMode.HALF_UP)).add(BigDecimal.valueOf(xiaolvTemp).multiply(BigDecimal.valueOf(-0.5)).setScale(2, RoundingMode.HALF_UP));
                        }
                    }
                }
            }

            //05风险系数
            //公司纪律（需要确定内部是否有记录的系统）

            Map<String, Object> fengxianMap = new HashMap<>();
            fengxianArray.add(fengxianMap);
            List<Object> fengxianSonArray = new ArrayList<>();
            Map<String, Double> fengxianSonMap = new HashMap<>();
            fengxianSonArray.add(fengxianSonMap);
            fengxianMap.put("公司纪律", fengxianSonArray);
            //违法、违纪
            fengxianSonMap.put("违法、违纪", 0D);
            //成立的异议或投诉（按标）：由于招标经理自身原因形成有效异议或投诉
            fengxianSonMap.put("成立的异议或投诉（按标）", zishenBig.doubleValue());
            //评标纪律：出现评标纪律问题
            fengxianSonMap.put("评标纪律", jilvBig.doubleValue());
            fengxianSonMap.put("未及时退还保证金（按标）", new BigDecimal(fengxianNum2).multiply(new BigDecimal(-2)).setScale(2, RoundingMode.HALF_UP).doubleValue());
            fengxianSonMap.put("招标质量", (double) fengxianNum1);
            //未及时按规定在外部系统录入信息（按标）
            fengxianSonMap.put("未及时按规定在外部系统录入信息（按标）", new BigDecimal(fengxianNum3).multiply(new BigDecimal(-3)).setScale(2, RoundingMode.HALF_UP).doubleValue());

            //06额外贡献
            //公司安排的其他工作

            for (Map<String, Object> twoItem : twoArray) {
                if (twoItem.containsKey("额外贡献系数")) {
                    Object xishu = twoItem.get("额外贡献系数");
                    ewaiMap.put("公司安排的其他工作", xishu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JsonUtil.writeValueAsString(jsonArray);
    }

    public boolean checkTime(Date date, Date startDate, Date endDate) {
        if (date != null && date.compareTo(startDate) != -1 && date.compareTo(endDate) != 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBidMethod(String biddingMethod) {
        if (biddingMethod != null && (biddingMethod.contains("国内公开资格预审") || biddingMethod.contains("国内公开招标") || biddingMethod.contains("国际公开资格预审") || biddingMethod.contains("国际公开招标") || biddingMethod.contains("国际邀请招标") || biddingMethod.contains("国际竞谈"))
        ) {
            return true;
        }
        return false;
    }
}


