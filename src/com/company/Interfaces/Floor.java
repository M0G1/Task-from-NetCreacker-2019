package com.company.Interfaces;

import com.company.exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Iterator;

public interface Floor extends Serializable, Iterable<Space>, Comparable<Floor> {

//========================================Methods============================================

    public int getCountOfSpace();

    public double getTotalArea();

    public long getTotalCountOfRoom();

//=====================================Getter=and=Setter==========================================

    public Space[] getSpaces();

    public Space getSpace(int index) throws SpaceIndexOutOfBoundsException;

    public Space setSpace(int index, Space space) throws SpaceIndexOutOfBoundsException;

    public boolean insertSpace(int index, Space space) throws SpaceIndexOutOfBoundsException;

    public Space eraseSpace(int index) throws SpaceIndexOutOfBoundsException;

    public Space getBestSpace();

    public Object clone() throws CloneNotSupportedException;

}
