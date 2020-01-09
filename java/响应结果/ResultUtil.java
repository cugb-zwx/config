

/**
 * Title:
 * Project:  
 * Description: 数据同步结果的生成类
 * Date: 2019-11-10
 * Copyright: Copyright (c) 2020
 * Company:  
 *
 * @author zwx
 * @version 1.0
 */

public class ResultUtil {

    public static SyncFailResult returnFail() {
        return new SyncFailResult();
    }

    public static SyncFailResult returnFail(String msg) {
        return new SyncFailResult(msg);
    }

    public static <T> SyncFailResult returnFail(T data) {
        return new SyncFailResult(data);
    }

    public static <T> SyncFailResult returnFail(String msg, T data) {
        return new SyncFailResult(msg, data);
    }

    public static SyncSuccessResult returnSuccess() {
        return new SyncSuccessResult();
    }

    public static SyncSuccessResult returnSuccess(String msg) {
        return new SyncSuccessResult(msg);
    }

    public static <T> SyncSuccessResult returnSuccess(T data) {
        return new SyncSuccessResult(data);
    }

    public static <T> SyncSuccessResult returnSuccess(String msg, T data) {
        return new SyncSuccessResult(msg, data);
    }
}
