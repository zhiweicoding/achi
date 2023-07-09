package com.deta.achi.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.simpleframework.xml.Default;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.Serializable;

/**
 * @author wangminghao
 * @TableName achi_template
 */
@TableName(value = "achi_template")
public class AchiTemplate implements Serializable {
    /**
     *
     */
    @TableId(value = "achi_id")
    private String achiId;

    /**
     *
     */
    @TableField(value = "achi_name")
    private String achiName;

    /**
     *
     */
    @TableField(value = "achi_start")
    private Long achiStart;

    /**
     *
     */
    @TableField(value = "achi_stop")
    private Long achiStop;

    /**
     *
     */
    @TableField(value = "type_id")
    private String typeId;

    /**
     *
     */
    @TableField(value = "type")
    private String type;

    /**
     *
     */
    @TableField(value = "create_user")
    private String createUser;

    /**
     *
     */
    @TableField(value = "create_time")
    private Long createTime;

    /**
     *
     */
    @TableField(value = "update_time")
    private Long updateTime;

    /**
     *
     */
    @TableField(value = "update_user")
    private String updateUser;

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
     * 1草稿2考核中3已结束
     */
    @TableField(value = "achi_status")
    private Integer achiStatus;

    /**
     * 1有效0无效
     */
    @TableField(value = "status")
    private Integer status;
    @TableField(exist = false)
    private Integer pageNum;
    @TableField(exist = false)
    private Integer pageSize;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public String getAchiId() {
        return achiId;
    }

    /**
     *
     */
    public void setAchiId(String achiId) {
        this.achiId = achiId;
    }

    /**
     *
     */
    public String getAchiName() {
        return achiName;
    }

    /**
     *
     */
    public void setAchiName(String achiName) {
        this.achiName = achiName;
    }

    /**
     *
     */
    public Long getAchiStart() {
        return achiStart;
    }

    /**
     *
     */
    public void setAchiStart(Long achiStart) {
        this.achiStart = achiStart;
    }

    /**
     *
     */
    public Long getAchiStop() {
        return achiStop;
    }

    /**
     *
     */
    public void setAchiStop(Long achiStop) {
        this.achiStop = achiStop;
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
    public String getType() {
        return type;
    }

    /**
     *
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     *
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     *
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     *
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     *
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     *
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     *
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     *
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
     * 1草稿2考核中3已结束
     */
    public Integer getAchiStatus() {
        return achiStatus;
    }

    /**
     * 1草稿2考核中3已结束
     */
    public void setAchiStatus(Integer achiStatus) {
        this.achiStatus = achiStatus;
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
}