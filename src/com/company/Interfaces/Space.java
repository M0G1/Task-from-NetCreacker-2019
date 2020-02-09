package com.company.Interfaces;

import com.company.exceptions.InvalidRoomsCountException;
import com.company.exceptions.InvalidSpaceAreaException;

import java.io.Serializable;

public interface Space extends Serializable, Comparable<Space> {


//========================================Getter============================================

    public double getArea();

    public int getCountOfRooms();

//========================================Setter============================================

    public void setArea(double area) throws InvalidSpaceAreaException;

    public void setCountOfRooms(int countOfRooms) throws InvalidRoomsCountException;

//========================================Methods===========================================

    public Object clone() throws CloneNotSupportedException;

}
