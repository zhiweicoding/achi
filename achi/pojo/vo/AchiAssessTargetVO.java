package com.deta.achi.pojo.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zzs
 * @version 1.0.0
 * @description vo 对象
 * @date 2023-01-06 11:23
 */
public class AchiAssessTargetVO implements Serializable {
    private String id;
    private String accessTargetName;
    private String parentId;
    private List<AchiAssessTargetVO> accessTargetList;


    @Override
    public String toString() {
        return "AchiAssessTargetVO{" +
                "id='" + id + '\'' +
                ", accessTargetName='" + accessTargetName + '\'' +
                ", parentId='" + parentId + '\'' +
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

    public List<AchiAssessTargetVO> getAccessTargetList() {
        return accessTargetList;
    }

    public void setAccessTargetList(List<AchiAssessTargetVO> accessTargetList) {
        this.accessTargetList = accessTargetList;
    }
}
