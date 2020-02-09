package com.company.buildings.threads;

import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;

public class SequentialCleaner implements Runnable {
    private Floor floor;
    private QueueSemaphore sem,sem2;

    public SequentialCleaner(Floor floor, QueueSemaphore queueSemaphore, QueueSemaphore queueSemaphore2) {
        if (floor == null || queueSemaphore == null)
            throw new NullPointerException("Argument must be not null: floor and queueSemaphore");
        this.floor = floor;
        this.sem = queueSemaphore;
        this.sem2 = queueSemaphore2;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < floor.getCountOfSpace() + 1; ++i) {
                //System.out.println("Cleaner before " + i);
                this.sem.waitInQueue();
                //System.out.println("Cleaner after " + i);

                if (i < floor.getCountOfSpace()) {
                    System.out.println(
                            String.format("Cleaning room number  %d with total area %f square meters", i, floor.getSpace(i).getArea())
                    );
                }
                else{
                    System.out.println("End of cleaning space");
                }
                this.sem2.release();
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
