package com.deta.achi.pojo;

/**
 * @author zzs
 * @version 1.0.0
 * @description 返回code
 * @date 2023-01-06 13:05
 */
public enum AchiResultCode {
    // 普通操作
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    PARAM_CHECK_FAIL(400, "参数校验失败"),

    // 查询
    QUERY_SUCCESS(100200, "查询成功"),
    QUERY_FAIL(100500, "查询失败"),

    // 添加
    INSERT_SUCCESS(200200, "添加成功"),
    INSERT_FAIL(200500, "添加失败"),
    // 修改
    UPDATE_SUCCESS(300200, "修改成功"),
    UPDATE_FAIL(300500, "修改失败"),
    // 删除
    REMOVE_SUCCESS(400200, "删除成功"),
    REMOVE_FAIL(400500, "删除失败"),
    REMOVE_FAIL_EXISTS_SON(400600, "删除失败,此分类下有子分类存在"),
    REMOVE_FAIL_EXISTS_BRO(400600, "删除失败,此分类下有指标存在"),
    REMOVE_FAIL_NOT_EXISTS(400600, "删除失败,此分类不存在"),
    ;
    private final int code;
    private final String msg;

    AchiResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
