package com.sec.eac.base.synchronous.pools.common;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Title:
 * Project: DTS
 * Description:
 * Date: 2019-12-15
 * Copyright: Copyright (c) 2020
 * Company: 北京中科院软件中心有限公司 (SEC)
 *
 * @author zwx
 * @version 1.0
 */

public class PubRetryThreadPoolExecutor {

    private static ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(1);
}
