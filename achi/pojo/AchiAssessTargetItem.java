package com.deta.achi.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zzs
 * @version 1.0.0
 * @description 绩效指标管理 item
 * @date 2023-01-06 16:15
 */
@TableName("achi_assess_target_item")
public class AchiAssessTargetItem {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    @TableField("assess_target_item_name")
    // 考核指标名称
    private String assessTargetItemName;

    // 备注
    @TableField("notes")
    private String notes;
    // 分值/系数
    @TableField("score")
    private String score;
    // 积分类型
    @TableField("score_type")
    private String scoreType;
    // 积分类型id
    @TableField("score_type_id")
    private String scoreTypeId;
    // 累加方式
    @TableField("add_socre_type")
    private String addScoreType;
    // 累加方式id
    @TableField("add_socre_type_id")
    private String addScoreTypeId;

    // 加分位置
    @TableField("socre_place")
    private String scorePlace;
    // 数据文件
    @TableField("data_file")
    private String dateFile;
    // 数据文件id
    @TableField("data_file_id")
    private String dateFileId;
    // 数据文件 关联字段
    @TableField("data_file_rel_field")
    private String dateFileRelField;
    // 数据文件 关联字段id
    @TableField("data_file_rel_field_id")
    private String dateFileRelFieldId;
    // 考核指标类别id
    @TableField("assess_target_id")
    private String assessTargetId;

    @TableField(value = "full_assess_target_id", select = false)
    private String fullAssessTargetId;

    @TableField(value = "full_assess_target_name", select = false)
    private String fullAssessTargetName;
    @TableField("is_deleted")
    @TableLogic(value = "0", delval = "-1")
    private String isDeleted;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;    //创建时间
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  gmtModified;  //修改时间




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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
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

    public AchiAssessTargetItem() {
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
}
