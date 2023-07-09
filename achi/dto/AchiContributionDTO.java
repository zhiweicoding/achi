package com.deta.achi.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.simpleframework.xml.Default;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 个人绩效贡献
 * @TableName achi_contribution
 */
@TableName(value ="achi_contribution")
public class AchiContributionDTO implements Serializable {
    /**
     * 
     */
    @TableId(value = "id")
    private String id;

    /**
     * 入职时间
     */
    @TableField(value = "join_time")
    private Long joinTime;

    /**
     * 工作开始时间
     */
    @TableField(value = "w_start_time")
    private Long wStartTime;

    /**
     * 单位类型
     */
    @TableField(value = "w_type")
    private String wType;

    /**
     * 排名
     */
    @TableField(value = "w_rank")
    private Integer wRank;

    /**
     * 培训(积分)
     */
    @TableField(value = "class_hour")
    private Integer classHour;

    /**
     * 抽调、巡视、审计（累计工作日）
     */
    @TableField(value = "w_work")
    private Integer wWork;

    /**
     * 业务标准编写（个数）
     */
    @TableField(value = "w_write")
    private Integer wWrite;

    /**
     * 建章立制（个数）
     */
    @TableField(value = "w_system")
    private Integer wSystem;

    /**
     * 党工群工作（次数）
     */
    @TableField(value = "workers")
    private Integer workers;

    /**
     * 专项工作
     */
    @TableField(value = "special")
    private Integer special;

    /**
     * 处室内其它管理工作（积分）
     */
    @TableField(value = "tration")
    private Integer tration;

    /**
     * 师带徒
     */
    @TableField(value = "apprentice")
    private Integer apprentice;

    /**
     * 用户名
     */
    @TableField(value = "work_name")
    private String workName;

    /**
     * 任务时间
     */
    @TableField(value = "con_year")
    private String conYear;

    /**
     * 年度人均净利润（填写金额，单位万元）
     */
    @TableField(value = "niandu_money")
    private BigDecimal nianduMoney;

    /**
     * 外部市场总收入（填写金额，单位万元）
     */
    @TableField(value = "waibu_money")
    private BigDecimal waibuMoney;

    /**
     * 履行招标经理职责得分
     */
    @TableField(value = "jlzhize")
    private Integer jlzhize;

    /**
     * 负责或深度参与工作得分
     */
    @TableField(value = "shendugz")
    private Integer shendugz;

    /**
     * 项目单位推荐招标经理：竞争性招标项目中，项目单位推荐招标经理，且招标经理承接此项目
     */
    @TableField(value = "xiangmu")
    private String xiangmu;

    /**
     * 所属单位
     */
    @TableField(value = "company")
    private String company;

    /**
     * 所属处室
     */
    @TableField(value = "department")
    private String department;

    /**
     * 类型名字
     */
    @TableField(value = "type_name")
    private String typeName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Long updateTime;

    /**
     * 1有效0无效
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 当前账号id
     */
    @TableField(value = "work_id")
    private String workId;

    /**
     * 全部工作完成度
     */
    @TableField(value = "work_plan_completion")
    private String workPlanCompletion;

    /**
     * 引进国产替代次数
     */
    @TableField(value = "introduce_domestic_sum")
    private String introduceDomesticSum;

    /**
     * 取得职称时间
     */
    @TableField(value = "professional_title_time")
    private Long professionalTitleTime;

    /**
     * 职称类别
     */
    @TableField(value = "professional_category")
    private String professionalCategory;

    /**
     * 取得注册类资格时间
     */
    @TableField(value = "obtain_registration_qualification_time")
    private Long obtainRegistrationQualificationTime;

    /**
     * 取得注册类资格个数
     */
    @TableField(value = "obtain_registration_qualification_qty")
    private Integer obtainRegistrationQualificationQty;

    /**
     * 绿色低碳、三新三化等方面取得进展的
     */
    @TableField(value = "green_low_carbon_times")
    private Integer greenLowCarbonTimes;

    /**
     * 受到批评
     */
    @TableField(value = "criticism_times")
    private String criticismTimes;

    /**
     * 需求单位书面投诉
     */
    @TableField(value = "written_complaints_times")
    private Integer writtenComplaintsTimes;

    /**
     * 书面表扬
     */
    @TableField(value = "written_praise_times")
    private String writtenPraiseTimes;

    /**
     * 获得荣誉
     */
    @TableField(value = "honor_type_qty")
    private String honorTypeQty;

