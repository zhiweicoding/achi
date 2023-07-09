package com.deta.achi.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @TableName achi_task
 */
@TableName(value = "achi_task")
public class AchiTaskDTO implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 任务名称
     */
    @TableField(value = "achi_name")
    private String achiName;

    /**
     * 考核人姓名
     */
    @TableField(value = "work_name")
    private String workName;

    /**
     * 考核人工号
     */
    @TableField(value = "work_id")
    private String workId;

    /**
     * 所属部门
     */
    @TableField(value = "department")
    private String department;

    /**
     * 考核开始时间
     */
    @TableField(value = "achi_start")
    private Long achiStart;

    /**
     * 考核结束时间
     */
    @TableField(value = "achi_stop")
    private Long achiStop;

    /**
     * 1草稿2已提交3已结束
     */
    @TableField(value = "achi_status")
    private Integer achiStatus;

    /**
     * 0无效1未评分2已评分3已提交4已结束
     */
    @TableField(value = "manager_status")
    private Integer managerStatus;

    /**
     * 0无效1未评分2已评分3已提交4已结束
     */
    @TableField(value = "director_status")
    private Integer directorStatus;

    /**
     * 1有效0无效
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 模板id
     */
    @TableField(value = "achi_id")
    private String achiId;

    /**
     * 单位
     */
    @TableField(value = "company")
    private String company;


    @TableField(exist = false)
    private String nameOrId;

    @TableField(exist = false)
    private Integer pageNum;


    @TableField(exist = false)
    private Integer pageSize;

    @TableField(exist = false)
    private String sort;

    /**
     * 品类类型：
     * 1-可协议化且当年形成框架协议的品类
     * 2-不适宜协议化的品类
     * 3-协议化且已经形成框架协议的
     * 4-其他
     */
    @TableField(exist = false)
    private String orderType;
    @TableField(exist = false)
    private String xmNameOrId;

    @TableField(value = "type_name")
    private String typeName;
    /**
     * 分类Id
     */
    @TableField(value = "type_id")
    private Long typeId;

    /**
     *
     */
    @TableField(value = "auto_score")
    private String autoScore;

    /**
     * 处室主任评分
     */
    @TableField(value = "director_score")
    private String directorScore;

    /**
     * 部门经理评分
     */
    @TableField(value = "manger_score")
    private String mangerScore;
    /**
     * 导入详情得分
     */
    @TableField(value = "detail_score")
    private String detailScore;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public String getDetailScore() {
        return detailScore;
    }

    public void setDetailScore(String detailScore) {
        this.detailScore = detailScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAchiName() {
        return achiName;
    }

    public void setAchiName(String achiName) {
        this.achiName = achiName;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getAchiStart() {
        return achiStart;
    }

    public void setAchiStart(Long achiStart) {
        this.achiStart = achiStart;
    }

    public Long getAchiStop() {
        return achiStop;
    }

    public void setAchiStop(Long achiStop) {
        this.achiStop = achiStop;
    }

    public Integer getAchiStatus() {
        return achiStatus;
    }

    public void setAchiStatus(Integer achiStatus) {
        this.achiStatus = achiStatus;
    }

    public Integer getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(Integer managerStatus) {
        this.managerStatus = managerStatus;
    }

    public Integer getDirectorStatus() {
        return directorStatus;
    }

    public void setDirectorStatus(Integer directorStatus) {
        this.directorStatus = directorStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAchiId() {
        return achiId;
    }

    public void setAchiId(String achiId) {
        this.achiId = achiId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNameOrId() {
        return nameOrId;
    }

    public void setNameOrId(String nameOrId) {
        this.nameOrId = nameOrId;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getXmNameOrId() {
        return xmNameOrId;
    }

    public void setXmNameOrId(String xmNameOrId) {
        this.xmNameOrId = xmNameOrId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getAutoScore() {
        return autoScore;
    }

    public void setAutoScore(String autoScore) {
        this.autoScore = autoScore;
    }

    public String getDirectorScore() {
        return directorScore;
    }

    public void setDirectorScore(String directorScore) {
        this.directorScore = directorScore;
    }

    public String getMangerScore() {
        return mangerScore;
    }

    public void setMangerScore(String mangerScore) {
        this.mangerScore = mangerScore;
    }
}