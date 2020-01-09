

/**
 * Title:
 * Project: 
 * Description:
 * Date: 2019-12-13
 * Copyright: Copyright (c) 2020
 * Company:  
 *
 * @author zwx
 * @version 1.0
 */

public class CustomRunnable implements Runnable {
    private CallFunction callFunction;
    private byte[] data;
    private String topicName;

    @Override
    public void run() {
        callFunction.call(topicName, data);
    }

    public CustomRunnable(String topicName, byte[] data, CallFunction callFunction) {
        this.topicName = topicName;
        this.data = data;
        this.callFunction = callFunction;
    }

    public byte[] getData() {
        return data;
    }

    public String getTopicName() {
        return topicName;
    }
}
