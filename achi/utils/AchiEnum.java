package com.deta.achi.utils;

/**
 * @author wangminghao
 */
public enum AchiEnum {
    DELETE_FALSE(1, "未删除"),
    DELETE_TRUE(0, "已删除"),
    LEVEL_ONE(1, "一级菜单"),
    LEVEL_TWO(2, "二级菜单"),
    ZERO(0, "常量0"),
    ONE(1, "常量1"),

    TWO(2, "常量2"),
    THREE(3, "常量3"),

    FOUR(4, "常量4");
    /**
     * 字段编号
     */
    private final Integer code;
    /**
     * 字段含义
     */
    private final String description;

    AchiEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer code() {
        return this.code;
    }

    public String description() {
        return this.description;
    }

}
