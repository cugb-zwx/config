package com.sec.eac.base.synchronous.pools.common;

import java.util.concurrent.*;

/**
 * Title:
 * Project: DTS
 * Description:
 * Date: 2019-12-13
 * Copyright: Copyright (c) 2020
 * Company: 北京中科院软件中心有限公司 (SEC)
 *
 * @author zwx
 * @version 1.0
 */

public class CustomThreadPoolExecutor extends ThreadPoolExecutor {
    private static final int corePoolSize = 2;
    private static final int maxPoolSize = 10;
    private static final long keepAliveTime = 30;
    private static final TimeUnit unit = TimeUnit.SECONDS;
    private static final ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);

    public CustomThreadPoolExecutor(String threadNamePrefix, RejectedExecutionHandler handler) {
        super(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue,
                new CustomThreadFactory(threadNamePrefix), handler);
    }
}
