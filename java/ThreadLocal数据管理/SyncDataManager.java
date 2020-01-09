package com.sec.eac.base.synchronous.data;

import com.sec.eac.base.synchronous.utils.ActionUtil;
import com.sec.eac.base.utils.ThreadLocalManagerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:
 * Project: DTS
 * Description: 线程内收集需要同步数据
 * Date: 2019-11-19
 * Copyright: Copyright (c) 2020
 * Company: 
 *
 * @author zwx
 * @version 1.0
 */

public class SyncDataManager {

    private static final String thread_local_name = "thread_action_map";
    private static final String thread_local_prefix = "SyncDataManager";
    private static final String SPLIT = "-";

    private static String getKey() {
        return thread_local_prefix + SPLIT + thread_local_name;
    }

    private static Action getAction(String key) {
        Map<String, Action> map = getActionsMap();
        return map.get(key);
    }

    public static <T> void addAction(String serviceName, String method, T param) {
        String key = parseKey(serviceName, method);
        Action action = getAction(key);
        if (action == null) {
            action = ActionUtil.getAction(serviceName, method, param);
            addAction(key, action);
        } else {
            action.getParam().add(param);
        }
    }

    public static <T> void addAction(String serviceName, String method, List<T> params) {
        String key = parseKey(serviceName, method);
        Action action = getAction(key);
        if (action == null) {
            action = ActionUtil.getAction(serviceName, method, params);
            addAction(key, action);
        } else {
            action.getParam().addAll(params);
        }
    }

    private static void addAction(String key, Action action) {
        Map<String, Action> map = getActionsMap();
        map.put(key, action);
    }

    public static List<Action> getActions() {
        Map<String, Action> map = getActionsMap();
        List<Action> actions = new ArrayList<>();
        map.values().stream().forEach(item -> actions.add(item));
        return actions;
    }

    //重置数据，放在重复发送数据
    public static void reset() {
        ThreadLocalManagerUtil.set(getKey(), null);
    }

    private static Map<String, Action> getActionsMap() {
        Map<String, Action> map = ThreadLocalManagerUtil.get(getKey());
        if (map == null) {
            map = new HashMap<>();
            ThreadLocalManagerUtil.set(getKey(), map);
        }
        return map;
    }

    private static String parseKey(String serviceName, String method) {
        return new StringBuilder().append(serviceName).append(SPLIT).append(method).toString();
    }
}
