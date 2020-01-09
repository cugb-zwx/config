package com.sec.eac.base.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Title:
 * Project: DTS
 * Description:
 * Date: 2019-8-19
 * Copyright: Copyright (c) 2020
 * Company: 北京中科院软件中心有限公司 (SEC)
 *
 * @author zwx
 * @version 1.0
 */

public class ThreadLocalManagerUtil {

    private static ThreadLocal<Map<String, Object>> local = new ThreadLocal<>();

    public static void reset() {
        local.set(null);
    }

    public static <T> T get(String key) {
        T obj = (T) getLocal().get(key);
        return obj;
    }

    public static void set(String key, Object value) {
        getLocal().put(key, value);
    }

    private static Map<String, Object> getLocal() {
        Map<String, Object> map = local.get();
        if (map == null) {
            map = new HashMap<>();
            local.set(map);
        }
        return map;
    }
}
