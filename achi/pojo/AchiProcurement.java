package com.deta.achi.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.deta.achi.dto.PageAssistantBean;
import com.deta.achi.pojo.handler.DateTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @TableName achi_pl_new
 * @email diaozhiwei2k@163.com
 */
@TableName(value = "achi_procurement")
public class AchiProcurement implements Serializable {
    /**
     *
     */
    @TableId(value = "id")
    private String id;

    /**
     * 标段id
     */
    @TableField(value = "biaoduanuid")
    private String biaoduanuid;

    /**
     * 标段名称
     */
    @TableField(value = "biaoduanname")
    private String biaoduanname;

    /**
     * 项目编号
     */
    @TableField(value = "projectno")
    private String projectno;

    /**
     * 项目名称
     */
    @TableField(value = "projectname")
    private String projectname;

    /**
     * 所属单位
     */
    @TableField(value = "company")
    private String company;

    /**
     * 处室
     */
    @TableField(value = "department")
    private String department;

    /**
     * 大类编码
     */
    @TableField(value = "category_code")
    private String categoryCode;

    /**
     * 中类编码
     */
    @TableField(value = "middle_code")
    private String middleCode;

    /**
     * 小类编码
     */
    @TableField(value = "subcategory_code")
    private String subcategoryCode;

    /**
     * 大类名称
     */
    @TableField(value = "category_name")
    private String categoryName;

    /**
     * 中类名称
     */
    @TableField(value = "middle_name")
    private String middleName;

    /**
     * 小类名称
     */
    @TableField(value = "subca_name")
    private String subcaName;


