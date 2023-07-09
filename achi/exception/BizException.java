package com.deta.achi.exception;


import com.deta.achi.pojo.ResultEnum;

/**
 * @author wangminghao
 */
public class BizException extends RuntimeException{

    private static final long serialVersionUID = -6233506444097954612L;

    /**

     * 错误码

     */

    private int errorCode;

    /**

     * 错误信息

     */

    private String errorMsg;

    /**

     * 返回错误对象信息

     */

    private transient Object data;

    public BizException() {

        super();

    }

    public BizException(String errorMsg) {

        super(errorMsg);

        this.errorMsg = errorMsg;

    }

    public BizException(int errorCode, String errorMsg) {

        super(String.valueOf(errorCode));

        this.errorCode = errorCode;

        this.errorMsg = errorMsg;

    }

    public BizException(int errorCode, String errorMsg, Throwable cause) {

        super(String.valueOf(errorCode), cause);

        this.errorCode = errorCode;

        this.errorMsg = errorMsg;

    }

    public BizException(ResultEnum resultEnum) {

        super(String.valueOf(resultEnum.code()));

        this.errorCode = resultEnum.code();

        this.errorMsg = resultEnum.message();

    }

    public BizException(ResultEnum resultEnum, Throwable cause) {

        super(String.valueOf(resultEnum.code()), cause);

        this.errorCode = resultEnum.code();

        this.errorMsg = resultEnum.message();

    }

    public BizException(ResultEnum resultEnum, Object data) {

        this.errorCode = resultEnum.code();

        this.errorMsg = resultEnum.message();

        this.data = data;

    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
