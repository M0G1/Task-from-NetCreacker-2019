package com.company.buildings.dwelling;

import com.company.Interfaces.Space;
import com.company.exceptions.*;

public class Flat implements Space {
    private double area;
    private int countOfRooms;
    public static final double DEFAULT_AREA = 50d;
    public static final int DEFAULT_ROOMS = 2;

//========================================Constructors============================================

    public Flat() {
        this.area = Flat.DEFAULT_AREA;
        this.countOfRooms = Flat.DEFAULT_ROOMS;
    }

    public Flat(double area) throws InvalidSpaceAreaException {
        if (area < Double.MIN_VALUE)
            throw new IllegalArgumentException("Area can not be negative or equal zero");
        this.area = area;
        this.countOfRooms = Flat.DEFAULT_ROOMS;

    }

    public Flat(double area, int countOfRooms) throws InvalidSpaceAreaException, InvalidRoomsCountException {
        if (area < Double.MIN_VALUE)
            throw new InvalidSpaceAreaException("Area can not be negative or equal zero");
        if (countOfRooms < 1)
            throw new InvalidRoomsCountException("Count of rooms less than 1");
        this.area = area;
        this.countOfRooms = countOfRooms;
    }

//========================================Getter============================================

    public double getArea() {
        return area;
    }

    public int getCountOfRooms() {
        return countOfRooms;
    }

//========================================Setter============================================

    public void setArea(double area) throws InvalidSpaceAreaException {
        if (area < Double.MIN_VALUE)
            throw new InvalidSpaceAreaException("Area can not be negative or equal zero");
        this.area = area;
    }

    public void setCountOfRooms(int countOfRooms) throws InvalidRoomsCountException {
        if (countOfRooms < 1)
            throw new InvalidRoomsCountException("Count of rooms less than 1");
        this.countOfRooms = countOfRooms;
    }


//========================================Methods============================================

//========================================Object============================================

    @Override
    public String toString() {
        return String.format("Flat(%d, %f)", this.countOfRooms, this.area);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Flat))
            return false;
        Flat flat = (Flat) obj;
        if (flat.countOfRooms != this.countOfRooms || Math.abs(flat.area - this.area) > Double.MIN_VALUE)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        long longArea = Double.doubleToLongBits(this.area);
        return this.countOfRooms ^ (int) longArea ^ (int) (longArea >>> 32);
    }

    @Override
    public Object clone() {
        return new Flat(this.area, this.countOfRooms);
    }


    //========================================Compare===========================================
    @Override
    public int compareTo(Space o) {
        if (Math.abs(this.area - o.getArea()) < Double.MIN_VALUE)
            return 0;
        if (this.getArea() > o.getArea())
            return 1;
        else
            return -1;
    }
}

