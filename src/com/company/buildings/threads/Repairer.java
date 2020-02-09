package com.company.buildings.threads;

import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;

public class Repairer extends Thread{
    private Floor floor;

    public Repairer(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        int i = 0;
        for (Space space : this.floor) {
            System.out.println(
                    String.format("Repairing space  number  %d with total area %f square meters", i, space.getArea())
            );
            ++i;
        }
        System.out.println("End of repairing space");
    }
}
