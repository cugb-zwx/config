package com.sec.eac.base.synchronous.pools.common;

import com.sec.eac.base.synchronous.pools.common.CustomThread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

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

public class CustomThreadFactory implements ThreadFactory {
    private AtomicInteger counter;
    private String prefix;

    public CustomThreadFactory(String prefix) {
        this.prefix = prefix;
        counter = new AtomicInteger(1);
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new CustomThread(prefix + "-" + counter.getAndIncrement(), runnable);
    }
}
