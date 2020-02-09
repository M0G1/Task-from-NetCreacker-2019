package com.company.Interfaces;

import com.company.exceptions.FloorIndexOutOfBoundsException;
import com.company.exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Iterator;

public interface Building extends Serializable, Iterable<Floor>{

//========================================Getter============================================

    public int getNumberOfFloors();

    public long getNumberOfSpaces();

    public double getTotalAreaOfSpaces();

    public long getTotalNumberOfRooms();

    public Floor[] getFloors();

    public Floor getFloor(int index) throws FloorIndexOutOfBoundsException, NullPointerException;

    public Space getSpace(int number) throws IndexOutOfBoundsException, NullPointerException;

//========================================Setter============================================

    public Floor setFloor(int number, Floor floor) throws FloorIndexOutOfBoundsException, NullPointerException;

    public Space setSpaceInBuilding(int number, Space Space) throws SpaceIndexOutOfBoundsException, NullPointerException;

    public boolean insertSpaceInBuilding(int number, Space Space) throws SpaceIndexOutOfBoundsException, NullPointerException;

    public Space eraseSpaceInBuilding(int number) throws NullPointerException;

    public Space getBestSpace();

    public Space[] getSortSpacesOnArea();

    public Object clone() throws CloneNotSupportedException;

}
