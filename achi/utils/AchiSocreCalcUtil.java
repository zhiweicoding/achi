package com.deta.achi.utils;

import cn.hutool.core.date.DateUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Scanner;

/**
 * @Author lijialin
 * @date 2022/8/10 8:47
 */
public class AchiSocreCalcUtil {

    private static DecimalFormat df = new DecimalFormat("######0.00");

    /**
     * 根据招标方式计算分数
     *
     * @param biddingMethod 招标方式
     * @return
     */
    public static double biddingMethodSocre(String biddingMethod) {
        double socre = 0;
        try {
            if (biddingMethod != null) {
                switch (biddingMethod) {
                    case "国内公开资格预审":
                        socre = 10;
                        break;
                    case "国内公开招标":
                        socre = 10;
                        break;
                    case "国内邀请招标":
                        socre = 7;
                        break;
                    case "国内竞谈":
                        socre = 7;
                        break;
                    case "国内公开询价":
                        socre = 7;
                        break;
                    case "国内单一来源":
                        socre = 5;
                        break;
                    case "国内邀请询价":
                        socre = 3;
                        break;
                    case "国际公开资格预审":
                        socre = 15;
                        break;
                    case "国际公开招标":
                        socre = 15;
                        break;
                    case "国际邀请招标":
                        socre = 15;
                        break;
                    case "国际竞谈":
                        socre = 15;
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }

    /**
     * 公开资格预审后直接进行下一步采办方式完成招标分数计算
     *
     * @param biddingMethod 招标方式
     * @return
     */
    public static double nextStepCompleteMethodType(String biddingMethod) {
        double socre = 0;
        try {
            if (biddingMethod.contains("国内公开资格预审") || biddingMethod.contains("国内公开招标") || biddingMethod.contains("国际公开资格预审") || biddingMethod.contains("国际公开招标") || biddingMethod.contains("国际邀请招标") || biddingMethod.contains("国际竞谈")) {
                socre = 5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }


    /**
     * 多标段分数计算
     *
     * @param bidCount 标段数量
     * @return
     */
    public static double manyBiddingSocre(Integer bidCount) {
        double socre = 0;
        try {
            if (bidCount <= 3) {
                socre = (bidCount - 1) * 5;
            } else {
                socre = 2 * 5 + (bidCount - 3) * 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }

    /**
     * 投标人数量分数计算
     *
     * @param bidder 投标人数量
     * @return
     */
    public static double bidderSocre(Integer bidder) {
        double socre = 0;
        try {
            if (bidder >= 11 && bidder <= 20) {
                socre = 5;
            } else {
                socre = 10;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }

    /**
     * 采用综合评分法进行评审分数计算
     *
     * @param bidNum
     * @return
     */
    public static double comprehensiveSocre(Integer bidNum) {
        double socre = 0;
        try {
            if (bidNum > 0) {
                socre = 5 + (bidNum - 1) * 2;
            }
            if (socre > 20) {
                socre = 20;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }

    /**
     * 采用线下纸质版评标分数计算
     *
     * @param paper
     * @return
     */
    public static double paperReviewSocre(String paper, String biddingMethod) {
        double socre = 0;
        try {
            if (paper != null && paper.contains("是") && biddingMethod.contains("国内公开资格预审") || biddingMethod.contains("国内公开招标") || biddingMethod.contains("国际公开资格预审") || biddingMethod.contains("国际公开招标") || biddingMethod.contains("国际邀请招标") || biddingMethod.contains("国际竞谈")) {
                socre = 5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }

    /**
     * 经批准的两步法招标的标段
     *
     * @param stepMethod
     * @return
     */
    public static double twoStepSocre(String stepMethod) {
        double socre = 0;
        try {
            if (stepMethod != null && stepMethod.contains("是")) {
                socre = 3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }

    /**
     * 开标后重新招标（按标段）分数计算
     *
     * @param biddingTotalSocre 标段总分数
     * @param biddingName       标段名称
     * @return
     */
    public static double reBiddingSocre(double biddingTotalSocre, String biddingName) {
        double socre = 0;
        try {
            if (!biddingName.contains("重新招标")) {
                socre = biddingTotalSocre;
            } else if (biddingName.contains("第一次重新招标")) {
                socre = (1 + 0.8) * biddingTotalSocre;
            } else if (biddingName.contains("第二次重新招标")) {
                socre = (1 + 0.8 + 0.6) * biddingTotalSocre;
            } else {
                socre = 2.9 * biddingTotalSocre;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        df.format(socre);
        return socre;
    }

    /**
     * 不足3家重新招标（按标段）分数计算
     *
     * @param exceptionContent 异常情况
     * @param dealResult       拟处理结果
     * @return
     */
    public static double noEnoughThreeBidderSocre(String exceptionContent, String dealResult) {
        double socre = 0;
        try {
            if ((exceptionContent != null && exceptionContent.contains("不足三家"))
                    || (dealResult != null && dealResult.contains("重新招标"))) {
                socre = 3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }

    /**
     * 缩短天数分数计算
     *
     * @param biddingType    招标方式
     * @param acceptanceTime 受理时间
     * @param sendTime       发送中标通知书时间
     * @return
     */
    public static double shortDaySocre(String biddingType, Date acceptanceTime, Date sendTime) {
        double socre = 0;
        try {
            int day = calcDay(sendTime, acceptanceTime);
            switch (biddingType) {
                case "国内公开招标":
                    if (day < 70) {
                        if (day == 69) {
                            socre = 0.1;
                        } else {
                            socre = 0.2;
                        }
                    } else if (day > 84) {
                        if (day == 85) {
                            socre = -0.1;
                        } else {
                            socre = -0.2;
                        }
                    }
                    break;
                case "国内公开资格预审":
                    if (day < 70) {
                        if (day == 69) {
                            socre = 0.1;
                        } else {
                            socre = 0.2;
                        }
                    } else if (day > 84) {
                        if (day == 85) {
                            socre = -0.1;
                        } else {
                            socre = -0.2;
                        }
                    }
                    break;
                case "国内邀请招标":
                    if (day < 63) {
                        if (day == 62) {
                            socre = 0.1;
                        } else {
                            socre = 0.2;
                        }
                    } else if (day > 79) {
                        if (day == 80) {
                            socre = -0.1;
                        } else {
                            socre = -0.2;
                        }
                    }
                    break;
                case "国内竞谈":
                    if (day < 51) {
                        if (day == 50) {
                            socre = 0.1;
                        } else {
                            socre = 0.2;
                        }
                    } else if (day > 85) {
                        if (day == 86) {
                            socre = -0.1;
                        } else {
                            socre = -0.2;
                        }
                    }
                    break;
                case "国际采购":
                    if (day < 77) {
                        if (day == 76) {
                            socre = 0.1;
                        } else {
                            socre = 0.2;
                        }
                    } else if (day > 95) {
                        if (day == 96) {
                            socre = -0.1;
                        } else {
                            socre = -0.2;
                        }
                    }
                    break;
                default:
                    break;
            }
            socre = (double) Math.round(socre * 100) / 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }

    /**
     * 计算两个日期相差天数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int calcDay(Date time1, Date time2) {
        long l = time1.getTime() - time2.getTime();
        int floor = ((int) Math.floor(l / (1000 * 60 * 60 * 24)));
        return floor;
    }

    /**
     * 节资率超过20%分数计算
     *
     * @param budgetAmount 预算金额（折算人民币）
     * @param bidAmount    中标金额（折算人民币）
     * @return
     */
    public static double saveResourcePercentSocre(long budgetAmount, long bidAmount) {
        double socre = 0;
        try {
            BigDecimal budgetAmountBd = new BigDecimal(budgetAmount);
            BigDecimal bidAmountBd = new BigDecimal(bidAmount);
            BigDecimal percent = (budgetAmountBd.subtract(bidAmountBd)).divide(budgetAmountBd, 2, BigDecimal.ROUND_HALF_UP);
            if (percent.doubleValue() > 0.2) {
                double superPercent = (percent.subtract(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()) * 10;
                int multiple = (int) superPercent;
                socre = multiple * 0.5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        df.format(socre);
        return socre;
    }

    /**
     * 必联网操作分数计算
     *
     * @param bidCount
     * @return
     */
    public static double isOnlineSocre(Integer bidCount) {
        double socre = 0;
        try {
            if (bidCount <= 1) {
                socre = 3;
            } else {
                socre = 3 + (bidCount - 1) * 2;
                if (socre > 15) {
                    socre = 15;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }

    /**
     * 风险系数分数计算
     *
     * @param riskType
     * @return
     */
    public static double riskSocre(String riskType, double itemBidSocre) {
        double socre = 0;
        try {
            if (riskType != null) {
                switch (riskType) {
                    case "自身原因导致的异议或投诉":
                        socre = -1 * itemBidSocre * 0.5;
                        break;
                    case "招标质量":
                        socre = -1;
                        break;
                    case "未按时退还保证金":
                        socre = -1;
                        break;
                    case "未按时收取中标服务费":
                        socre = -2;
                        break;
                    default:
                        break;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return socre;
    }

}
