package com.company.buildings.threads;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayDeque;
import java.util.LinkedList;

/**
 * It is better to give unique objects.
 */
public class QueueSemaphore {
    private ArrayDeque<Object> quene;
    private int permits;

    public QueueSemaphore(int permits ) {
        if (permits < 0)
            throw new IllegalArgumentException("permits " + permits);
        this.quene = new ArrayDeque<>();
        this.permits = permits;
    }

    public void waitInQueue() throws InterruptedException {
        /*Блокируем потоки, на которых разрешений не хватило*/
        if (this.permits == 0) {
            Thread cur = Thread.currentThread();
            synchronized (cur) {
                this.quene.add(cur);
                //System.out.println(cur.getName() + " add and begin to sleep");
                cur.wait();
            }
        } else {
            --this.permits;
        }
    }

    public void release() {
        //разрешаем следущему выполняться, если в очереди, кто-то есть
        if (this.quene.size() > 0) {
            Object obj = quene.pollFirst();
            synchronized (obj) {
                obj.notify();
            }
        } else {
            ++this.permits;
        }
    }

}
