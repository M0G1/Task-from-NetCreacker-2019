package com.company.tool_classes;

import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;
import com.sun.javafx.geom.Matrix3f;

import java.util.Comparator;

public class BuildingComparator {
    public static final Comparator<Space> spaceComparator = new Comparator<Space>() {
        @Override
        public int compare(Space o1, Space o2) {
            return o2.getCountOfRooms() - o1.getCountOfRooms();
        }
    };

    public static final Comparator<Floor> floorComparator = new Comparator<Floor>() {
        @Override
        public int compare(Floor o1, Floor o2) {
            double totalArea1 = o1.getTotalArea();
            double totalArea2 = o2.getTotalArea();
            if (Math.abs(totalArea1 - totalArea2) < Double.MIN_VALUE)
                return 0;
            if (totalArea1 > totalArea2)
                return -1;
            else
                return 1;
        }
    };


}
