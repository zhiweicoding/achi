package com.deta.achi.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.List;

/**
 * @Created by zhiwei on 2022/5/3.
 */
public class PageAssistantBean<T> implements Serializable {

    @TableField(exist = false)
    private int everyPageCount;
    @TableField(exist = false)
    private int whichPageNum;
    @TableField(exist = false)
    private int allPageCount;
    @TableField(exist = false)
    private boolean hasNext;
    @TableField(exist = false)
    private boolean hasBefore;
    @TableField(exist = false)
    private transient List<T> tList;
    @TableField(exist = false)
    private transient List<T> tAllList;

    public int getEveryPageCount() {
        return everyPageCount;
    }

    public void setEveryPageCount(int everyPageCount) {
        this.everyPageCount = everyPageCount;
    }

    public int getWhichPageNum() {
        return whichPageNum;
    }

    public void setWhichPageNum(int whichPageNum) {
        this.whichPageNum = whichPageNum;
    }

    public int getAllPageCount() {
        return allPageCount;
    }

    public void setAllPageCount(int allPageCount) {
        this.allPageCount = allPageCount;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasBefore() {
        return hasBefore;
    }

    public void setHasBefore(boolean hasBefore) {
        this.hasBefore = hasBefore;
    }

    public List<T> getTList() {
        return tList;
    }

    public void setTList(List<T> tList) {
        this.tList = tList;
    }

    public List<T> getTAllList() {
        return tAllList;
    }

    public void setTAllList(List<T> tAllList) {
        this.tAllList = tAllList;
    }

}
