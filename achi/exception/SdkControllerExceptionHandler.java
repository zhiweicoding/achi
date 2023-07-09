package com.deta.achi.exception;


import com.coredata.utils.response.ResponseMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @author wangminghao
 */
@RestControllerAdvice(basePackages = {"com.deta"})
@RestController
@Order(2)
public class SdkControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(SdkControllerExceptionHandler.class);

    /**
     * 自定义异常类
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = SdkException.class)
    @ResponseBody
    public ResponseMap bootExceptionHandler(SdkException e, HttpServletRequest req) {
        logger.error("业务异常==========Host： {}， invokes url： {}， ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getErrorMsg(), e);
        return new ResponseMap(e.getErrorCode(), e.getErrorMsg());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public ResponseMap illegalArgumentExceptionHandler(IllegalArgumentException e, HttpServletRequest req) {
        logger.error("传参异常==========Host： {}， invokes url： {}， ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage(), e);
        return new ResponseMap(ResultEnum.PARAMS_IS_EMPTY.code(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseMap exceptionHandler(HttpServletRequest req, Exception ex) {
        logger.error("系统异常==========Host： {}， invokes url： {}， ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage(), ex);
        return ResponseMap.ErrorInstance();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseMap handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuffer errorMsg = new StringBuffer();
        BindingResult bindingResult = e.getBindingResult();
        // 如果存在多个校验错误，这里只取第一个错误的提示信息
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getAllErrors().get(0);
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorMsg.append(fieldError.getField()).append(fieldError.getDefaultMessage());
            } else {
                errorMsg.append(error.getDefaultMessage());
            }
        }
        return new ResponseMap(ResultEnum.PARAMS_IS_EMPTY.code(), e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseMap handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest req) {
        logger.error("传参异常==========Host： {}， invokes url： {}， ERROR: {}", req.getRemoteHost(), req.getRequestURL(), ex.getMessage(), ex);
        return new ResponseMap(ResultEnum.PARAMS_IS_EMPTY.code(), ex.getMessage());
    }
}