    /**
     * 招标单价
     */
    @TableField(value = "bidding_price")
    private BigDecimal biddingPrice;

    /**
     * 是否集团公司为参与单位的联合采购
     */
    @TableField(value = "is_jointprocurement")
    private String isJointprocurement;

    /**
     * 是否产生重大项目保产保供问题
     */
    @TableField(value = "is_big_problem")
    private String isBigProblem;

    /**
     * 审计、巡视巡察、上级检查问题次数-国家级
     */
    @TableField(value = "national_level")
    private String nationalLevel;

    /**
     * 审计、巡视巡察、上级检查问题次数-集团公司级
     */
    @TableField(value = "company_level")
    private String companyLevel;

    /**
     * 审计、巡视巡察、上级检查问题次数-中心级
     */
    @TableField(value = "center_level")
    private String centerLevel;

    /**
     * 审计、巡视巡察、上级检查问题次数-业务部内部 
     */
    @TableField(value = "internal_business")
    private String internalBusiness;

    /**
     * 被查实的举报次数
     */
    @TableField(value = "reports_num")
    private Integer reportsNum;

    /**
     * 业务标准编写
     */
    @TableField(value = "business_standard")
    private Integer businessStandard;

    /**
     * 通过2.0系统完成的招标采办项目每完成一个标段
     */
    @TableField(value = "issue_time")
    private Integer issueTime;

    /**
     * 采购标准-编制（个数）:第一编写人
     */
    @TableField(value = "preparation_standards_first")
    private Integer preparationStandardsFirst;

    /**
     * 采购标准-编制（个数）：主审人

     */
    @TableField(value = "preparation_standards_zs")
    private Integer preparationStandardsZs;

    /**
     * 采购标准-编制（个数）：其他
     */
    @TableField(value = "preparation_standards_other")
    private Integer preparationStandardsOther;

    /**
     * 优选供应商名录工作：负责人
     */
    @TableField(value = "preferred_supplier_bx")
    private Integer preferredSupplierBx;

    /**
     * 优选供应商名录工作：编写人员
     */
    @TableField(value = "preferred_supplier_cy")
    private Integer preferredSupplierCy;

    @TableField(value = "joint_procurement")
    private Integer jointProcurement;
    @Value(value = "10")
    private Integer pageNum;

    private Integer pageSize;

    private String sort;

    @TableField(exist = false)
    private Long startTime;
    @TableField(exist = false)
    private Long stopTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Integer getJointProcurement() {
        return jointProcurement;
    }

