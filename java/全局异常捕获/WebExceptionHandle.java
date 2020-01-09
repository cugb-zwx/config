package com.sec.eac.base.controller.api;

import com.sec.eac.base.utils.LoggerUtil;
import com.sec.eac.base.utils.exception.CustomException;
import com.sec.eac.helper.ret.RetObject;
import com.sec.eac.helper.ret.RetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Title:
 * Project: DTS
 * Description:
 * Date: 2019-10-16
 * Copyright: Copyright (c) 2020
 * Company: 北京中科院软件中心有限公司 (SEC)
 *
 * @author zwx
 * @version 1.0
 */
@ControllerAdvice
@ResponseBody
public class WebExceptionHandle {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RetObject handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = "参数解析失败";
        logger.error(msg, e);
        return RetUtil.returnFailure(msg);
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RetObject handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = "参数解析失败";
        logger.error(msg, e);
        return RetUtil.returnFailure(msg);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RetObject handleHttpMediaTypeNotSupportedException(Exception e) {
        String msg = "不支持当前媒体类型";
        logger.error(msg, e);
        return RetUtil.returnFailure(msg);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RetObject handleException(Exception e) {
        String msg = "服务运行异常";
        logger.error(msg, e);
        return RetUtil.returnFailure(msg);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CustomException.class)
    public RetObject handleCustomException(CustomException e) {
        LoggerUtil.logCustomException(logger, e);
        return RetUtil.returnFailure(e.getMessage());
    }
}
