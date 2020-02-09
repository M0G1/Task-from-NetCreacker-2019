package com.company.buildings;

import com.company.Interfaces.Floor;
import com.company.Interfaces.Space;
import com.company.exceptions.SpaceIndexOutOfBoundsException;

import java.util.Iterator;

public class SynchronizedFloor implements Floor {

    Floor floor;

    public SynchronizedFloor(Floor floor) throws NullPointerException {
        if (floor == null)
            throw new NullPointerException("Floor is null");
        this.floor = floor;
    }

    @Override
    synchronized public int getCountOfSpace() {
        return this.floor.getCountOfSpace();
    }

    @Override
    synchronized public double getTotalArea() {
        return this.floor.getTotalArea();
    }

    @Override
    synchronized public long getTotalCountOfRoom() {
        return this.floor.getTotalCountOfRoom();
    }

    @Override
    synchronized public Space[] getSpaces() {
        return this.floor.getSpaces();
    }

    @Override
    synchronized public Space getSpace(int index) throws SpaceIndexOutOfBoundsException {
        return this.floor.getSpace(index);
    }

    @Override
    synchronized public Space setSpace(int index, Space space) throws SpaceIndexOutOfBoundsException {
        return this.floor.setSpace(index, space);
    }

    @Override
    synchronized public boolean insertSpace(int index, Space space) throws SpaceIndexOutOfBoundsException {
        return this.floor.insertSpace(index, space);
    }

    @Override
    synchronized public Space eraseSpace(int index) throws SpaceIndexOutOfBoundsException {
        return this.floor.eraseSpace(index);
    }

    @Override
    synchronized public Space getBestSpace() {
        return this.floor.getBestSpace();
    }

    //========================================Object============================================
    @Override
    synchronized public Object clone() throws CloneNotSupportedException {
        return new SynchronizedFloor((Floor) this.floor.clone());
    }

    @Override
    synchronized public int hashCode() {
        int hash = this.floor.getCountOfSpace();
        long sN = 21;
        long acum = 1;
        for (Space space : this.floor) {
            if (space != null)
                hash ^= (space.hashCode() * acum);
            acum *= sN;
        }
        return hash;
    }

    @Override
    synchronized public boolean equals(Object obj) {
        if (obj instanceof SynchronizedFloor) {
            SynchronizedFloor sFloor = (SynchronizedFloor) obj;
            return sFloor.equals(this.floor);
        }
        return false;
    }

    @Override
    synchronized public String toString() {
        StringBuilder str = new StringBuilder(String.format("SynchronizedFloor(%d", this.getCountOfSpace()));
        for (Space space : this.floor) {
            str.append(", ");
            if (space != null) {
                str.append(space.toString());
            } else
                str.append("null");
        }
        str.append(")");
        return str.toString();
    }

    //========================================Compare===========================================

    @Override
    synchronized public int compareTo(Floor o) {
        return this.floor.compareTo(o);
    }

    //======================================== Iterator ============================================

    @Override
    synchronized public Iterator<Space> iterator() {
        return this.floor.iterator();
    }
}
