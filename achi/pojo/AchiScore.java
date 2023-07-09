package com.deta.achi.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.deta.entity.PageParam;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author lijialin
 * @date 2022/8/9 17:27
 */
public class AchiScore extends PageParam {

    /**
     * 主键
     */
    private String id;
    /**
     * 考核名称
     */
    private String achiName;
    /**
     * 考核人姓名
     */
    private String workName;

    /**
     * 考核人工号
     */
    private String workId;
    /**
     * 考核区间
     */
    private String period;
    /**
     * 状态
     */
    private String status;
    /**
     * 自动评分，格式json
     */
    private String autoScore;
    /**
     * 室主任评分，格式json，和自动评分一样
     */
    private String directorScore;
    /**
     * 部门经理评分，格式json，和自动评分一样
     */
    private String mangerScore;
    /**
     * 主键
     */
    private String totalScore;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }
}
