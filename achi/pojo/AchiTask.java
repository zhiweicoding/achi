package com.deta.achi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName achi_task
 */
@TableName(value ="achi_task")
public class AchiTask implements Serializable {
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
     * 1草稿2已提交3已结束4已评分
     */
    @TableField(value = "achi_status")
    private Integer achiStatus;

    /**
     * 无效（1）未评分（2）已评分（3）已提交（4）已结束（5）
     */
    @TableField(value = "manager_status")
    private Integer managerStatus;

    /**
     * 无效（1）未评分（2）已评分（3）已提交（4）已结束（5）
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

    /**
     * 分类Id
     */
    @TableField(value = "type_id")
    private String typeId;

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
     * 类型名
     */
    @TableField(value = "type_name")
    private String typeName;


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

    /**
     * id
     */
    public String getId() {
        return id;
    }

    /**
     * id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 任务名称
     */
    public String getAchiName() {
        return achiName;
    }

    /**
     * 任务名称
     */
    public void setAchiName(String achiName) {
        this.achiName = achiName;
    }

    /**
     * 考核人姓名
     */
    public String getWorkName() {
        return workName;
    }

    /**
     * 考核人姓名
     */
    public void setWorkName(String workName) {
        this.workName = workName;
    }

    /**
     * 考核人工号
     */
    public String getWorkId() {
        return workId;
    }

    /**
     * 考核人工号
     */
    public void setWorkId(String workId) {
        this.workId = workId;
    }

    /**
     * 所属部门
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 所属部门
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 考核开始时间
     */
    public Long getAchiStart() {
        return achiStart;
    }

    /**
     * 考核开始时间
     */
    public void setAchiStart(Long achiStart) {
        this.achiStart = achiStart;
    }

    /**
     * 考核结束时间
     */
    public Long getAchiStop() {
        return achiStop;
    }

    /**
     * 考核结束时间
     */
    public void setAchiStop(Long achiStop) {
        this.achiStop = achiStop;
    }

    /**
     * 1草稿2已提交3已结束
     */
    public Integer getAchiStatus() {
        return achiStatus;
    }

    /**
     * 1草稿2已提交3已结束
     */
    public void setAchiStatus(Integer achiStatus) {
        this.achiStatus = achiStatus;
    }

    /**
     * 0无效1未评分2已评分3已提交4已结束
     */
    public Integer getManagerStatus() {
        return managerStatus;
    }

    /**
     * 0无效1未评分2已评分3已提交4已结束
     */
    public void setManagerStatus(Integer managerStatus) {
        this.managerStatus = managerStatus;
    }

    /**
     * 0无效1未评分2已评分3已提交4已结束
     */
    public Integer getDirectorStatus() {
        return directorStatus;
    }

    /**
     * 0无效1未评分2已评分3已提交4已结束
     */
    public void setDirectorStatus(Integer directorStatus) {
        this.directorStatus = directorStatus;
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
     * 模板id
     */
    public String getAchiId() {
        return achiId;
    }

    /**
     * 模板id
     */
    public void setAchiId(String achiId) {
        this.achiId = achiId;
    }

    /**
     * 单位
     */
    public String getCompany() {
        return company;
    }

    /**
     * 单位
     */
    public void setCompany(String company) {
        this.company = company;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    /**
     * 
     */
    public String getAutoScore() {
        return autoScore;
    }

    /**
     * 
     */
    public void setAutoScore(String autoScore) {
        this.autoScore = autoScore;
    }

    /**
     * 处室主任评分
     */
    public String getDirectorScore() {
        return directorScore;
    }

    /**
     * 处室主任评分
     */
    public void setDirectorScore(String directorScore) {
        this.directorScore = directorScore;
    }

    /**
     * 部门经理评分
     */
    public String getMangerScore() {
        return mangerScore;
    }

    /**
     * 部门经理评分
     */
    public void setMangerScore(String mangerScore) {
        this.mangerScore = mangerScore;
    }

    /**
     * 类型名
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 类型名
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}