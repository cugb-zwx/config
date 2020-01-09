

/**
 * Title:
 * Project:  
 * Description: 同步结果的结构
 * Date: 2019-11-1
 * Copyright: Copyright (c) 2020
 * Company:  
 *
 * @author zwx
 * @version 1.0
 */

public class SyncResult {

    protected static final Integer fail_code = 0;
    protected static final String fail_msg = "操作失败";
    protected static final Integer success_code = 1;
    protected static final String success_msg = "操作成功";

    private Integer code;
    private String msg;
    private Object data;

    protected SyncResult() {
    }

    public SyncResult(Integer code) {
        this.code = code;
        if (isFail()) {
            this.msg = fail_msg;
        } else {
            this.msg = success_msg;
            this.data = this.msg;
        }
    }

    public SyncResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        if (isSuccess()) {
            this.data = this.msg;
        }
    }

    public <T> SyncResult(Integer code, T data) {
        this.code = code;
        if (isFail()) {
            this.msg = fail_msg;
        } else {
            this.msg = success_msg;
        }
        this.data = data;
    }

    public <T> SyncResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean isFail() {
        return fail_code.equals(code);
    }

    public boolean isSuccess() {
        return success_code.equals(code);
    }

    public String getMsg() {
        return msg;
    }

    protected void setMsg(String msg) {
        this.msg = msg;
    }

    public <T> T getData() {
        return (T) data;
    }

    public Integer getCode() {
        return code;
    }

    public <T> void setData(T data) {
        this.data = data;
    }
}
