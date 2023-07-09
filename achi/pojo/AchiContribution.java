package com.deta.achi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 个人绩效贡献
 * @TableName achi_contribution
 */
@TableName(value ="achi_contribution")
public class AchiContribution implements Serializable {
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
    private int introduceDomesticSum;

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
     * 采购标准-新编制（个数）:第一编写人
     */
    @TableField(value = "preparation_standards_first")
    private Integer preparationStandardsFirst;

    /**
     * 采购标准-新编制（个数）：主审人

     */
    @TableField(value = "preparation_standards_zs")
    private Integer preparationStandardsZs;

    public int getIntroduceDomesticSum() {
        return introduceDomesticSum;
    }

    public void setIntroduceDomesticSum(int introduceDomesticSum) {
        this.introduceDomesticSum = introduceDomesticSum;
    }

    /**
     * 采购标准-新编制（个数）：其他
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
    /**
     * 采购标准-修订（个数）
     */
    @TableField(value = "biaozhun")
    private Integer biaozhun;

    @TableField(value = "joint_procurement")
    private Integer jointProcurement;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Integer getJointProcurement() {
        return jointProcurement;
    }

    public void setJointProcurement(Integer jointProcurement) {
        this.jointProcurement = jointProcurement;
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

    public Integer getBiaozhun() {
        return biaozhun;
    }

    public void setBiaozhun(Integer biaozhun) {
        this.biaozhun = biaozhun;
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

}