    public void setJointProcurement(Integer jointProcurement) {
        this.jointProcurement = jointProcurement;
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

    /**
     * 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 入职时间
     */
    public Long getJoinTime() {
        return joinTime;
    }

    /**
     * 入职时间
     */
    public void setJoinTime(Long joinTime) {
        this.joinTime = joinTime;
    }

    /**
     * 工作开始时间
     */
    public Long getwStartTime() {
        return wStartTime;
    }

    /**
     * 工作开始时间
     */
    public void setwStartTime(Long wStartTime) {
        this.wStartTime = wStartTime;
    }

    /**
     * 单位类型
     */
    public String getwType() {
        return wType;
    }

    /**
     * 单位类型
     */
    public void setwType(String wType) {
        this.wType = wType;
    }

    /**
     * 排名
     */
    public Integer getwRank() {
        return wRank;
    }

    /**
     * 排名
     */
    public void setwRank(Integer wRank) {
        this.wRank = wRank;
    }

    /**
     * 培训(积分)
     */
    public Integer getClassHour() {
        return classHour;
    }

    /**
     * 培训(积分)
     */
    public void setClassHour(Integer classHour) {
        this.classHour = classHour;
    }

    /**
     * 抽调、巡视、审计（累计工作日）
     */
    public Integer getwWork() {
        return wWork;
    }

    /**
     * 抽调、巡视、审计（累计工作日）
     */
    public void setwWork(Integer wWork) {
        this.wWork = wWork;
    }

    /**
     * 业务标准编写（个数）
     */
    public Integer getwWrite() {
        return wWrite;
    }

    /**
     * 业务标准编写（个数）
     */
    public void setwWrite(Integer wWrite) {
        this.wWrite = wWrite;
    }

    /**
     * 建章立制（个数）
     */
    public Integer getwSystem() {
        return wSystem;
    }

    /**
     * 建章立制（个数）
     */
    public void setwSystem(Integer wSystem) {
        this.wSystem = wSystem;
    }

    /**
     * 党工群工作（次数）
     */
    public Integer getWorkers() {
        return workers;
    }

    /**
     * 党工群工作（次数）
     */
    public void setWorkers(Integer workers) {
        this.workers = workers;
    }

    /**
     * 专项工作
     */
    public Integer getSpecial() {
        return special;
    }

    /**
     * 专项工作
     */
    public void setSpecial(Integer special) {
        this.special = special;
    }

    /**
     * 处室内其它管理工作（积分）
     */
    public Integer getTration() {
        return tration;
    }

    /**
     * 处室内其它管理工作（积分）
     */
    public void setTration(Integer tration) {
        this.tration = tration;
    }

    /**
     * 师带徒
     */
    public Integer getApprentice() {
        return apprentice;
    }

    /**
     * 师带徒
     */
    public void setApprentice(Integer apprentice) {
        this.apprentice = apprentice;
    }

    /**
     * 用户名
     */
    public String getWorkName() {
        return workName;
    }

    /**
     * 用户名
     */
    public void setWorkName(String workName) {
        this.workName = workName;
    }

    /**
     * 任务时间
     */
    public String getConYear() {
        return conYear;
    }

    /**
     * 任务时间
     */
    public void setConYear(String conYear) {
        this.conYear = conYear;
    }

    /**
     * 年度人均净利润（填写金额，单位万元）
     */
    public BigDecimal getNianduMoney() {
        return nianduMoney;
    }

    /**
     * 年度人均净利润（填写金额，单位万元）
     */
    public void setNianduMoney(BigDecimal nianduMoney) {
        this.nianduMoney = nianduMoney;
    }

    /**
     * 外部市场总收入（填写金额，单位万元）
     */
    public BigDecimal getWaibuMoney() {
        return waibuMoney;
    }

    /**
     * 外部市场总收入（填写金额，单位万元）
     */
    public void setWaibuMoney(BigDecimal waibuMoney) {
        this.waibuMoney = waibuMoney;
    }

    /**
     * 履行招标经理职责得分
     */
    public Integer getJlzhize() {
        return jlzhize;
    }

    /**
     * 履行招标经理职责得分
     */
    public void setJlzhize(Integer jlzhize) {
        this.jlzhize = jlzhize;
    }

    /**
     * 负责或深度参与工作得分
     */
    public Integer getShendugz() {
        return shendugz;
    }

    /**
     * 负责或深度参与工作得分
     */
    public void setShendugz(Integer shendugz) {
        this.shendugz = shendugz;
    }

    /**
     * 项目单位推荐招标经理：竞争性招标项目中，项目单位推荐招标经理，且招标经理承接此项目
     */
    public String getXiangmu() {
        return xiangmu;
    }

    /**
     * 项目单位推荐招标经理：竞争性招标项目中，项目单位推荐招标经理，且招标经理承接此项目
     */
    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }

    /**
     * 所属单位
     */
    public String getCompany() {
        return company;
    }

    /**
     * 所属单位
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 所属处室
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 所属处室
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 类型名字
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 类型名字
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 更新时间
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 1有效0无效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1有效0无效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 当前账号id
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 当前账号id
     */
    public void setWorkId(String workId) {
        this.workId = workId;
    }

    /**
     * 全部工作完成度
     */
    public String getWorkPlanCompletion() {
        return workPlanCompletion;
    }

    /**
     * 全部工作完成度
     */
    public void setWorkPlanCompletion(String workPlanCompletion) {
        this.workPlanCompletion = workPlanCompletion;
    }

    /**
     * 引进国产替代次数
     */
    public String getIntroduceDomesticSum() {
        return introduceDomesticSum;
    }

    /**
     * 引进国产替代次数
     */
    public void setIntroduceDomesticSum(String introduceDomesticSum) {
        this.introduceDomesticSum = introduceDomesticSum;
    }

    /**
     * 取得职称时间
     */
    public Long getProfessionalTitleTime() {
        return professionalTitleTime;
    }

    /**
     * 取得职称时间
     */
    public void setProfessionalTitleTime(Long professionalTitleTime) {
        this.professionalTitleTime = professionalTitleTime;
    }

    /**
     * 职称类别
     */
    public String getProfessionalCategory() {
        return professionalCategory;
    }

    /**
     * 职称类别
     */
    public void setProfessionalCategory(String professionalCategory) {
        this.professionalCategory = professionalCategory;
    }

    /**
     * 取得注册类资格时间
     */
    public Long getObtainRegistrationQualificationTime() {
        return obtainRegistrationQualificationTime;
    }

