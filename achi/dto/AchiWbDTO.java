package com.deta.achi.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @TableName achi_wb
 */
@TableName(value = "achi_external_market")
public class AchiWbDTO extends PageAssistantBean<AchiWbDTO> implements Serializable {
    /**
     *
     */
    @TableId(value = "id")
    private String id;

    /**
     * 标段编号
     */
    @TableField(value = "biaoduanuid")
    private String biaoduanuid;

    /**
     * 标段name
     */
    @TableField(value = "biaoduanname")
    private String biaoduanname;

    /**
     * 项目编号
     */
    @TableField(value = "progectno")
    private String progectno;

    /**
     * 项目名称
     */
    @TableField(value = "projectname")
    private String projectname;

    /**
     * 所属处室
     */
    @TableField(value = "department")
    private String department;

    /**
     * 所属单位
     */
    @TableField(value = "company")
    private String company;

    /**
     * 项目跟踪日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "genzong")
    private Date genzong;

    /**
     * 是否完成代理公司投标
     */
    @TableField(value = "dailitoubiao")
    private String dailitoubiao;

    /**
     * 是否中标
     */
    @TableField(value = "is_zhongbiao")
    private String isZhongbiao;

    /**
     * 签订代理合同时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "dailihetong")
    private Date dailihetong;

    /**
     * 是否独自招揽项目
     */
    @TableField(value = "is_duzizhaolan")
    private String isDuzizhaolan;

    /**
     * 资金来源
     */
    @TableField(value = "zijin")
    private String zijin;

    /**
     * 项目来源
     */
    @TableField(value = "xiangmu")
    private String xiangmu;

    /**
     * 项目阶段
     */
    @TableField(value = "jieduan")
    private String jieduan;

    /**
     * "是否已确定采购计划
     * （选择：是、否）"
     */
    @TableField(value = "caigoujihua")
    private String caigoujihua;

    /**
     * "是否完成采购备案
     * （选择：是、否）"
     */
    @TableField(value = "caigoubeian")
    private String caigoubeian;

    /**
     * 招标时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "bidding_time")
    private Date biddingTime;

    /**
     * 受理时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "acceptance_time")
    private Date acceptanceTime;

    /**
     * 定标时间(发送定标通知书)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "db_time")
    private Date dbTime;

    /**
     * 招标方式
     */
    @TableField(value = "zbfs")
    private String zbfs;

    /**
     * 是否必联网
     */
    @TableField(value = "shifoublw")
    private String shifoublw;

    /**
     * 是否在招投标公共服务平台
     */
    @TableField(value = "ztbggfwpt")
    private String ztbggfwpt;

    /**
     * 投标人数量
     */
    @TableField(value = "tbr_num")
    private Integer tbrNum;

    /**
     * 评分方法
     */
    @TableField(value = "score_method")
    private String scoreMethod;

    /**
     * 评标方式
     */
    @TableField(value = "pingbiaofs")
    private String pingbiaofs;

    /**
     * 前往项目单位进行招标文件编制沟通次数
     */
    @TableField(value = "zbwjgt")
    private Integer zbwjgt;

    /**
     * 是否至地方交易中心完成评审工作的标
     */
    @TableField(value = "assessment")
    private String assessment;

    /**
     * 是否协助贷款机构对电子交易系统进行评估
     */
    @TableField(value = "pinggu")
    private String pinggu;

    /**
     * 异常情况说明
     */
    @TableField(value = "message_text")
    private String messageText;

    /**
     * 拟处理结果
     */
    @TableField(value = "handle")
    private String handle;

    /**
     * 是否协助开标评标
     */
    @TableField(value = "is_pingb")
    private String isPingb;

    /**
     * "是否完成合同谈判及签订采购代理合同
     * （选择：是、否）"
     */
    @TableField(value = "is_tanpan")
    private String isTanpan;

    /**
     * "是否完成办理支付、进口、报关、提货
     * （选择：是、否）"
     */
    @TableField(value = "is_banli")
    private String isBanli;

    /**
     * "是否完成合同归档
     * （选择：是、否）"
     */
    @TableField(value = "is_guidang")
    private String isGuidang;

    /**
     * "项目阶段
     * （任选其一：投融资决策咨询、
     * 编写项目建议书和申报材料、
     * 列入国家外资贷款项目备选规划、
     * 列入贷款机构三年滚动计划清单、
     * 贷款机构对项目评估、
     * 贷款谈判及草签贷款协定、
     * 项目协议 ）"
     */
    @TableField(value = "project_jd")
    private String projectJd;

