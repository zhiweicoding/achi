package com.deta.achi.pojo;

public enum ResultEnum {

    FAIL(500, "服务器发生错误，无法判断发出的请求是否成功"),
    IMPORT_IS_EMPTY(400, "导入文件为空"),

    PARAMS_IS_EMPTY(400, "缺少必要的传入参数或传入参数格式不对。"),
    SELECT_ERROR(500, "查询信息异常"),
    JSON_CONVERT_ERROR(500, "格式转换异常"),

    // 用户信息相关
    NOTIFY_USER_ERROR(1001, "通知用户失败"),
    USER_IS_EMATY(1002, "用户信息不能为空"),
    NOTIFY_OTHER_APP_ERROR(1003, "回调第三方业务系统异常"),
    GET_USER_ERROR(1004, "获取节点用户信息异常"),



    // 审批相关
    APPROVE_INFO_NONE(2001, "当前审批信息不存在"),
    PASS_RATE_IS_BLANK(2002, "通过率为空，请检查"),
    APPROVE_USER_ERROR(2003, "审批人不存在，无法继续创建，建议检查模板后，重试"),
    NODE_CONFIG_ABSENT(2004, "节点信息不存在，请检查"),
    TEMPLATE_IS_ERROR(2005, "审批模板已停用，请检查"),
    TEMPLATE_NOT_EXISTS(2006, "审批模板不存在"),

    // ....
    CONVER_VO_ERROE(100500, "转换失败"),
    // 参数异常
    CHECK_PARAMS_FIELD(2023500, "参数异常"),
    REMOVE_SUCCESS(200, "删除成功！"),
    REMOVE_FIELD(2023500, "删除失败！"),
    REPORT_NOT_EXISTS(2023500, "报表不存在！"),
    CREATE_REPORT_SUCCESS(200, "创建成功！"),
    CREATE_WORKFLOW_FAIL(600, "流程创建失败！"),
    REQUEST_OTHER_SERVER_FAIL(500, "请求其他服务失败，请联系管理员！"),
    WORKFLOW_CALLBACK_FAIL(600, "流程回调操作失败！"),
    QUERY_NOTIFY_FAIL(600, "查询待办失败！"),
    SUCCESS(200, "操作成功！"),

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
    REMOVE_FAIL(400500, "删除失败"),
    REMOVE_FAIL_EXISTS_SON(400600, "删除失败,此分类下有子分类存在"),
    REMOVE_FAIL_EXISTS_BRO(400600, "删除失败,此分类下有指标存在"),
    REMOVE_FAIL_NOT_EXISTS(400600, "删除失败,此分类不存在"),
    SUBMIT_TIMEOUT(200, "上报超时！请您在10号之前报送"),
    SUBMIT_FILE(200, "上报失败！请您检查其他数据的审核情况"),
    SUBMIT_SUCCESS(200, "上报成功！"),
    IMPORT_FILE_FAIL(400400, "导入失败，请检查文件！"),
    DATE_CHECK_FAIL(200, "信息校验失败！请您检查数据"),
    COLLECT_DATA_IS_EMPTY(200, "获取数据为空！"),
    CHECK_FILE_FAIL(300500, "文件校验失败，请正确上传文件！"),
    END(-1, "param is invalid");

    private Integer code;

    private String message;

    ResultEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer code(){
        return this.code;
    }

    public String message(){
        return this.message;
    }

}