    /**
     * 取得注册类资格时间
     */
    public void setObtainRegistrationQualificationTime(Long obtainRegistrationQualificationTime) {
        this.obtainRegistrationQualificationTime = obtainRegistrationQualificationTime;
    }

    /**
     * 取得注册类资格个数
     */
    public Integer getObtainRegistrationQualificationQty() {
        return obtainRegistrationQualificationQty;
    }

    /**
     * 取得注册类资格个数
     */
    public void setObtainRegistrationQualificationQty(Integer obtainRegistrationQualificationQty) {
        this.obtainRegistrationQualificationQty = obtainRegistrationQualificationQty;
    }

    /**
     * 绿色低碳、三新三化等方面取得进展的
     */
    public Integer getGreenLowCarbonTimes() {
        return greenLowCarbonTimes;
    }

    /**
     * 绿色低碳、三新三化等方面取得进展的
     */
    public void setGreenLowCarbonTimes(Integer greenLowCarbonTimes) {
        this.greenLowCarbonTimes = greenLowCarbonTimes;
    }

    /**
     * 受到批评
     */
    public String getCriticismTimes() {
        return criticismTimes;
    }

    /**
     * 受到批评
     */
    public void setCriticismTimes(String criticismTimes) {
        this.criticismTimes = criticismTimes;
    }

    /**
     * 需求单位书面投诉
     */
    public Integer getWrittenComplaintsTimes() {
        return writtenComplaintsTimes;
    }

    /**
     * 需求单位书面投诉
     */
    public void setWrittenComplaintsTimes(Integer writtenComplaintsTimes) {
        this.writtenComplaintsTimes = writtenComplaintsTimes;
    }

    /**
     * 书面表扬
     */
    public String getWrittenPraiseTimes() {
        return writtenPraiseTimes;
    }

    /**
     * 书面表扬
     */
    public void setWrittenPraiseTimes(String writtenPraiseTimes) {
        this.writtenPraiseTimes = writtenPraiseTimes;
    }

    /**
     * 获得荣誉
     */
    public String getHonorTypeQty() {
        return honorTypeQty;
    }

    /**
     * 获得荣誉
     */
    public void setHonorTypeQty(String honorTypeQty) {
        this.honorTypeQty = honorTypeQty;
    }

    /**
     * 招标单价
     */
    public BigDecimal getBiddingPrice() {
        return biddingPrice;
    }

    /**
     * 招标单价
     */
    public void setBiddingPrice(BigDecimal biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

    /**
     * 是否集团公司为参与单位的联合采购
     */
    public String getIsJointprocurement() {
        return isJointprocurement;
    }

    /**
     * 是否集团公司为参与单位的联合采购
     */
    public void setIsJointprocurement(String isJointprocurement) {
        this.isJointprocurement = isJointprocurement;
    }

    /**
     * 是否产生重大项目保产保供问题
     */
    public String getIsBigProblem() {
        return isBigProblem;
    }

    /**
     * 是否产生重大项目保产保供问题
     */
    public void setIsBigProblem(String isBigProblem) {
        this.isBigProblem = isBigProblem;
    }

    /**
     * 审计、巡视巡察、上级检查问题次数-国家级
     */
    public String getNationalLevel() {
        return nationalLevel;
    }

    /**
     * 审计、巡视巡察、上级检查问题次数-国家级
     */
    public void setNationalLevel(String nationalLevel) {
        this.nationalLevel = nationalLevel;
    }

    /**
     * 审计、巡视巡察、上级检查问题次数-集团公司级
     */
    public String getCompanyLevel() {
        return companyLevel;
    }

    /**
     * 审计、巡视巡察、上级检查问题次数-集团公司级
     */
    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }

    /**
     * 审计、巡视巡察、上级检查问题次数-中心级
     */
    public String getCenterLevel() {
        return centerLevel;
    }

    /**
     * 审计、巡视巡察、上级检查问题次数-中心级
     */
    public void setCenterLevel(String centerLevel) {
        this.centerLevel = centerLevel;
    }

    /**
     * 审计、巡视巡察、上级检查问题次数-业务部内部 
     */
    public String getInternalBusiness() {
        return internalBusiness;
    }

    /**
     * 审计、巡视巡察、上级检查问题次数-业务部内部 
     */
    public void setInternalBusiness(String internalBusiness) {
        this.internalBusiness = internalBusiness;
    }

