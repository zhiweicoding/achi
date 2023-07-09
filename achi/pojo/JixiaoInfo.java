package com.deta.achi.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.deta.achi.dto.PageAssistantBean;
import com.deta.achi.pojo.handler.DateTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @TableName jixiao_info
 */
@TableName(value ="achi_bidding")
public class JixiaoInfo extends PageAssistantBean<JixiaoInfo> implements Serializable {
    /**
     * 标段编号
     */
    @NotBlank(message = "[标段编号]不能为空")
    @Size(max = 256, message = "编码长度不能超过256")
    @ApiModelProperty("标段编号")
    @Length(max = 256, message = "编码长度不能超过256")
    private String biaoduanuid;
    /**
     * 标段名称
     */
    @Size(max = 256, message = "编码长度不能超过256")
    @ApiModelProperty("标段名称")
    @Length(max = 256, message = "编码长度不能超过256")
    private String biaoduanname;
    /**
     * 项目名称
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("项目名称")
    @Length(max = 255, message = "编码长度不能超过255")
    private String projectname;
    /**
     * 项目编号
     */
    @Size(max = 256, message = "编码长度不能超过256")
    @ApiModelProperty("项目编号")
    @Length(max = 256, message = "编码长度不能超过256")
    private String projectno;
    /**
     * 经办人（招标经理，编码+名称）
     */
    @Size(max = 0, message = "编码长度不能超过0")
    @ApiModelProperty("经办人（招标经理，编码+名称）")
    @Length(max = 0, message = "编码长度不能超过0")
    private String jbrCode;
    /**
     * 经办人姓名
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("经办人姓名")
    @Length(max = 255, message = "编码长度不能超过255")
    private String jbrName;
    /**
     * 招标时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "zb_date", typeHandler = DateTypeHandler.class)
    @ApiModelProperty("招标时间")
    private Date zbDate;
    /**
     * 受理时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "project_sl_date", typeHandler = DateTypeHandler.class)
    @ApiModelProperty("受理时间")
    private Date projectSlDate;
    /**
     * 定标时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "db_date", typeHandler = DateTypeHandler.class)
    @ApiModelProperty("定标时间")
    private Date dbDate;
    /**
     * 招标方式
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("招标方式")
    @Length(max = 255, message = "编码长度不能超过255")
    private String zhaobiaofangshi;
    /**
     * 所属阶段
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("所属阶段")
    @Length(max = 255, message = "编码长度不能超过255")
    private String zbStage;
    /**
     * 评标方法
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("评标方法")
    @Length(max = 255, message = "编码长度不能超过255")
    private String definemethod;
    /**
     * 是否纸质版评标
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("是否纸质版评标")
    @Length(max = 255, message = "编码长度不能超过255")
    private String isusewebztb;
    /**
     * 是否框架协议
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("是否框架协议")
    @Length(max = 255, message = "编码长度不能超过255")
    private String isframe;
    /**
     * 合同生效时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "contract_startdate", typeHandler = DateTypeHandler.class)
    @ApiModelProperty("合同生效时间")
    private Date contractStartdate;
    /**
     * 合同到期时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "contract_closedate", typeHandler = DateTypeHandler.class)
    @ApiModelProperty("合同到期时间")
    private Date contractClosedate;
    /**
     * 是否两步法招标
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("是否两步法招标")
    @Length(max = 255, message = "编码长度不能超过255")
    private String isneedsecondenvelope;
    /**
     * 异常情况说明
     */
    @Size(max = 5000, message = "编码长度不能超过5000")
    @ApiModelProperty("异常情况说明")
    @Length(max = 5000, message = "编码长度不能超过5,000")
    private String yichangreason;
    /**
     * 拟处理结果
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @ApiModelProperty("拟处理结果")
    @Length(max = 128, message = "编码长度不能超过128")
    private String todoResult;
    /**
     * 预算金额
     */
    @Size(max = 50, message = "编码长度不能超过50")
    @ApiModelProperty("预算金额")
    @Length(max = 50, message = "编码长度不能超过50")
    private String touzigusuan;
    /**
     * 预算金额（折合人民币）
     */
    @ApiModelProperty("预算金额（折合人民币）")
    private BigDecimal touzigusuanzh;
    /**
     * 预算金额(币种)
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @ApiModelProperty("预算金额(币种)")
    @Length(max = 128, message = "编码长度不能超过128")
    private String currency;
    /**
     * 中标金额
     */
    @Size(max = 50, message = "编码长度不能超过50")
    @ApiModelProperty("中标金额")
    @Length(max = 50, message = "编码长度不能超过50")
    private String spZhongbiaomoney;
    /**
     * 中标金额（折合人民币）
     */
    @ApiModelProperty("中标金额（折合人民币）")
    private BigDecimal spZhongbiaomoneyzh;
    /**
     * 中标金额（币种）
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @ApiModelProperty("中标金额（币种）")
    @Length(max = 128, message = "编码长度不能超过128")
    private String maincurrency;
    /**
     * 汇率
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @ApiModelProperty("汇率")
    @Length(max = 128, message = "编码长度不能超过128")
    private String yusuanrate;
    /**
     * 是否出差至地方交易中心完成评审工作的标
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @ApiModelProperty("是否出差至地方交易中心完成评审工作的标")
    @Length(max = 128, message = "编码长度不能超过128")
    private String isTravel;
    /**
     * 是否必联网操作
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("是否必联网操作")
    @Length(max = 255, message = "编码长度不能超过255")
    private String isOnline;
    /**
     * 是否前往至外地项目单位完成评审工作的标
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @ApiModelProperty("是否前往至外地项目单位完成评审工作的标")
    @Length(max = 128, message = "编码长度不能超过128")
    private String zhiwaidi;
    /**
     * 是否集团公司重点项目（列入集团公司重点项目清单）或1亿元（含）以上的标
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @ApiModelProperty("是否集团公司重点项目（列入集团公司重点项目清单）或1亿元（含）以上的标")
    @Length(max = 128, message = "编码长度不能超过128")
    private String zhongdianxm;
    /**
     * 大于一年的框架协议项目是否已收取第2次或以上费用
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @ApiModelProperty("大于一年的框架协议项目是否已收取第2次或以上费用")
    @Length(max = 128, message = "编码长度不能超过128")
    private String greaterthan;
    /**
     * 投标供应商数
     */
    @ApiModelProperty("投标供应商数")
    private Integer tbgysNum;
    /**
     * 公开资格预审后直接进行下一步采办方式完成招标
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("公开资格预审后直接进行下一步采办方式完成招标")
    @Length(max = 255, message = "编码长度不能超过255")
    private String nextPurchase;
    /**
     * 节资率
     */
    @Size(max = 64, message = "编码长度不能超过64")
    @ApiModelProperty("节资率")
    @Length(max = 64, message = "编码长度不能超过64")
    private String jzl;
    /**
     * 天数（受理时间与定标时间的差值）
     */
    @Size(max = 128, message = "编码长度不能超过128")
    @ApiModelProperty("天数（受理时间与定标时间的差值）")
    @Length(max = 128, message = "编码长度不能超过128")
    private String tianshu;
    /**
     * 与目标差额
     */
    @ApiModelProperty("与目标差额")
    private Integer mbchae;
    /**
     * 违法、违纪（次数）
     */
    @ApiModelProperty("违法、违纪（次数）")
    private Integer weijiNum;
    /**
     * 自身原因导致的异议或投诉（次数）
     */
    @ApiModelProperty("自身原因导致的异议或投诉（次数）")
    private Integer tousuNum;
    /**
     * 评标纪律问题（次数）
     */
    @ApiModelProperty("评标纪律问题（次数）")
    private Integer jilvNum;
    /**
     * 招标质量（扣分数）
     */
    @ApiModelProperty("招标质量（扣分数）")
    private Integer zhiliangNum;
    /**
     * 未按时退还保证金（次数）
     */
    @ApiModelProperty("未按时退还保证金（次数）")
    private Integer tuihuanNum;
    /**
     * 未按时收取中标服务费次数（按标段）
     */
    @ApiModelProperty("未按时收取中标服务费次数（按标段）")
    private Integer fwfNum;
    /**
     * 中标通知书发出时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "zb_send_date", typeHandler = DateTypeHandler.class)
    @ApiModelProperty("中标通知书发出时间")
    private Date zbSendDate;
    /**
     * 所属处室
     */
    @Size(max = 64, message = "编码长度不能超过64")
    @ApiModelProperty("所属处室")
    @Length(max = 64, message = "编码长度不能超过64")
    private String department;
    /**
     * 所属单位
     */
    @Size(max = 64, message = "编码长度不能超过64")
    @ApiModelProperty("所属单位")
    @Length(max = 64, message = "编码长度不能超过64")
    private String company;
    /**
     * 项目单位推荐招标经理并由招标经理承接此项目
     */
    @Size(max = 255, message = "编码长度不能超过255")
    @ApiModelProperty("项目单位推荐招标经理并由招标经理承接此项目")
    @Length(max = 255, message = "编码长度不能超过255")
    private String recoCompany;
    /**
     * 项目单位推荐招标经理并由招标经理承接此项目时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "reco_company_date", typeHandler = DateTypeHandler.class)
    @ApiModelProperty("项目单位推荐招标经理并由招标经理承接此项目时间")
    private Date recoCompanyDate;
    /**
     * 疫情期间极端情况下完成的招标项目（标段数）
     */
    @ApiModelProperty("疫情期间极端情况下完成的招标项目（标段数）")
    private String tenderProject;