    /**
     * 贷款机构对项目合同检查评议时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "daikuan_py")
    private Date daikuanPy;

    /**
     * 贷款机构评议结果
     */
    @TableField(value = "daikuan_py_data")
    private String daikuanPyData;

    /**
     * 未按时退还保证金次数
     */
    @TableField(value = "anshituihuan")
    private Integer anshituihuan;

    /**
     * 未及时按规定在外部系统录入信息
     */
    @TableField(value = "jishiluru")
    private Integer jishiluru;

    /**
     * 自身原因导致的意义或投诉次数
     */
    @TableField(value = "zishen")
    private Integer zishen;

    /**
     * 评标纪律问题次数
     */
    @TableField(value = "jilv")
    private Integer jilv;

    /**
     * 招标质量问题扣分
     */
    @TableField(value = "zhiliang")
    private Integer zhiliang;

    /**
     * 违法违纪次数
     */
    @TableField(value = "weifaweiji")
    private Integer weifaweiji;

    /**
     * 列入国家外资贷款项目备选规划时间
     */
    @TableField(value = "alternative_planning_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date alternativePlanningTime;

    /**
     * 列入贷款机构三年滚动计划清单时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "rolling_plan_time")
    private Date rollingPlanTime;

    /**
     * 贷款机构签署备忘录时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "memorandum_time")
    private Date memorandumTime;

    /**
     * 贷款谈判及草签贷款协定、项目协议时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "project_agreement_time")
    private Date projectAgreementTime;

    /**
     * 列入项目管理手册时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "management_manual_time")
    private Date managementManualTime;

    /**
     * 采购培训次数
     */
    @TableField(value = "caigoupeixun_num")
    private Integer caigoupeixunNum;

    /**
     * 账号
     */
    @TableField(value = "jbr_code")
    private String jbrCode;

    /**
     * 名字
     */
    @TableField(value = "jbr_name")
    private String jbrName;

    /**
     * 投融资决策咨询、编写项目建议书和申报材料时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "application_materials_date")
    private Date applicationMaterialsDate;

    /**
     * 开标时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "bid_open_date")
    private Date bidOpenDate;

    /**
     * 评标时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "bid_evaluation_date")
    private Date bidEvaluationDate;

    /**
     * 前往至外地项目单位进行招标文件编制沟通的次数
     */
    @TableField(value = "prepare_communication")
    private Integer prepareCommunication;

    /**
     * 协助贷款机构评估会议日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "assistance_agencies_date")
    private Date assistanceAgenciesDate;

    /**
     * 中标通知书发出时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "acceptance_date")
    private Date acceptanceDate;

    /**
     * 签订采购代理合同日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "procurement_agency_date")
    private Date procurementAgencyDate;

    /**
     * 办理进口报关时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "handling_import_date")
    private Date handlingImportDate;

    /**
     * 提货时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "take_delivery_date")
    private Date takeDeliveryDate;

    /**
     * 支付时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "payment_date")
    private Date paymentDate;

    /**
     * 合同归档日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "contract_filing_date")
    private Date contractFilingDate;

    /**
     * 年度客户满意度调查时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "manyi_py")
    private Date manyiPy;

    /**
     * 年度客户满意度调查结果
     */

    @TableField(value = "manyi_py_data")
    private String manyiPyData;

    @TableField(value = "level")
    private int level;

    @TableField(value = "status")
    private int status;

    @TableField(value = "caigoupeixun")
    private String caigoupeixun;


    @TableField(value = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private List<AchiWbDTO> achiWbList;

    @TableField(exist = false)
    private List<AchiWbDTO> achiWbDTOList;
    @TableField(exist = false)
    private Integer pageNum;
    @TableField(exist = false)
    private Integer pageSize;
    @TableField(exist = false)
    private String sort;
    @TableField(exist = false)
    private Long startTime;
    @TableField(exist = false)
    private Long stopTime;
    @TableField(exist = false)
    private String nameOrId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public List<AchiWbDTO> getAchiWbDTOList() {
        return achiWbDTOList;
    }

    public void setAchiWbDTOList(List<AchiWbDTO> achiWbDTOList) {
        this.achiWbDTOList = achiWbDTOList;
    }

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

    public String getNameOrId() {
        return nameOrId;
    }

    public void setNameOrId(String nameOrId) {
        this.nameOrId = nameOrId;
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

    public String getProgectno() {
        return progectno;
    }

    public void setProgectno(String progectno) {
        this.progectno = progectno;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getGenzong() {
        return genzong;
    }

    public void setGenzong(Date genzong) {
        this.genzong = genzong;
    }

    public String getDailitoubiao() {
        return dailitoubiao;
    }

    public void setDailitoubiao(String dailitoubiao) {
        this.dailitoubiao = dailitoubiao;
    }

    public String getIsZhongbiao() {
        return isZhongbiao;
    }

    public void setIsZhongbiao(String isZhongbiao) {
        this.isZhongbiao = isZhongbiao;
    }

    public Date getDailihetong() {
        return dailihetong;
    }

    public void setDailihetong(Date dailihetong) {
        this.dailihetong = dailihetong;
    }

    public String getIsDuzizhaolan() {
        return isDuzizhaolan;
    }

    public void setIsDuzizhaolan(String isDuzizhaolan) {
        this.isDuzizhaolan = isDuzizhaolan;
    }

    public String getZijin() {
        return zijin;
    }

    public void setZijin(String zijin) {
        this.zijin = zijin;
    }

    public String getXiangmu() {
        return xiangmu;
    }

    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }

    public String getJieduan() {
        return jieduan;
    }

    public void setJieduan(String jieduan) {
        this.jieduan = jieduan;
    }

    public String getCaigoujihua() {
        return caigoujihua;
    }

    public void setCaigoujihua(String caigoujihua) {
        this.caigoujihua = caigoujihua;
    }

    public String getCaigoubeian() {
        return caigoubeian;
    }

    public void setCaigoubeian(String caigoubeian) {
        this.caigoubeian = caigoubeian;
    }

    public Date getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(Date biddingTime) {
        this.biddingTime = biddingTime;
    }

    public Date getAcceptanceTime() {
        return acceptanceTime;
    }

    public void setAcceptanceTime(Date acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }

    public Date getDbTime() {
        return dbTime;
    }

    public void setDbTime(Date dbTime) {
        this.dbTime = dbTime;
    }

    public String getZbfs() {
        return zbfs;
    }

    public void setZbfs(String zbfs) {
        this.zbfs = zbfs;
    }

    public String getShifoublw() {
        return shifoublw;
    }

    public void setShifoublw(String shifoublw) {
        this.shifoublw = shifoublw;
    }

    public String getZtbggfwpt() {
        return ztbggfwpt;
    }

    public void setZtbggfwpt(String ztbggfwpt) {
        this.ztbggfwpt = ztbggfwpt;
    }

    public Integer getTbrNum() {
        return tbrNum;
    }

    public void setTbrNum(Integer tbrNum) {
        this.tbrNum = tbrNum;
    }

    public String getScoreMethod() {
        return scoreMethod;
    }

    public void setScoreMethod(String scoreMethod) {
        this.scoreMethod = scoreMethod;
    }

    public String getPingbiaofs() {
        return pingbiaofs;
    }

    public void setPingbiaofs(String pingbiaofs) {
        this.pingbiaofs = pingbiaofs;
    }

    public Integer getZbwjgt() {
        return zbwjgt;
    }

    public void setZbwjgt(Integer zbwjgt) {
        this.zbwjgt = zbwjgt;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getPinggu() {
        return pinggu;
    }

    public void setPinggu(String pinggu) {
        this.pinggu = pinggu;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getIsPingb() {
        return isPingb;
    }

    public void setIsPingb(String isPingb) {
        this.isPingb = isPingb;
    }

    public String getIsTanpan() {
        return isTanpan;
    }

    public void setIsTanpan(String isTanpan) {
        this.isTanpan = isTanpan;
    }

    public String getIsBanli() {
        return isBanli;
    }

    public void setIsBanli(String isBanli) {
        this.isBanli = isBanli;
    }

    public String getIsGuidang() {
        return isGuidang;
    }

    public void setIsGuidang(String isGuidang) {
        this.isGuidang = isGuidang;
    }

    public String getProjectJd() {
        return projectJd;
    }

    public void setProjectJd(String projectJd) {
        this.projectJd = projectJd;
    }

    public Date getDaikuanPy() {
        return daikuanPy;
    }

    public void setDaikuanPy(Date daikuanPy) {
        this.daikuanPy = daikuanPy;
    }

    public String getDaikuanPyData() {
        return daikuanPyData;
    }

    public void setDaikuanPyData(String daikuanPyData) {
        this.daikuanPyData = daikuanPyData;
    }

    public Integer getAnshituihuan() {
        return anshituihuan;
    }

    public void setAnshituihuan(Integer anshituihuan) {
        this.anshituihuan = anshituihuan;
    }

    public Integer getJishiluru() {
        return jishiluru;
    }

    public void setJishiluru(Integer jishiluru) {
        this.jishiluru = jishiluru;
    }

    public Integer getZishen() {
        return zishen;
    }

    public void setZishen(Integer zishen) {
        this.zishen = zishen;
    }

    public Integer getJilv() {
        return jilv;
    }

    public void setJilv(Integer jilv) {
        this.jilv = jilv;
    }

    public Integer getZhiliang() {
        return zhiliang;
    }

    public void setZhiliang(Integer zhiliang) {
        this.zhiliang = zhiliang;
    }

    public Integer getWeifaweiji() {
        return weifaweiji;
    }

    public void setWeifaweiji(Integer weifaweiji) {
        this.weifaweiji = weifaweiji;
    }

    public Date getAlternativePlanningTime() {
        return alternativePlanningTime;
    }

    public void setAlternativePlanningTime(Date alternativePlanningTime) {
        this.alternativePlanningTime = alternativePlanningTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getRollingPlanTime() {
        return rollingPlanTime;
    }

    public void setRollingPlanTime(Date rollingPlanTime) {
        this.rollingPlanTime = rollingPlanTime;
    }

    public Date getMemorandumTime() {
        return memorandumTime;
    }

    public void setMemorandumTime(Date memorandumTime) {
        this.memorandumTime = memorandumTime;
    }

    public Date getProjectAgreementTime() {
        return projectAgreementTime;
    }

    public void setProjectAgreementTime(Date projectAgreementTime) {
        this.projectAgreementTime = projectAgreementTime;
    }

    public Date getManagementManualTime() {
        return managementManualTime;
    }

    public void setManagementManualTime(Date managementManualTime) {
        this.managementManualTime = managementManualTime;
    }

    public Integer getCaigoupeixunNum() {
        return caigoupeixunNum;
    }

    public void setCaigoupeixunNum(Integer caigoupeixunNum) {
        this.caigoupeixunNum = caigoupeixunNum;
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

    public Date getApplicationMaterialsDate() {
        return applicationMaterialsDate;
    }

    public void setApplicationMaterialsDate(Date applicationMaterialsDate) {
        this.applicationMaterialsDate = applicationMaterialsDate;
    }

    public Date getBidOpenDate() {
        return bidOpenDate;
    }

    public void setBidOpenDate(Date bidOpenDate) {
        this.bidOpenDate = bidOpenDate;
    }

    public Date getBidEvaluationDate() {
        return bidEvaluationDate;
    }

    public void setBidEvaluationDate(Date bidEvaluationDate) {
        this.bidEvaluationDate = bidEvaluationDate;
    }

    public Integer getPrepareCommunication() {
        return prepareCommunication;
    }

    public void setPrepareCommunication(Integer prepareCommunication) {
        this.prepareCommunication = prepareCommunication;
    }

    public Date getAssistanceAgenciesDate() {
        return assistanceAgenciesDate;
    }

    public void setAssistanceAgenciesDate(Date assistanceAgenciesDate) {
        this.assistanceAgenciesDate = assistanceAgenciesDate;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public Date getProcurementAgencyDate() {
        return procurementAgencyDate;
    }

    public void setProcurementAgencyDate(Date procurementAgencyDate) {
        this.procurementAgencyDate = procurementAgencyDate;
    }

    public Date getHandlingImportDate() {
        return handlingImportDate;
    }

    public void setHandlingImportDate(Date handlingImportDate) {
        this.handlingImportDate = handlingImportDate;
    }

    public Date getTakeDeliveryDate() {
        return takeDeliveryDate;
    }

    public void setTakeDeliveryDate(Date takeDeliveryDate) {
        this.takeDeliveryDate = takeDeliveryDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getContractFilingDate() {
        return contractFilingDate;
    }

    public void setContractFilingDate(Date contractFilingDate) {
        this.contractFilingDate = contractFilingDate;
    }

    public Date getManyiPy() {
        return manyiPy;
    }

    public void setManyiPy(Date manyiPy) {
        this.manyiPy = manyiPy;
    }

    public String getManyiPyData() {
        return manyiPyData;
    }

    public void setManyiPyData(String manyiPyData) {
        this.manyiPyData = manyiPyData;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCaigoupeixun() {
        return caigoupeixun;
    }

    public void setCaigoupeixun(String caigoupeixun) {
        this.caigoupeixun = caigoupeixun;
    }


    public List<AchiWbDTO> getAchiWbList() {
        return achiWbList;
    }

    public void setAchiWbList(List<AchiWbDTO> achiWbList) {
        this.achiWbList = achiWbList;
    }
}