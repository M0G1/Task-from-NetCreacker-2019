package com.company.buildings.factory;

import com.company.Interfaces.Building;
import com.company.Interfaces.BuildingFactory;
import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;
import com.company.buildings.dwelling.Flat;
import com.company.buildings.dwelling.hotel.*;

public class HotelFactory implements BuildingFactory {
    @Override
    public Space createSpace(double area) {
        return new Flat(area);
    }

    @Override
    public Space createSpace(int roomsCount, double area) {
        return new Flat(area, roomsCount);
    }

    @Override
    public Floor createFloor(int spacesCount) {
        return new HotelFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new HotelFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int[] spacesCounts) {
        return new Hotel(floorsCount, spacesCounts);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new Hotel(floors);
    }
}