    /**
     * 被查实的举报次数
     */
    public Integer getReportsNum() {
        return reportsNum;
    }

    /**
     * 被查实的举报次数
     */
    public void setReportsNum(Integer reportsNum) {
        this.reportsNum = reportsNum;
    }

    /**
     * 业务标准编写
     */
    public Integer getBusinessStandard() {
        return businessStandard;
    }

    /**
     * 业务标准编写
     */
    public void setBusinessStandard(Integer businessStandard) {
        this.businessStandard = businessStandard;
    }

    /**
     * 通过2.0系统完成的招标采办项目每完成一个标段
     */
    public Integer getIssueTime() {
        return issueTime;
    }

    /**
     * 通过2.0系统完成的招标采办项目每完成一个标段
     */
    public void setIssueTime(Integer issueTime) {
        this.issueTime = issueTime;
    }

    /**
     * 采购标准-编制（个数）:第一编写人
     */
    public Integer getPreparationStandardsFirst() {
        return preparationStandardsFirst;
    }

    /**
     * 采购标准-编制（个数）:第一编写人
     */
    public void setPreparationStandardsFirst(Integer preparationStandardsFirst) {
        this.preparationStandardsFirst = preparationStandardsFirst;
    }

    /**
     * 采购标准-编制（个数）：主审人

     */
    public Integer getPreparationStandardsZs() {
        return preparationStandardsZs;
    }

    /**
     * 采购标准-编制（个数）：主审人

     */
    public void setPreparationStandardsZs(Integer preparationStandardsZs) {
        this.preparationStandardsZs = preparationStandardsZs;
    }

    /**
     * 采购标准-编制（个数）：其他
     */
    public Integer getPreparationStandardsOther() {
        return preparationStandardsOther;
    }

    /**
     * 采购标准-编制（个数）：其他
     */
    public void setPreparationStandardsOther(Integer preparationStandardsOther) {
        this.preparationStandardsOther = preparationStandardsOther;
    }

    /**
     * 优选供应商名录工作：负责人
     */
    public Integer getPreferredSupplierBx() {
        return preferredSupplierBx;
    }

    /**
     * 优选供应商名录工作：负责人
     */
    public void setPreferredSupplierBx(Integer preferredSupplierBx) {
        this.preferredSupplierBx = preferredSupplierBx;
    }

    /**
     * 优选供应商名录工作：编写人员
     */
    public Integer getPreferredSupplierCy() {
        return preferredSupplierCy;
    }

