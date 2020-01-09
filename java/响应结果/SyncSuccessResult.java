

/**
 * Title:
 * Project: DTS
 * Description: 同步成功的结构
 * Date: 2019-11-1
 * Copyright: Copyright (c) 2020
 * Company: 北京中科院软件中心有限公司 (SEC)
 *
 * @author zwx
 * @version 1.0
 */

public class SyncSuccessResult extends SyncResult {
    public <T> SyncSuccessResult(String msg, T data) {
        super(success_code, msg, data);
    }

    public <T> SyncSuccessResult(T data) {
        super(success_code, data);
    }

    public SyncSuccessResult(String msg) {
        super(success_code, msg);
    }

    public SyncSuccessResult() {
        super(success_code);
    }
}
