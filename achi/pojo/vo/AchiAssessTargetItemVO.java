package com.deta.achi.pojo.vo;


import java.util.Date;

/**
 * @author zzs
 * @version 1.0.0
 * @description 考核指标 VO
 * @date 2023-01-06 18:50
 */
public class AchiAssessTargetItemVO {

    private String id;
    // 考核指标名称
    private String assessTargetItemName;
    // 备注
    private String notes;
    // 分值/系数
    private String score;
    // 积分类型
    private String scoreType;
    // 积分类型id
    private String scoreTypeId;
    // 累加方式
    private String addScoreType;
    // 累加方式id
    private String addScoreTypeId;
    // 加分位置
    private String scorePlace;
    // 数据文件
    private String dateFile;
    // 数据文件id
    private String dateFileId;
    // 数据文件 关联字段
    private String dateFileRelField;
    // 数据文件 关联字段id
    private String dateFileRelFieldId;
    // 考核指标类别id
    private String assessTargetId;

    private Date gmtCreate;    //创建时间
    private Date  gmtModified;  //修改时间

    private String fullAssessTargetId;

    private String fullAssessTargetName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssessTargetItemName() {
        return assessTargetItemName;
    }

    public void setAssessTargetItemName(String assessTargetItemName) {
        this.assessTargetItemName = assessTargetItemName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getScoreTypeId() {
        return scoreTypeId;
    }

    public void setScoreTypeId(String scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }

    public String getAddScoreType() {
        return addScoreType;
    }

    public void setAddScoreType(String addScoreType) {
        this.addScoreType = addScoreType;
    }

    public String getAddScoreTypeId() {
        return addScoreTypeId;
    }

    public void setAddScoreTypeId(String addScoreTypeId) {
        this.addScoreTypeId = addScoreTypeId;
    }

    public String getScorePlace() {
        return scorePlace;
    }

    public void setScorePlace(String scorePlace) {
        this.scorePlace = scorePlace;
    }

    public String getDateFile() {
        return dateFile;
    }

    public void setDateFile(String dateFile) {
        this.dateFile = dateFile;
    }

    public String getDateFileId() {
        return dateFileId;
    }

    public void setDateFileId(String dateFileId) {
        this.dateFileId = dateFileId;
    }

    public String getDateFileRelField() {
        return dateFileRelField;
    }

    public void setDateFileRelField(String dateFileRelField) {
        this.dateFileRelField = dateFileRelField;
    }

    public String getDateFileRelFieldId() {
        return dateFileRelFieldId;
    }

    public void setDateFileRelFieldId(String dateFileRelFieldId) {
        this.dateFileRelFieldId = dateFileRelFieldId;
    }

    public String getAssessTargetId() {
        return assessTargetId;
    }

    public void setAssessTargetId(String assessTargetId) {
        this.assessTargetId = assessTargetId;
    }

    public AchiAssessTargetItemVO() {
    }

    public String getFullAssessTargetId() {
        return fullAssessTargetId;
    }

    public void setFullAssessTargetId(String fullAssessTargetId) {
        this.fullAssessTargetId = fullAssessTargetId;
    }

    public String getFullAssessTargetName() {
        return fullAssessTargetName;
    }

    public void setFullAssessTargetName(String fullAssessTargetName) {
        this.fullAssessTargetName = fullAssessTargetName;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
