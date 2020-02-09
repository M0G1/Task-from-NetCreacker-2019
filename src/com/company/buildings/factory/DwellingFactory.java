package com.company.buildings.factory;

import com.company.Interfaces.Building;
import com.company.Interfaces.BuildingFactory;
import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;
import com.company.buildings.dwelling.*;

public class DwellingFactory implements BuildingFactory {
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
        return new DwellingFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new DwellingFloor(spaces);
    }

    @Override
    public Building createBuilding(int floorsCount, int[] spacesCounts) {
        return new Dwelling(floorsCount, spacesCounts);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new Dwelling(floors);
    }
}
