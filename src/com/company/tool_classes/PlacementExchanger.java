package com.company.tool_classes;

import com.company.Interfaces.*;
import com.company.exceptions.InexchangeableFloorsException;
import com.company.exceptions.InexchangeableSpacesException;

import static java.lang.Math.abs;

public class PlacementExchanger {
    public static boolean isExchangeableSpace(Space space1, Space space2) {
        if ((abs(space1.getArea() - space2.getArea()) < Double.MIN_VALUE)
                && (space1.getCountOfRooms() == space2.getCountOfRooms())
        ) {
            return true;
        }
        return false;
    }

    public static boolean isExchangeableFloor(Floor floor1, Floor floor2) {
        if ((abs(floor1.getTotalArea() - floor2.getTotalArea()) < Double.MIN_VALUE)
                && (floor1.getTotalCountOfRoom() == floor2.getTotalCountOfRoom())) {
            return true;
        }
        return false;
    }

    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2) throws InexchangeableSpacesException {
        //check indexes
        if (index1 < 0 || index1 >= floor1.getCountOfSpace()
                || index2 < 0 || index2 >= floor2.getCountOfSpace())
            throw new InexchangeableSpacesException(String.format("Invalid index %d or %d", index1, index2));
        Space space1 = floor1.getSpace(index1);
        Space space2 = floor2.getSpace(index2);
        //the check
        if (!isExchangeableSpace(space1, space2))
            throw new InexchangeableSpacesException(String.format("Can not exchange %s and %s", space1.toString(), space2.toString()));
        //Exchange the spaces
        floor1.setSpace(index1, space2);
        floor2.setSpace(index2, space1);
    }

    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2) throws InexchangeableFloorsException {
        //check indexes
        if (index1 < 0 || index1 >= building1.getNumberOfFloors()
                || index2 < 0 || index2 >= building2.getNumberOfFloors())
            throw new InexchangeableFloorsException(String.format("Invalid index %d or %d", index1, index2));
        Floor floor1 = building1.getFloor(index1);
        Floor floor2 = building2.getFloor(index2);
        //the check
        if (!isExchangeableFloor(floor1, floor2))
            throw new InexchangeableFloorsException(String.format("Can not exchange %s and %s", floor1.toString(), floor2.toString()));
        building1.setFloor(index1, floor2);
        building2.setFloor(index2, floor1);
    }
}