    /**
     * 进行品类协议化可行性分析研究的完成时间
     */
    @TableField(value = "kxx_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kxxDate;

    /**
     * 完成品类采购工作方案编制时间
     */
    @TableField(value = "bz_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bzDate;

    /**
     * 完成品类采购工作方案审查时间
     */
    @TableField(value = "examin_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date examinDate;

    /**
     * 是否首次签定完成品类采购工作方案
     */
    @TableField(value = "first_programme")
    private String firstProgramme;

    /**
     * 完成招标文件编制时间
     */
    @TableField(value = "bidding_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date biddingDate;

    /**
     * 完成发标时间
     */
    @TableField(value = "fb_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fbDate;

    /**
     * 是否首次签订完成招标文件编制和发标
     */
    @TableField(value = "first_issuance")
    private String firstIssuance;

    /**
     * 完成评标时间
     */
    @TableField(value = "pb_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pbDate;

    /**
     * 完成授标时间
     */
    @TableField(value = "wsb_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date wsbDate;

    /**
     * 是否首次签订完成评标并授标
     */
    @TableField(value = "first_award")
    private String firstAward;

    /**
     * 是否设备类
     */
    @TableField(value = "is_sbl")
    private String isSbl;

    /**
     * 评标方式
     */
    @TableField(value = "pingbiaofs")
    private String pingbiaofs;

    /**
     * 数据所属时间
     */
    @TableField(value = "suoshu_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date suoshuDate;

    /**
     * 是否跨单位
     */
    @TableField(value = "is_kdw")
    private String isKdw;

    /**
     * +1”的框架协议，按采购方案完成协议执行回顾续签的
     */
    @TableField(value = "xuqian")
    private String xuqian;

    /**
     * 是否协议优化
     */
    @TableField(value = "pro_opti")
    private String proOpti;

    /**
     * 完成框架协议签订时间
     */
    @TableField(value = "kjqd_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date kjqdDate;

    /**
     * 签订时间
     */
    @TableField(value = "qd_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qdDate;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", typeHandler = DateTypeHandler.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 采购类别
     */
    @TableField(value = "purchase_category")
    private String purchaseCategory;

    /**
     * 受理时间
     */
    @TableField(value = "acceptance_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date acceptanceDate;

    /**
     * 合同编号
     */
    @TableField(value = "ht_no")
    private String htNo;

    /**
     * 合同名称
     */
    @TableField(value = "ht_name")
    private String htName;

    /**
     * 中标金额（人民币）
     */
    @TableField(value = "zb_money")
    private double zbMoney;

    /**
     * "预算金额
     * （人民币）"
     */
    @TableField(value = "ys_money")
    private double ysMoney;

    /**
     * 框架协议编号
     */
    @TableField(value = "xieyi_no")
    private String xieyiNo;

    /**
     * 采购时间
     */
    @TableField(value = "purchase_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date purchaseDate;

    /**
     * 材料类物资价格指数
     */
    @TableField(value = "materials_price_index")
    private String materialsPriceIndex;

    /**
     * 协议价格
     */
    @TableField(value = "agreement_price")
    private double agreementPrice;

    /**
     * 协议主要标的品类
     */
    @TableField(value = "agreement_main_category")
    private String agreementMainCategory;

    /**
     * 原协议价格
     */
    @TableField(value = "origin_agreement_price")
    private double originAgreementPrice;

    /**
     * 前一年招标采购价格平均数
     */
    @TableField(value = "bid_procure_average_price_last_year")
    private int bidProcureAveragePriceLastYear;

    /**
     * 节资率
     */
    @TableField(value = "jzl")
    private String jzl;

    /**
     * 框架协议名称
     */
    @TableField(value = "xieyi_name")
    private String xieyiName;

    /**
     * 订单类型
     */
    @TableField(value = "order_type")
    private String orderType;

    /**
     * 签订协议是否有瑕疵与供应商纠纷次数
     */
    @TableField(value = "jf_num")
    private int jfNum;

    /**
     * 产生质量问题次数
     */
    @TableField(value = "zl_num")
    private int zlNum;

    /**
     * 1.可协议化且当年形成框架协议的品类2.不适宜协议化的品类3.可协议化且已经形成框架协议的品类4.其他
     */
    @TableField(value = "type")
    private int type;

    /**
     * 1有效0无效
     */
    @TableField(value = "status")
    private int status;

    /**
     * 投标人账号
     */
    @TableField(value = "jbr_code")
    private String jbrCode;

    /**
     * 投标人name
     */
    @TableField(value = "jbr_name")
    private String jbrName;

    /**
     * 研究性推荐集采目录-通过业务部审核时间
     */
    @TableField(value = "business_approval_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date businessApprovalTime;

    /**
     * 研究性推荐集采目录-通过中心审核时间
     */
    @TableField(value = "central_approval_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date centralApprovalTime;

    /**
     * 研究性推荐集采目录-通过最终通过并发布时间
     */
    @TableField(value = "final_approval_and_release_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finalApprovalAndReleaseTime;

    /**
     * 协调订单分配现场催交货次数
     */
    @TableField(value = "coordinate_num")
    private int coordinateNum;

    /**
     * 协调保供项目数
     */
    @TableField(value = "csg_num")
    private int csgNum;

    /**
     * 是否产生重大项目保产保供问题
     */
    @TableField(value = "is_ensure_product_supply_problem")
    private String isEnsureProductSupplyProblem;

    /**
     * 是否首次签订框架协议
     */
    @TableField(value = "is_first_time_framework")
    private String isFirstTimeFramework;

    /**
     * 招标方式
     */
    @TableField(value = "purchase_way")
    private String purchaseWay;
    /**
     * 招标单价
     */
    @TableField(value = "unit_price")
    private BigDecimal unitPrice;

    @TableField(exist = false)
    private static final long serialVersionUID = 136571518253736373L;
    /**
     * 页数
     */
    @TableField(exist = false)
    private Integer pageNum;
    /**
     * 数据量
     */
    @TableField(exist = false)
    private Integer pageSize;
    /**
     * 排序
     */
    @TableField(exist = false)
    private String sort;
    /**
     * 名称
     */
    @TableField(exist = false)
    private String nameOrId;
    /**
     * 协议名称
     */
    @TableField(exist = false)
    private String xmNameOrId;
    /**
     * 开始时间
     */
    @TableField(exist = false)
    private Long startTime;
    /**
     * 结束时间
     */
    @TableField(exist = false)
    private Long stopTime;
    @TableField(exist = false)
    private List<AchiProcurement> achiPlList;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getNameOrId() {
        return nameOrId;
    }

    public void setNameOrId(String nameOrId) {
        this.nameOrId = nameOrId;
    }

    public String getXmNameOrId() {
        return xmNameOrId;
    }

    public void setXmNameOrId(String xmNameOrId) {
        this.xmNameOrId = xmNameOrId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBiaoduanuid() {
        return biaoduanuid;
    }

    public void setBiaoduanuid(String biaoduanuid) {
        this.biaoduanuid = biaoduanuid;
    }

    public String getBiaoduanname() {
        return biaoduanname;
    }

    public void setBiaoduanname(String biaoduanname) {
        this.biaoduanname = biaoduanname;
    }

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getMiddleCode() {
        return middleCode;
    }

    public void setMiddleCode(String middleCode) {
        this.middleCode = middleCode;
    }

    public String getSubcategoryCode() {
        return subcategoryCode;
    }

    public void setSubcategoryCode(String subcategoryCode) {
        this.subcategoryCode = subcategoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSubcaName() {
        return subcaName;
    }

    public void setSubcaName(String subcaName) {
        this.subcaName = subcaName;
    }


    public Date getKxxDate() {
        return kxxDate;
    }

    public void setKxxDate(Date kxxDate) {
        this.kxxDate = kxxDate;
    }

    public Date getBzDate() {
        return bzDate;
    }

    public void setBzDate(Date bzDate) {
        this.bzDate = bzDate;
    }

    public Date getExaminDate() {
        return examinDate;
    }

    public void setExaminDate(Date examinDate) {
        this.examinDate = examinDate;
    }

    public String getFirstProgramme() {
        return firstProgramme;
    }

    public void setFirstProgramme(String firstProgramme) {
        this.firstProgramme = firstProgramme;
    }

    public Date getBiddingDate() {
        return biddingDate;
    }

    public void setBiddingDate(Date biddingDate) {
        this.biddingDate = biddingDate;
    }

    public Date getFbDate() {
        return fbDate;
    }

    public void setFbDate(Date fbDate) {
        this.fbDate = fbDate;
    }

    public String getFirstIssuance() {
        return firstIssuance;
    }

    public void setFirstIssuance(String firstIssuance) {
        this.firstIssuance = firstIssuance;
    }

    public Date getPbDate() {
        return pbDate;
    }

    public void setPbDate(Date pbDate) {
        this.pbDate = pbDate;
    }

    public Date getWsbDate() {
        return wsbDate;
    }

    public void setWsbDate(Date wsbDate) {
        this.wsbDate = wsbDate;
    }

    public String getFirstAward() {
        return firstAward;
    }

    public void setFirstAward(String firstAward) {
        this.firstAward = firstAward;
    }

    public String getIsSbl() {
        return isSbl;
    }

    public void setIsSbl(String isSbl) {
        this.isSbl = isSbl;
    }


    public String getPingbiaofs() {
        return pingbiaofs;
    }

    public void setPingbiaofs(String pingbiaofs) {
        this.pingbiaofs = pingbiaofs;
    }

    public Date getSuoshuDate() {
        return suoshuDate;
    }

    public void setSuoshuDate(Date suoshuDate) {
        this.suoshuDate = suoshuDate;
    }


    public String getIsKdw() {
        return isKdw;
    }

    public void setIsKdw(String isKdw) {
        this.isKdw = isKdw;
    }

    public String getXuqian() {
        return xuqian;
    }

    public void setXuqian(String xuqian) {
        this.xuqian = xuqian;
    }

    public String getProOpti() {
        return proOpti;
    }

    public void setProOpti(String proOpti) {
        this.proOpti = proOpti;
    }

    public Date getKjqdDate() {
        return kjqdDate;
    }

    public void setKjqdDate(Date kjqdDate) {
        this.kjqdDate = kjqdDate;
    }

    public Date getQdDate() {
        return qdDate;
    }

    public void setQdDate(Date qdDate) {
        this.qdDate = qdDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(String purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }


    public String getHtNo() {
        return htNo;
    }

    public void setHtNo(String htNo) {
        this.htNo = htNo;
    }

    public String getHtName() {
        return htName;
    }

    public void setHtName(String htName) {
        this.htName = htName;
    }

    public double getZbMoney() {
        return zbMoney;
    }

    public void setZbMoney(double zbMoney) {
        this.zbMoney = zbMoney;
    }

    public double getYsMoney() {
        return ysMoney;
    }

    public void setYsMoney(double ysMoney) {
        this.ysMoney = ysMoney;
    }

    public String getXieyiNo() {
        return xieyiNo;
    }

    public void setXieyiNo(String xieyiNo) {
        this.xieyiNo = xieyiNo;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getMaterialsPriceIndex() {
        return materialsPriceIndex;
    }

    public void setMaterialsPriceIndex(String materialsPriceIndex) {
        this.materialsPriceIndex = materialsPriceIndex;
    }

    public double getAgreementPrice() {
        return agreementPrice;
    }

    public void setAgreementPrice(double agreementPrice) {
        this.agreementPrice = agreementPrice;
    }

    public String getAgreementMainCategory() {
        return agreementMainCategory;
    }

    public void setAgreementMainCategory(String agreementMainCategory) {
        this.agreementMainCategory = agreementMainCategory;
    }

    public double getOriginAgreementPrice() {
        return originAgreementPrice;
    }

    public void setOriginAgreementPrice(double originAgreementPrice) {
        this.originAgreementPrice = originAgreementPrice;
    }

    public int getBidProcureAveragePriceLastYear() {
        return bidProcureAveragePriceLastYear;
    }

    public void setBidProcureAveragePriceLastYear(int bidProcureAveragePriceLastYear) {
        this.bidProcureAveragePriceLastYear = bidProcureAveragePriceLastYear;
    }

    public String getJzl() {
        return jzl;
    }

    public void setJzl(String jzl) {
        this.jzl = jzl;
    }


    public String getXieyiName() {
        return xieyiName;
    }

    public void setXieyiName(String xieyiName) {
        this.xieyiName = xieyiName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    public int getJfNum() {
        return jfNum;
    }

    public void setJfNum(int jfNum) {
        this.jfNum = jfNum;
    }

    public int getZlNum() {
        return zlNum;
    }

    public void setZlNum(int zlNum) {
        this.zlNum = zlNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getJbrCode() {
        return jbrCode;
    }

    public void setJbrCode(String jbrCode) {
        this.jbrCode = jbrCode;
    }

    public String getJbrName() {
        return jbrName;
    }

    public void setJbrName(String jbrName) {
        this.jbrName = jbrName;
    }

    public Date getBusinessApprovalTime() {
        return businessApprovalTime;
    }

    public void setBusinessApprovalTime(Date businessApprovalTime) {
        this.businessApprovalTime = businessApprovalTime;
    }

    public Date getCentralApprovalTime() {
        return centralApprovalTime;
    }

    public void setCentralApprovalTime(Date centralApprovalTime) {
        this.centralApprovalTime = centralApprovalTime;
    }

    public Date getFinalApprovalAndReleaseTime() {
        return finalApprovalAndReleaseTime;
    }

    public void setFinalApprovalAndReleaseTime(Date finalApprovalAndReleaseTime) {
        this.finalApprovalAndReleaseTime = finalApprovalAndReleaseTime;
    }

    public int getCoordinateNum() {
        return coordinateNum;
    }

    public void setCoordinateNum(int coordinateNum) {
        this.coordinateNum = coordinateNum;
    }

    public int getCsgNum() {
        return csgNum;
    }

    public void setCsgNum(int csgNum) {
        this.csgNum = csgNum;
    }

    public String getIsEnsureProductSupplyProblem() {
        return isEnsureProductSupplyProblem;
    }

    public void setIsEnsureProductSupplyProblem(String isEnsureProductSupplyProblem) {
        this.isEnsureProductSupplyProblem = isEnsureProductSupplyProblem;
    }

    public List<AchiProcurement> getAchiPlList() {
        return achiPlList;
    }

    public void setAchiPlList(List<AchiProcurement> achiPlList) {
        this.achiPlList = achiPlList;
    }

    public String getIsFirstTimeFramework() {
        return isFirstTimeFramework;
    }

    public void setIsFirstTimeFramework(String isFirstTimeFramework) {
        this.isFirstTimeFramework = isFirstTimeFramework;
    }

    public String getPurchaseWay() {
        return purchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        this.purchaseWay = purchaseWay;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}