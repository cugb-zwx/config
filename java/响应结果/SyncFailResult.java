

/**
 * Title:
 * Project: DTS
 * Description: 同步失败的结构
 * Date: 2019-11-1
 * Copyright: Copyright (c) 2020
 * Company: 北京中科院软件中心有限公司 (SEC)
 *
 * @author zwx
 * @version 1.0
 */

public class SyncFailResult extends SyncResult {

    public <T> SyncFailResult(String msg, T data) {
        super(fail_code, msg, data);
    }

    public <T> SyncFailResult(T data) {
        super(fail_code, data);
    }

    public SyncFailResult(String msg) {
        super(fail_code, msg);
    }

    public SyncFailResult() {
        super(fail_code);
    }
}
