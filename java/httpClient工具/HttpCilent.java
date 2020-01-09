

/**
 * Title:
 * Project: 
 * Description:
 * Date: 2019-11-21
 * Copyright: Copyright (c) 2020
 * Company: 
 *
 * @author zwx
 * @version 1.0
 */

public class HttpCilent {

    public static String sendHttpRequest() {
        return HttpClientUtil.send(ParseUtil.parseMethod());
    }

    public static SyncResult send() {
        String rs = HttpClientUtil.send(ParseUtil.parseMethod());
        if (StringUtils.isBlank(rs))
            return ResultUtil.returnFail(rs);
        RetObject result = JSON.parseObject(rs, RetObject.class);
        if (result.getResponseCode() != 1) {
            return ResultUtil.returnFail(result.getResponseMessage());
        }
        return ResultUtil.returnSuccess(result.getResponseData());
    }

    public static SyncResult send(String path, Map<String, String> params) {
        String rs = HttpClientUtil.send(ParseUtil.getMethod(path, params));
        if (StringUtils.isBlank(rs))
            return ResultUtil.returnFail(rs);
        RetObject result = JSON.parseObject(rs, RetObject.class);
        if (result.getResponseCode() != 1) {
            return ResultUtil.returnFail(result.getResponseMessage());
        }
        return ResultUtil.returnSuccess(result.getResponseData());
    }
}