    @TableField(exist = false)
    private List<JixiaoInfo> jixiaoInfoList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public List<JixiaoInfo> getJixiaoInfoList() {
        return jixiaoInfoList;
    }

    public String getTenderProject() {
        return tenderProject;
    }

    public void setTenderProject(String tenderProject) {
        this.tenderProject = tenderProject;
    }

    public void setJixiaoInfoList(List<JixiaoInfo> jixiaoInfoList) {
        this.jixiaoInfoList = jixiaoInfoList;
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

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectno() {
        return projectno;
    }

    public void setProjectno(String projectno) {
        this.projectno = projectno;
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

    public Date getZbDate() {
        return zbDate;
    }

    public void setZbDate(Date zbDate) {
        this.zbDate = zbDate;
    }

    public Date getProjectSlDate() {
        return projectSlDate;
    }

    public void setProjectSlDate(Date projectSlDate) {
        this.projectSlDate = projectSlDate;
    }

    public Date getDbDate() {
        return dbDate;
    }

    public void setDbDate(Date dbDate) {
        this.dbDate = dbDate;
    }

    public String getZhaobiaofangshi() {
        return zhaobiaofangshi;
    }

    public void setZhaobiaofangshi(String zhaobiaofangshi) {
        this.zhaobiaofangshi = zhaobiaofangshi;
    }

    public String getZbStage() {
        return zbStage;
    }

    public void setZbStage(String zbStage) {
        this.zbStage = zbStage;
    }

    public String getDefinemethod() {
        return definemethod;
    }

    public void setDefinemethod(String definemethod) {
        this.definemethod = definemethod;
    }

    public String getIsusewebztb() {
        return isusewebztb;
    }

    public void setIsusewebztb(String isusewebztb) {
        this.isusewebztb = isusewebztb;
    }

    public String getIsframe() {
        return isframe;
    }

    public void setIsframe(String isframe) {
        this.isframe = isframe;
    }

    public Date getContractStartdate() {
        return contractStartdate;
    }

    public void setContractStartdate(Date contractStartdate) {
        this.contractStartdate = contractStartdate;
    }

    public Date getContractClosedate() {
        return contractClosedate;
    }

    public void setContractClosedate(Date contractClosedate) {
        this.contractClosedate = contractClosedate;
    }

    public String getIsneedsecondenvelope() {
        return isneedsecondenvelope;
    }

    public void setIsneedsecondenvelope(String isneedsecondenvelope) {
        this.isneedsecondenvelope = isneedsecondenvelope;
    }

    public String getYichangreason() {
        return yichangreason;
    }

    public void setYichangreason(String yichangreason) {
        this.yichangreason = yichangreason;
    }

    public String getTodoResult() {
        return todoResult;
    }

    public void setTodoResult(String todoResult) {
        this.todoResult = todoResult;
    }

    public String getTouzigusuan() {
        return touzigusuan;
    }

    public void setTouzigusuan(String touzigusuan) {
        this.touzigusuan = touzigusuan;
    }

    public BigDecimal getTouzigusuanzh() {
        return touzigusuanzh;
    }

    public void setTouzigusuanzh(BigDecimal touzigusuanzh) {
        this.touzigusuanzh = touzigusuanzh;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSpZhongbiaomoney() {
        return spZhongbiaomoney;
    }

    public void setSpZhongbiaomoney(String spZhongbiaomoney) {
        this.spZhongbiaomoney = spZhongbiaomoney;
    }

    public BigDecimal getSpZhongbiaomoneyzh() {
        return spZhongbiaomoneyzh;
    }

    public void setSpZhongbiaomoneyzh(BigDecimal spZhongbiaomoneyzh) {
        this.spZhongbiaomoneyzh = spZhongbiaomoneyzh;
    }

    public String getMaincurrency() {
        return maincurrency;
    }

    public void setMaincurrency(String maincurrency) {
        this.maincurrency = maincurrency;
    }

    public String getYusuanrate() {
        return yusuanrate;
    }

    public void setYusuanrate(String yusuanrate) {
        this.yusuanrate = yusuanrate;
    }

    public String getIsTravel() {
        return isTravel;
    }

    public void setIsTravel(String isTravel) {
        this.isTravel = isTravel;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getZhiwaidi() {
        return zhiwaidi;
    }

    public void setZhiwaidi(String zhiwaidi) {
        this.zhiwaidi = zhiwaidi;
    }

    public String getZhongdianxm() {
        return zhongdianxm;
    }

    public void setZhongdianxm(String zhongdianxm) {
        this.zhongdianxm = zhongdianxm;
    }

    public String getGreaterthan() {
        return greaterthan;
    }

    public void setGreaterthan(String greaterthan) {
        this.greaterthan = greaterthan;
    }

    public Integer getTbgysNum() {
        return tbgysNum;
    }

    public void setTbgysNum(Integer tbgysNum) {
        this.tbgysNum = tbgysNum;
    }

    public String getNextPurchase() {
        return nextPurchase;
    }

    public void setNextPurchase(String nextPurchase) {
        this.nextPurchase = nextPurchase;
    }

    public String getJzl() {
        return jzl;
    }

    public void setJzl(String jzl) {
        this.jzl = jzl;
    }

    public String getTianshu() {
        return tianshu;
    }

    public void setTianshu(String tianshu) {
        this.tianshu = tianshu;
    }

    public Integer getMbchae() {
        return mbchae;
    }

    public void setMbchae(Integer mbchae) {
        this.mbchae = mbchae;
    }

    public Integer getWeijiNum() {
        return weijiNum;
    }

    public void setWeijiNum(Integer weijiNum) {
        this.weijiNum = weijiNum;
    }

    public Integer getTousuNum() {
        return tousuNum;
    }

    public void setTousuNum(Integer tousuNum) {
        this.tousuNum = tousuNum;
    }

    public Integer getJilvNum() {
        return jilvNum;
    }

    public void setJilvNum(Integer jilvNum) {
        this.jilvNum = jilvNum;
    }

    public Integer getZhiliangNum() {
        return zhiliangNum;
    }

    public void setZhiliangNum(Integer zhiliangNum) {
        this.zhiliangNum = zhiliangNum;
    }

    public Integer getTuihuanNum() {
        return tuihuanNum;
    }

    public void setTuihuanNum(Integer tuihuanNum) {
        this.tuihuanNum = tuihuanNum;
    }

    public Integer getFwfNum() {
        return fwfNum;
    }

    public void setFwfNum(Integer fwfNum) {
        this.fwfNum = fwfNum;
    }

    public Date getZbSendDate() {
        return zbSendDate;
    }

    public void setZbSendDate(Date zbSendDate) {
        this.zbSendDate = zbSendDate;
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

    public String getRecoCompany() {
        return recoCompany;
    }

    public void setRecoCompany(String recoCompany) {
        this.recoCompany = recoCompany;
    }

    public Date getRecoCompanyDate() {
        return recoCompanyDate;
    }

    public void setRecoCompanyDate(Date recoCompanyDate) {
        this.recoCompanyDate = recoCompanyDate;
    }


}
