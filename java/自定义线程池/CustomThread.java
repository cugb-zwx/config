

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

public class CustomThread extends Thread {
    private final Date creationDate;
    private Date startDate;
    private Date finishDate;

    public CustomThread(String name, Runnable target) {
        super(target, name);
        creationDate = new Date();
    }

    @Override
    public void run() {
        setStartDate();
        super.run();
        setFinishDate();
    }

    public synchronized void setStartDate() {
        startDate = new Date();
    }

    public synchronized void setFinishDate() {
        finishDate = new Date();
    }

    public synchronized long getExecutionTime() {
        return finishDate.getTime() - startDate.getTime();
    }

    @Override
    public synchronized String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(getName());
        buffer.append(": ");
        buffer.append(" Creation Date: ");
        buffer.append(creationDate);
        buffer.append(" : Running time: ");
        buffer.append(getExecutionTime());
        buffer.append(" Milliseconds.");
        return buffer.toString();
    }
}
