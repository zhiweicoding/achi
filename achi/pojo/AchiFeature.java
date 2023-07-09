package com.deta.achi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * @TableName achi_feature
 */
@TableName(value = "achi_feature")
public class AchiFeature implements Serializable {

    public static final String TYPE_NAME = "未命名";
    /**
     *
     */
    @TableId(value = "id")
    private String id;

    /**
     *
     */
    @TableField(value = "name")
    private String name;

    /**
     *
     */
    @TableField(value = "status")
    private Integer status;

    /**
     *
     */
    @TableField(value = "ops_time")
    private Long opsTime;

    /**
     *
     */
    @TableField(value = "ops_user")
    private String opsUser;

    /**
     *
     */
    @TableField(value = "type_id")
    private String typeId;

    /**
     *
     */
    @TableField(value = "level")
    private Integer level;

    @TableField(exist = false)
    private List<AchiFeature> achiFeatures;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public List<AchiFeature> getAchiFeatures() {
        return achiFeatures;
    }

    public void setAchiFeatures(List<AchiFeature> achiFeatures) {
        this.achiFeatures = achiFeatures;
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
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     */
    public Long getOpsTime() {
        return opsTime;
    }

    /**
     *
     */
    public void setOpsTime(Long opsTime) {
        this.opsTime = opsTime;
    }

    /**
     *
     */
    public String getOpsUser() {
        return opsUser;
    }

    /**
     *
     */
    public void setOpsUser(String opsUser) {
        this.opsUser = opsUser;
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
    public Integer getLevel() {
        return level;
    }

    /**
     *
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
}