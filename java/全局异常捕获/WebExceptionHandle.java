

/**
 * Title:
 * Project: 
 * Description:
 * Date: 2019-10-16
 * Copyright: Copyright (c) 2020
 * Company:  
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
