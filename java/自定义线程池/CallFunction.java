package com.sec.eac.base.synchronous.pools.common;

/**
 * Title:
 * Project: DTS
 * Description:
 * Date: 2019-12-13
 * Copyright: Copyright (c) 2020
 * Company:  
 *
 * @author zwx
 * @version 1.0
 */
@FunctionalInterface
public interface CallFunction {
    void call(String topicname, byte[] data);
}