    /**
     * 优选供应商名录工作：编写人员
     */
    public void setPreferredSupplierCy(Integer preferredSupplierCy) {
        this.preferredSupplierCy = preferredSupplierCy;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AchiContributionDTO other = (AchiContributionDTO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getJoinTime() == null ? other.getJoinTime() == null : this.getJoinTime().equals(other.getJoinTime()))
            && (this.getwStartTime() == null ? other.getwStartTime() == null : this.getwStartTime().equals(other.getwStartTime()))
            && (this.getwType() == null ? other.getwType() == null : this.getwType().equals(other.getwType()))
            && (this.getwRank() == null ? other.getwRank() == null : this.getwRank().equals(other.getwRank()))
            && (this.getClassHour() == null ? other.getClassHour() == null : this.getClassHour().equals(other.getClassHour()))
            && (this.getwWork() == null ? other.getwWork() == null : this.getwWork().equals(other.getwWork()))
            && (this.getwWrite() == null ? other.getwWrite() == null : this.getwWrite().equals(other.getwWrite()))
            && (this.getwSystem() == null ? other.getwSystem() == null : this.getwSystem().equals(other.getwSystem()))
            && (this.getWorkers() == null ? other.getWorkers() == null : this.getWorkers().equals(other.getWorkers()))
            && (this.getSpecial() == null ? other.getSpecial() == null : this.getSpecial().equals(other.getSpecial()))
            && (this.getTration() == null ? other.getTration() == null : this.getTration().equals(other.getTration()))
            && (this.getApprentice() == null ? other.getApprentice() == null : this.getApprentice().equals(other.getApprentice()))
            && (this.getWorkName() == null ? other.getWorkName() == null : this.getWorkName().equals(other.getWorkName()))
            && (this.getConYear() == null ? other.getConYear() == null : this.getConYear().equals(other.getConYear()))
            && (this.getNianduMoney() == null ? other.getNianduMoney() == null : this.getNianduMoney().equals(other.getNianduMoney()))
            && (this.getWaibuMoney() == null ? other.getWaibuMoney() == null : this.getWaibuMoney().equals(other.getWaibuMoney()))
            && (this.getJlzhize() == null ? other.getJlzhize() == null : this.getJlzhize().equals(other.getJlzhize()))
            && (this.getShendugz() == null ? other.getShendugz() == null : this.getShendugz().equals(other.getShendugz()))
            && (this.getXiangmu() == null ? other.getXiangmu() == null : this.getXiangmu().equals(other.getXiangmu()))
            && (this.getCompany() == null ? other.getCompany() == null : this.getCompany().equals(other.getCompany()))
            && (this.getDepartment() == null ? other.getDepartment() == null : this.getDepartment().equals(other.getDepartment()))
            && (this.getTypeName() == null ? other.getTypeName() == null : this.getTypeName().equals(other.getTypeName()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getWorkId() == null ? other.getWorkId() == null : this.getWorkId().equals(other.getWorkId()))
            && (this.getWorkPlanCompletion() == null ? other.getWorkPlanCompletion() == null : this.getWorkPlanCompletion().equals(other.getWorkPlanCompletion()))
            && (this.getIntroduceDomesticSum() == null ? other.getIntroduceDomesticSum() == null : this.getIntroduceDomesticSum().equals(other.getIntroduceDomesticSum()))
            && (this.getProfessionalTitleTime() == null ? other.getProfessionalTitleTime() == null : this.getProfessionalTitleTime().equals(other.getProfessionalTitleTime()))
            && (this.getProfessionalCategory() == null ? other.getProfessionalCategory() == null : this.getProfessionalCategory().equals(other.getProfessionalCategory()))
            && (this.getObtainRegistrationQualificationTime() == null ? other.getObtainRegistrationQualificationTime() == null : this.getObtainRegistrationQualificationTime().equals(other.getObtainRegistrationQualificationTime()))
            && (this.getObtainRegistrationQualificationQty() == null ? other.getObtainRegistrationQualificationQty() == null : this.getObtainRegistrationQualificationQty().equals(other.getObtainRegistrationQualificationQty()))
            && (this.getGreenLowCarbonTimes() == null ? other.getGreenLowCarbonTimes() == null : this.getGreenLowCarbonTimes().equals(other.getGreenLowCarbonTimes()))
            && (this.getCriticismTimes() == null ? other.getCriticismTimes() == null : this.getCriticismTimes().equals(other.getCriticismTimes()))
            && (this.getWrittenComplaintsTimes() == null ? other.getWrittenComplaintsTimes() == null : this.getWrittenComplaintsTimes().equals(other.getWrittenComplaintsTimes()))
            && (this.getWrittenPraiseTimes() == null ? other.getWrittenPraiseTimes() == null : this.getWrittenPraiseTimes().equals(other.getWrittenPraiseTimes()))
            && (this.getHonorTypeQty() == null ? other.getHonorTypeQty() == null : this.getHonorTypeQty().equals(other.getHonorTypeQty()))
            && (this.getBiddingPrice() == null ? other.getBiddingPrice() == null : this.getBiddingPrice().equals(other.getBiddingPrice()))
            && (this.getIsJointprocurement() == null ? other.getIsJointprocurement() == null : this.getIsJointprocurement().equals(other.getIsJointprocurement()))
            && (this.getIsBigProblem() == null ? other.getIsBigProblem() == null : this.getIsBigProblem().equals(other.getIsBigProblem()))
            && (this.getNationalLevel() == null ? other.getNationalLevel() == null : this.getNationalLevel().equals(other.getNationalLevel()))
            && (this.getCompanyLevel() == null ? other.getCompanyLevel() == null : this.getCompanyLevel().equals(other.getCompanyLevel()))
            && (this.getCenterLevel() == null ? other.getCenterLevel() == null : this.getCenterLevel().equals(other.getCenterLevel()))
            && (this.getInternalBusiness() == null ? other.getInternalBusiness() == null : this.getInternalBusiness().equals(other.getInternalBusiness()))
            && (this.getReportsNum() == null ? other.getReportsNum() == null : this.getReportsNum().equals(other.getReportsNum()))
            && (this.getBusinessStandard() == null ? other.getBusinessStandard() == null : this.getBusinessStandard().equals(other.getBusinessStandard()))
            && (this.getIssueTime() == null ? other.getIssueTime() == null : this.getIssueTime().equals(other.getIssueTime()))
            && (this.getPreparationStandardsFirst() == null ? other.getPreparationStandardsFirst() == null : this.getPreparationStandardsFirst().equals(other.getPreparationStandardsFirst()))
            && (this.getPreparationStandardsZs() == null ? other.getPreparationStandardsZs() == null : this.getPreparationStandardsZs().equals(other.getPreparationStandardsZs()))
            && (this.getPreparationStandardsOther() == null ? other.getPreparationStandardsOther() == null : this.getPreparationStandardsOther().equals(other.getPreparationStandardsOther()))
            && (this.getPreferredSupplierBx() == null ? other.getPreferredSupplierBx() == null : this.getPreferredSupplierBx().equals(other.getPreferredSupplierBx()))
            && (this.getPreferredSupplierCy() == null ? other.getPreferredSupplierCy() == null : this.getPreferredSupplierCy().equals(other.getPreferredSupplierCy()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getJoinTime() == null) ? 0 : getJoinTime().hashCode());
        result = prime * result + ((getwStartTime() == null) ? 0 : getwStartTime().hashCode());
        result = prime * result + ((getwType() == null) ? 0 : getwType().hashCode());
        result = prime * result + ((getwRank() == null) ? 0 : getwRank().hashCode());
        result = prime * result + ((getClassHour() == null) ? 0 : getClassHour().hashCode());
        result = prime * result + ((getwWork() == null) ? 0 : getwWork().hashCode());
        result = prime * result + ((getwWrite() == null) ? 0 : getwWrite().hashCode());
        result = prime * result + ((getwSystem() == null) ? 0 : getwSystem().hashCode());
        result = prime * result + ((getWorkers() == null) ? 0 : getWorkers().hashCode());
        result = prime * result + ((getSpecial() == null) ? 0 : getSpecial().hashCode());
        result = prime * result + ((getTration() == null) ? 0 : getTration().hashCode());
        result = prime * result + ((getApprentice() == null) ? 0 : getApprentice().hashCode());
        result = prime * result + ((getWorkName() == null) ? 0 : getWorkName().hashCode());
        result = prime * result + ((getConYear() == null) ? 0 : getConYear().hashCode());
        result = prime * result + ((getNianduMoney() == null) ? 0 : getNianduMoney().hashCode());
        result = prime * result + ((getWaibuMoney() == null) ? 0 : getWaibuMoney().hashCode());
        result = prime * result + ((getJlzhize() == null) ? 0 : getJlzhize().hashCode());
        result = prime * result + ((getShendugz() == null) ? 0 : getShendugz().hashCode());
        result = prime * result + ((getXiangmu() == null) ? 0 : getXiangmu().hashCode());
        result = prime * result + ((getCompany() == null) ? 0 : getCompany().hashCode());
        result = prime * result + ((getDepartment() == null) ? 0 : getDepartment().hashCode());
        result = prime * result + ((getTypeName() == null) ? 0 : getTypeName().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getWorkId() == null) ? 0 : getWorkId().hashCode());
        result = prime * result + ((getWorkPlanCompletion() == null) ? 0 : getWorkPlanCompletion().hashCode());
        result = prime * result + ((getIntroduceDomesticSum() == null) ? 0 : getIntroduceDomesticSum().hashCode());
        result = prime * result + ((getProfessionalTitleTime() == null) ? 0 : getProfessionalTitleTime().hashCode());
        result = prime * result + ((getProfessionalCategory() == null) ? 0 : getProfessionalCategory().hashCode());
        result = prime * result + ((getObtainRegistrationQualificationTime() == null) ? 0 : getObtainRegistrationQualificationTime().hashCode());
        result = prime * result + ((getObtainRegistrationQualificationQty() == null) ? 0 : getObtainRegistrationQualificationQty().hashCode());
        result = prime * result + ((getGreenLowCarbonTimes() == null) ? 0 : getGreenLowCarbonTimes().hashCode());
        result = prime * result + ((getCriticismTimes() == null) ? 0 : getCriticismTimes().hashCode());
        result = prime * result + ((getWrittenComplaintsTimes() == null) ? 0 : getWrittenComplaintsTimes().hashCode());
        result = prime * result + ((getWrittenPraiseTimes() == null) ? 0 : getWrittenPraiseTimes().hashCode());
        result = prime * result + ((getHonorTypeQty() == null) ? 0 : getHonorTypeQty().hashCode());
        result = prime * result + ((getBiddingPrice() == null) ? 0 : getBiddingPrice().hashCode());
        result = prime * result + ((getIsJointprocurement() == null) ? 0 : getIsJointprocurement().hashCode());
        result = prime * result + ((getIsBigProblem() == null) ? 0 : getIsBigProblem().hashCode());
        result = prime * result + ((getNationalLevel() == null) ? 0 : getNationalLevel().hashCode());
        result = prime * result + ((getCompanyLevel() == null) ? 0 : getCompanyLevel().hashCode());
        result = prime * result + ((getCenterLevel() == null) ? 0 : getCenterLevel().hashCode());
        result = prime * result + ((getInternalBusiness() == null) ? 0 : getInternalBusiness().hashCode());
        result = prime * result + ((getReportsNum() == null) ? 0 : getReportsNum().hashCode());
        result = prime * result + ((getBusinessStandard() == null) ? 0 : getBusinessStandard().hashCode());
        result = prime * result + ((getIssueTime() == null) ? 0 : getIssueTime().hashCode());
        result = prime * result + ((getPreparationStandardsFirst() == null) ? 0 : getPreparationStandardsFirst().hashCode());
        result = prime * result + ((getPreparationStandardsZs() == null) ? 0 : getPreparationStandardsZs().hashCode());
        result = prime * result + ((getPreparationStandardsOther() == null) ? 0 : getPreparationStandardsOther().hashCode());
        result = prime * result + ((getPreferredSupplierBx() == null) ? 0 : getPreferredSupplierBx().hashCode());
        result = prime * result + ((getPreferredSupplierCy() == null) ? 0 : getPreferredSupplierCy().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", joinTime=").append(joinTime);
        sb.append(", wStartTime=").append(wStartTime);
        sb.append(", wType=").append(wType);
        sb.append(", wRank=").append(wRank);
        sb.append(", classHour=").append(classHour);
        sb.append(", wWork=").append(wWork);
        sb.append(", wWrite=").append(wWrite);
        sb.append(", wSystem=").append(wSystem);
        sb.append(", workers=").append(workers);
        sb.append(", special=").append(special);
        sb.append(", tration=").append(tration);
        sb.append(", apprentice=").append(apprentice);
        sb.append(", workName=").append(workName);
        sb.append(", conYear=").append(conYear);
        sb.append(", nianduMoney=").append(nianduMoney);
        sb.append(", waibuMoney=").append(waibuMoney);
        sb.append(", jlzhize=").append(jlzhize);
        sb.append(", shendugz=").append(shendugz);
        sb.append(", xiangmu=").append(xiangmu);
        sb.append(", company=").append(company);
        sb.append(", department=").append(department);
        sb.append(", typeName=").append(typeName);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", workId=").append(workId);
        sb.append(", workPlanCompletion=").append(workPlanCompletion);
        sb.append(", introduceDomesticSum=").append(introduceDomesticSum);
        sb.append(", professionalTitleTime=").append(professionalTitleTime);
        sb.append(", professionalCategory=").append(professionalCategory);
        sb.append(", obtainRegistrationQualificationTime=").append(obtainRegistrationQualificationTime);
        sb.append(", obtainRegistrationQualificationQty=").append(obtainRegistrationQualificationQty);
        sb.append(", greenLowCarbonTimes=").append(greenLowCarbonTimes);
        sb.append(", criticismTimes=").append(criticismTimes);
        sb.append(", writtenComplaintsTimes=").append(writtenComplaintsTimes);
        sb.append(", writtenPraiseTimes=").append(writtenPraiseTimes);
        sb.append(", honorTypeQty=").append(honorTypeQty);
        sb.append(", biddingPrice=").append(biddingPrice);
        sb.append(", isJointprocurement=").append(isJointprocurement);
        sb.append(", isBigProblem=").append(isBigProblem);
        sb.append(", nationalLevel=").append(nationalLevel);
        sb.append(", companyLevel=").append(companyLevel);
        sb.append(", centerLevel=").append(centerLevel);
        sb.append(", internalBusiness=").append(internalBusiness);
        sb.append(", reportsNum=").append(reportsNum);
        sb.append(", businessStandard=").append(businessStandard);
        sb.append(", issueTime=").append(issueTime);
        sb.append(", preparationStandardsFirst=").append(preparationStandardsFirst);
        sb.append(", preparationStandardsZs=").append(preparationStandardsZs);
        sb.append(", preparationStandardsOther=").append(preparationStandardsOther);
        sb.append(", preferredSupplierBx=").append(preferredSupplierBx);
        sb.append(", preferredSupplierCy=").append(preferredSupplierCy);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}