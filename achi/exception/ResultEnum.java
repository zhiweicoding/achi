package com.deta.achi.exception;

/**
 * @author wangminghao
 */

public enum ResultEnum {

    SUCCESS_MESSAGE(200, "添加成功"),
    ERROR_MESSAGE(400, "添加失败：请联系管理员"),

    FAIL(500, "服务器发生错误，无法判断发出的请求是否成功"),
    IMPORT_IS_EMPTY(400, "导入文件为空"),
    PARAMS_IS_EMPTY(400, "缺少必要的传入参数或传入参数格式不对。"),
    SELECT_ERROR(500, "查询信息异常"),
    JSON_CONVERT_ERROR(500, "格式转换异常"),

    // 用户信息相关
    USER_IS_EMATY(1002, "用户信息不能为空"),
    SERVICE_BUSY_ERROR(1000, "服务器正在繁忙，请稍后再试哦~"),

    REQUEST_PARAMS_FAIL(1001, "参数错误"),

    USER_NOT_LOGIN(1002, "用户未登录，请重新登录"),

    USER_HAS_EXIST_LOGIN(1003, "用户已经存在，请检查！"),

    USER_CODE_NOT_EXIST(1004, "用户编码不存在，请检查！"),

    END(-1, "param is invalid");

    private Integer code;

    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
