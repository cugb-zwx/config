
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

public class LoggerUtil {

    private static final String logCustomException = "自定义异常========>{}调用{}方法,参数：{},异常信息：{}";
    private static final String logError = "错误信息------>{}调用{}方法,参数：{},异常信息：{}";
    private static final String logError2 = "错误信息------>{}调用{}方法,参数{}：{},异常信息：{}";
    private static final String logError3 = "错误信息------>{}调用{}方法,异常信息：{}";
    private static final String logWarn = "警告信息------>{}调用{}方法,参数{}：{},警告信息：{}";

    public static void logCustomException(Logger logger, CustomException e) {
        logger.error(logCustomException, e.getClassName(), e.getMethodName(), JSON.toJSONString(e.getParamInfo()), e.getMessage());
    }

    public static void logError(Logger logger, String methdName, Object param, String eMsg) {
        logger.error(logError, logger.getName(), methdName, JSON.toJSONString(param), eMsg);
    }

    public static void logError(Logger logger, String eMsg, String methdName, Object... params) {
        logger.error(logError, logger.getName(), methdName, JSON.toJSONString(params), eMsg);
    }

    public static void logError(Logger logger, String methdName, String paramName, Object param, String eMsg) {
        logger.error(logError2, logger.getName(), methdName, paramName, JSON.toJSONString(param), eMsg);
    }

    public static void logError(Logger logger, String methdName, String eMsg) {
        logger.error(logError3, logger.getName(), methdName, eMsg);
    }

    public static void logWarn(Logger logger, String methdName, String paramName, Object param, String eMsg) {
        logger.warn(logWarn, logger.getName(), methdName, paramName, JSON.toJSONString(param), eMsg);
    }
}
