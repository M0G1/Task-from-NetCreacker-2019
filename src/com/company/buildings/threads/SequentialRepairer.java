package com.company.buildings.threads;

import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;

import java.util.Date;

public class SequentialRepairer implements Runnable {
    private Floor floor;
    private QueueSemaphore sem, sem2;

    public SequentialRepairer(Floor floor, QueueSemaphore queueSemaphore, QueueSemaphore queueSemaphore2) {
        this.floor = floor;
        this.sem = queueSemaphore;
        this.sem2 = queueSemaphore2;
    }

    @Override
    public void run() {
        try {
//            try {
//                synchronized (this) {
//                    long b = System.currentTimeMillis();
//                    System.out.println("Начало " + b);
////                Thread cur = Thread.currentThread();
//////                cur.wait(5000);
//                    wait(5000);
//                    long e = System.currentTimeMillis();
//                    System.out.println("Конец " + e);
//                    System.out.println("Разница " + (b - e));
//                }
//            } catch (InterruptedException e) {
//            }
            for (int i = 0; i < floor.getCountOfSpace() + 1; ++i) {
                //System.out.println("Repairer before " + i);
                this.sem.waitInQueue();
                //System.out.println("Repairer after " + i);
                if (i < floor.getCountOfSpace()) {
                    System.out.println(
                            String.format("Repairing space  number  %d with total area %f square meters", i, floor.getSpace(i).getArea())
                    );
                } else {
                    System.out.println("End of repairing space");
                }
                this.sem2.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
