package com.deta.achi.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zzs
 * @version 1.0.0
 * @description 绩效考核分类导航
 * @date 2023-01-05 19:14
 */
@TableName("achi_assess_target")
public class AchiAssessTarget {
    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    @TableField("id")
    private String id;
    // 分类名称
    @TableField("assess_target_name")
    private String accessTargetName;
    // 父级 id
    @TableField("parent_id")
    private String parentId;
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    // 逻辑删除
    @TableLogic(value = "0", delval = "-1")
    private String isDeleted;
    // 子分类
    @TableField(exist = false)
    private List<AchiAssessTarget> accessTargetList = new ArrayList<>();

    @Override
    public String toString() {
        return "AchiAssessTarget{" +
                "id='" + id + '\'' +
                ", accessTargetName='" + accessTargetName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                ", accessTargetList=" + accessTargetList +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessTargetName() {
        return accessTargetName;
    }

    public void setAccessTargetName(String accessTargetName) {
        this.accessTargetName = accessTargetName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<AchiAssessTarget> getAccessTargetList() {
        return accessTargetList;
    }

    public void setAccessTargetList(List<AchiAssessTarget> accessTargetList) {
        this.accessTargetList = accessTargetList;
    }

    public AchiAssessTarget() {
    }

    public AchiAssessTarget(String id, String accessTargetName, String parentId, String isDeleted, List<AchiAssessTarget> accessTargetList) {
        this.id = id;
        this.accessTargetName = accessTargetName;
        this.parentId = parentId;
        this.isDeleted = isDeleted;
        this.accessTargetList = accessTargetList;
    }
}
