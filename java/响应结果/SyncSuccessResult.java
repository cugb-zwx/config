

/**
 * Title:
 * Project:  
 * Description:  
 * Date: 2019-11-1
 * Copyright: Copyright (c) 2020
 * Company:  
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
